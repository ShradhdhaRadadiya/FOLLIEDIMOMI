package com.folliedimomi.fragment


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi._app.loadFragment
import com.folliedimomi.model.CreateOrderResponse
import com.folliedimomi.model.PaypalPaymentModel
import com.folliedimomi.model.StripeAuthentication
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.GsonBuilder
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PaymentActivity
import com.paypal.android.sdk.payments.PaymentConfirmation
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.Card
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.StripeIntent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_order_review.*
import kotlinx.android.synthetic.main.stripe_card_layout.*
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.lang.ref.WeakReference
import kotlin.math.roundToInt

/**
 * This example collects card payments, implementing the guide here: https://stripe.com/docs/payments/accept-a-payment#android
 *
 * To run this app, follow the steps here: https://github.com/stripe-samples/accept-a-card-payment#how-to-run-locally
 */
private val backendUrl = "http://baleshawebsolutions.com/stripeweb/"
//http://baleshawebsolutions.com/stripeweb/newcreate_payment.php?currency=usd&amount=1100&card_holder=Vipul&user_id=132

class OrderReviewFragment(
    mCartId: String, mSecretKey: String, mGrandTotal: String,
    mAddressId: String, mIdCarrier: String, myIdAddressInvoice: String, mIsGuest: Boolean = false
) : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private val session: Session by instance()
    private var bundle: Bundle = Bundle()


    val _currency: String = "eur"

    /**Stripe*/
    private lateinit var publishableKey: String
    private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe

    /**Paypal*/
    private var paymentAmount: String = "0"
    val PAYPAL_REQUEST_CODE = 123
    var config: PayPalConfiguration =
        PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION).clientId(
            PayPalConfig.PAYPAL_CLIENT_ID
        )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        requireActivity().logI("Grand Total : $grandTotal")
        //var amounts = grandTotal.toDouble().roundToInt() * 100
        //requireActivity().logI("Amounts : $amounts")

        //onInitStripe()

        btnPayNow.setOnClickListener {
            when {
                cbxPayPal.isChecked -> {
                    requireActivity().toast("Prossimamente...")

/*

                    if (grandTotal.isNotEmpty()) {
                        paymentAmount = grandTotal
                    }
                    //sandbox_d5b6zwpj_3g283r5trmgxb8qc - SANDBOX
                    //production_w3jssw64_89xhm7rnq77hczy2 - live

                    val dropInRequest =
                        DropInRequest().tokenizationKey("production_w3jssw64_89xhm7rnq77hczy2")
                    dropInRequest.collectDeviceData(true)
                    startActivityForResult(dropInRequest.getIntent(requireActivity()), 100)

*/

                    //onInitPayPal()
                    //requireActivity().loadFragment(PayPalFragment(cartId, secretKey, grandTotal, idAddress, idCarrier, idAddressInvoice))
                }
                cbxBonifico.isChecked -> {
                    onPlaceOrder("Bonifico bancario", "ps_wirepayment")
                }
                cbxPay.isChecked -> {
                    onPlaceOrder("Pagamento tramite PostePay", " ps_tanzopostepay")
                }

                else -> requireActivity().coordinatorLayout.snackBar("Seleziona il tipo di pagamento"/*""Please choose any payment option first"*/)
            }
        }

        cbxPayPal.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) cbxBonifico.isChecked = false
            if (isChecked) cbxPay.isChecked = false
        }
        cbxBonifico.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) cbxPayPal.isChecked = false
            if (isChecked) cbxPay.isChecked = false

        }
        cbxPay.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) cbxPayPal.isChecked = false
            if (isChecked) cbxBonifico.isChecked = false

        }



        tvCancel.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }

    }

    private fun validate(): Boolean {
        var isValid = true
        telCardHolder.error = null
        telCardNumber.error = null
        telExpMonth.error = null
        telExpYear.error = null
        telCVV.error = null
        isValid = when {
            etCardHolder.text!!.toString().isEmpty() -> setError(
                telCardHolder,
                getString(R.string.required)
            )
            etCardNumber.text!!.toString().isEmpty() -> setError(
                telCardNumber,
                getString(R.string.required)
            )
            etExpMonth.text!!.toString().isEmpty() -> setError(
                telExpMonth,
                getString(R.string.required)
            )
            etExpYear.text!!.toString().isEmpty() -> setError(
                telExpYear,
                getString(R.string.required)
            )
            etCVV.text!!.toString().isEmpty() -> setError(telCVV, getString(R.string.required))
            else -> true
        }
        return isValid
    }

    private fun setError(
        tel: TextInputLayout,
        msg: String = getString(R.string.required)
    ): Boolean {
        tel.isErrorEnabled = true
        tel.error = msg
        return false
    }

    private fun onInitPayPal() {
        // PayPal start service
        /*al intent = Intent(requireActivity(), PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        requireActivity().startService(intent)*/

        if (grandTotal.isNotEmpty()) {
            paymentAmount = grandTotal

            val dropInRequest = DropInRequest().tokenizationKey("sandbox_d5b6zwpj_3g283r5trmgxb8qc")
            startActivityForResult(dropInRequest.getIntent(requireActivity()), 100)
            /*
            val payment = PayPalPayment(BigDecimal(paymentAmount.toString()), "EUR", Constant.productName*//*"$"*//*, PayPalPayment.PAYMENT_INTENT_SALE)
            val paymentIntent = Intent(requireActivity(), PaymentActivity::class.java)
            paymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
            paymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
            startActivityForResult(paymentIntent, PAYPAL_REQUEST_CODE)*/
        }

    }





    /*
     id_cart
        id_customer
     id_address_delivery
     id_address_invoice
     id_carrier
     order_payment
     order_module
     secure_key
     customersessionid
     is_guest
     guest_email
     is_same_invoice_address
     shipping_id_country
     shipping_id_state
     shipping_firstname
     shipping_lastname
      shipping_company
    shipping_vat_number
    shipping_address1
    shipping_address2
    shipping_postcode
    shipping_city
    shipping_phone_mobile
    payment_id_country
    payment_id_state
payment_firstname
payment_lastname
payment_company
payment_vat_number
payment_address1
payment_address2
payment_postcode
payment_city
payment_phone_mobile


     */

    private fun onPlaceOrder(orderPayment: String, paymentMethod: String) {
        Coroutines.main {
            requireActivity().progress_bars_layout.show()
            val mMap = HashMap<String, RequestBody>()
            mMap["controller"] = "mobileapi".convertBody()
            mMap["op"] = "place_order".convertBody()
            mMap["id_cart"] = cartId.convertBody()
            mMap["lang_id"] = Constant.LANG.convertBody()

            mMap["order_payment"] = /*cardHolderName*/orderPayment.toString().convertBody()
            mMap["order_module"] = paymentMethod.toString().convertBody()

            mMap["secure_key"] = secretKey.convertBody()
            mMap["customersessionid"] = session.getAppSession().toString().convertBody()

            if (isGuest) {
                mMap["is_guest"] = "yes".convertBody()
                mMap["guest_email"] = bundle.getString("email", "")!!.toString().convertBody()
                mMap["shipping_firstname"] =
                    bundle.getString("first_name", "")!!.toString().convertBody()
                mMap["shipping_lastname"] =
                    bundle.getString("last_name", "")!!.toString().convertBody()
                mMap["shipping_address1"] =
                    bundle.getString("address_one", "")!!.toString().convertBody()
                mMap["shipping_address2"] =
                    bundle.getString("address_two", "")!!.toString().convertBody()
                mMap["shipping_city"] = bundle.getString("city", "")!!.toString().convertBody()
                mMap["shipping_id_country"] = bundle.getString("country", "")!!.convertBody()
                mMap["shipping_id_state"] = bundle.getString("state", "")!!.convertBody()
                mMap["shipping_postcode"] = bundle.getString("pin", "")!!.toString().convertBody()
                mMap["shipping_phone_mobile"] =
                    bundle.getString("mobile", "")!!.toString().convertBody()
                mMap["shipping_company"] =
                    bundle.getString("company", "")!!.toString().convertBody()
                mMap["shipping_vat_number"] =
                    bundle.getString("id_number", "")!!.toString().convertBody()
                val isDifferentBilling: Boolean = bundle.getBoolean("is_different_billing", false)
                if (isDifferentBilling) {
                    mMap["is_same_invoice_address"] = "no".convertBody()
                    mMap["payment_firstname"] =
                        bundle.getString("bill_first_name", "")!!.toString().convertBody()
                    mMap["payment_lastname"] =
                        bundle.getString("bill_last_name", "")!!.toString().convertBody()
                    mMap["payment_address1"] =
                        bundle.getString("bill_address_one", "")!!.toString().convertBody()
                    mMap["payment_address2"] =
                        bundle.getString("bill_address_two", "")!!.toString().convertBody()
                    mMap["payment_city"] =
                        bundle.getString("bill_city", "")!!.toString().convertBody()
                    mMap["payment_id_country"] =
                        bundle.getString("bill_country", "")!!.convertBody()
                    mMap["payment_id_state"] = bundle.getString("bill_state", "")!!.convertBody()
                    mMap["payment_postcode"] =
                        bundle.getString("bill_pin", "")!!.toString().convertBody()
                    mMap["payment_phone_mobile"] =
                        bundle.getString("bill_mobile", "")!!.toString().convertBody()
                    mMap["payment_company"] =
                        bundle.getString("bill_company", "")!!.toString().convertBody()
                    mMap["payment_vat_number"] =
                        bundle.getString("bill_id_number", "")!!.toString().convertBody()

                    mMap["payment_vat_number_two"] =
                        bundle.getString("bill_vat_number", "")!!.toString().convertBody()
                    mMap["payment_home_phone"] =
                        bundle.getString("bill_home_phone", "")!!.toString().convertBody()
                    mMap["payment_address_title"] =
                        bundle.getString("bill_address_title", "")!!.toString().convertBody()
                    mMap["payment_other_information"] =
                        bundle.getString("bill_other_information", "")!!.toString().convertBody()
                } else {
                    mMap["is_same_invoice_address"] = "yes".convertBody()
                }
            } else {
                mMap["id_customer"] = session.getUserId().toString().convertBody()
                mMap["id_address_delivery"] = idAddress.convertBody()
                mMap["id_address_invoice"] = idAddressInvoice.convertBody()
                mMap["id_carrier"] = "22".convertBody() //idCarrier.convertBody()
                mMap["is_guest"] = "no".convertBody()
            }

            try {
                val createOrderResponse: CreateOrderResponse = repository.createOrder(mMap)
                if (isAdded && isVisible) {
                    requireActivity().progress_bars_layout.hide()
                    createOrderResponse.let {
                        if (createOrderResponse.status == 1) {
                            requireActivity().loadFragment(
                                OrderConfirmFragment(
                                    createOrderResponse.result.idOrder.toString(),
                                    createOrderResponse.result.idCart.toString(),
                                    createOrderResponse.result.idCustomer.toString()
                                )
                            )

                        } else requireActivity().coordinatorLayout.snackBar(createOrderResponse.message)
                        return@main
                    }
                }
            } catch (e: java.lang.Exception) {
                requireActivity().onException(e)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_review, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if (arguments != null && !requireArguments().isEmpty) {
            val args = requireArguments()
            bundle.putString("email", args.getString("email", ""))
            bundle.putString("first_name", args.getString("first_name", ""))
            bundle.putString("last_name", args.getString("last_name", ""))
            bundle.putString("id_number", args.getString("id_number", ""))
            bundle.putString("address_one", args.getString("address_one", ""))
            bundle.putString("address_two", args.getString("address_two", ""))
            bundle.putString("pin", args.getString("pin", ""))
            bundle.putString("city", args.getString("city", ""))
            bundle.putString("country", args.getString("country", ""))
            bundle.putString("state", args.getString("state", ""))
            bundle.putString("company", args.getString("company", ""))
            bundle.putString("mobile", args.getString("mobile", ""))

            bundle.putBoolean(
                "is_different_billing",
                args.getBoolean("is_different_billing", false)
            )
            bundle.putString("bill_first_name", args.getString("bill_first_name", ""))
            bundle.putString("bill_last_name", args.getString("bill_last_name", ""))
            bundle.putString("bill_id_number", args.getString("bill_id_number", ""))
            bundle.putString("bill_address_one", args.getString("bill_address_one", ""))
            bundle.putString("bill_address_two", args.getString("bill_address_two", ""))
            bundle.putString("bill_pin", args.getString("bill_pin", ""))
            bundle.putString("bill_city", args.getString("bill_city", ""))
            bundle.putString("bill_country", args.getString("bill_country", ""))
            bundle.putString("bill_state", args.getString("bill_state", ""))
            bundle.putString("bill_company", args.getString("bill_company", ""))
            bundle.putString("bill_mobile", args.getString("bill_mobile", ""))

            bundle.putString("bill_vat_number", args.getString("bill_vat_number", ""))
            bundle.putString("bill_home_phone", args.getString("bill_home_phone", ""))
            bundle.putString("bill_address_title", args.getString("bill_address_title", ""))
            bundle.putString("bill_other_information", args.getString("bill_other_information", ""))

            //bundle.putString("bill_vat_number", etBillVatNumber.text.toString())
            //bundle.putString("bill_home_phone", etBillMobile.text.toString())
            // bundle.putString("bill_address_title", etBillMobile.text.toString())
            // bundle.putString("bill_other_information", etBillMobile.text.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_cart).isVisible = false
        menu.findItem(R.id.action_search).isVisible = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //val weakActivity = WeakReference<Activity>(this@OrderReviewFragment.activity)
        if (requestCode == PAYPAL_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val confirm: PaymentConfirmation =
                        data!!.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION)!!
                    confirm?.let {
                        try {
                            Log.i("paypal", confirm.toJSONObject().toString(10))
                            val paymentDetails = confirm.toJSONObject().toString(4)
                            Log.i("paypal", paymentDetails)
                            //requireActivity().coordinatorLayout.snackBar("Pagamento completato con successo"/*"Successfully payment done"*/)
                            try {
                                val jsonDetails = JSONObject(paymentDetails)
                                val jsonResponse = jsonDetails.getJSONObject("response")
                                val paymentStatus = jsonResponse.getString("state")
                                val paymentId = jsonResponse.getString("id")
                                onPlaceOrder(paymentId, "Paypal")
                                //displayAlert(requireActivity(), "Completa Pagamento"/*"Payment succeeded"*/, "Status : $paymentStatus and Payment Id : $paymentId")
                                //showDetail(jsonDetails.getJSONObject("response"), grandTotal)
                            } catch (e: JSONException) {
                                requireActivity().coordinatorLayout.snackBar("Failed : ${e.message}")
                            }
                            //startActivity(Intent(this, ConfirmationActivity::class.java).putExtra("PaymentDetails", paymentDetails).putExtra("PaymentAmount", paymentAmount))
                        } catch (e: JSONException) {
                            requireActivity().coordinatorLayout.snackBar("Failed : an extremely unlikely failure occurred $e")
                        }
                    }
                }
                Activity.RESULT_CANCELED -> {
                    requireActivity().coordinatorLayout.snackBar("The user canceled.")
                }
                PaymentActivity.RESULT_EXTRAS_INVALID -> {
                    requireActivity().coordinatorLayout.snackBar("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.")
                }
            }
        } else if (requestCode == 100) {
            when (resultCode) {
                AppCompatActivity.RESULT_OK -> {
                    val result: DropInResult =
                        data?.getParcelableExtra<DropInResult>(DropInResult.EXTRA_DROP_IN_RESULT) as DropInResult
                    val paymentMethodNonce = result.paymentMethodNonce
                    val devicedATA = result.deviceData

                    Log.e("TAG", "result is -----> $result")
                    Log.e("TAG", "nonce is -----> ${paymentMethodNonce!!.nonce}")
                    Log.e("TAG", "device is -----> $devicedATA")

                    onPlaceOrderPaypal(paymentMethodNonce.nonce, devicedATA)
                }
                AppCompatActivity.RESULT_CANCELED -> { // the user canceled
                }
                else -> { // handle errors here, an exception may be available in
                    val error = data?.getSerializableExtra(DropInActivity.EXTRA_ERROR) as Exception
                    Log.e("TAG", "error is ----->$error ")

                }
            }
        }
    }

    private fun onPlaceOrderPaypal(paymentMethodNonce: String, devicedATA: String?) {
        Coroutines.main {
            val auth: PaypalPaymentModel =
                repository.onPayPalAuth(
                    paymentMethodNonce.toString(),
                    paymentAmount!!.toString(),
                    devicedATA.toString()
                )
            auth.let {
                Log.e("TAG", "rESPONSE IS -----> $it")
                if (auth.success) {
                    onPlaceOrder(auth.transaction.id.toString(), "Paypal")
//                    requireActivity().loadFragment(HomeFragment())
//                    requireActivity().supportFragmentManager.popBackStackImmediate()
                }
                return@main
            }
        }


    }


    var cartId: String = ""
    var isGuest: Boolean = false
    var secretKey: String = ""
    var idCarrier: String = ""
    var idAddress: String = ""
    var grandTotal: String = ""
    var idAddressInvoice: String = ""

    init {
        isGuest = mIsGuest
        idAddress = mAddressId
        cartId = mCartId
        grandTotal = mGrandTotal
        secretKey = mSecretKey
        idCarrier = mIdCarrier
        idAddressInvoice = myIdAddressInvoice
    }

    /*private fun startCheckout() {
        val weakActivity = WeakReference<Activity>(requireActivity())
        // Create a PaymentIntent by calling the sample server's /create-payment-intent endpoint.
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val json = """
            {
                "currency":"inr",
                "items": [
                    {"id":"photo_subscription"}
                ]
            }
            """
        val body = json.toRequestBody(mediaType)
        val request = Request.Builder()
                .url(backendUrl + "create-payment-intent")
                .post(body)
                .build()
        httpClient.newCall(request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        displayAlert(weakActivity.get(), "Failed to load page", "Error: $e")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (!response.isSuccessful) {
                            displayAlert(weakActivity.get(), "Failed to load page", "Error: $response")
                        } else {
                            val responseData = response.body?.string()
                            var json = JSONObject(responseData)

                            // The response from the server includes the Stripe publishable key and
                            // PaymentIntent details.
                            // For added security, our sample app gets the publishable key from the server
                            publishableKey = json.getString("publishableKey")
                            paymentIntentClientSecret = json.getString("clientSecret")

                            // Configure the SDK with your Stripe publishable key so that it can make requests to the Stripe API
                            stripe = Stripe(requireActivity().applicationContext, publishableKey)
                        }
                    }
                })
    }*/

}
