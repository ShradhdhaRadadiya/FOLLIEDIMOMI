package com.folliedimomi.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi.activity.MainActivity
import com.folliedimomi.adapter.ShoppingCartAdapter
import com.folliedimomi.model.*
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_shoping_cart.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.IOException
import java.text.DecimalFormat

class ShoppingCartFragment : Fragment(), KodeinAware, ShoppingCartAdapter.OnCartChange {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private val mActivity: MainActivity by instance()
    private val session: Session by instance()
    private var cartId = ""
    private var grandTotal = ""
    private var secretKey = ""
    val objects = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        if (objects.isEmpty()) getPromotion()
        getShoppingCart(session.getUserId().toString(), session.getAppSession().toString())
        Log.i("OkHttp", "Key : ${session.getSecureKey().toString()}")
        /* rvCard.layoutManager = LinearLayoutManager(this@ShoppingCartFragment.activity!!)
         rvCard.adapter = ShoppingCartAdapter(requireActivity())*/
        btnNext.setOnClickListener {
            if (session.isServerLoggedIn()) loadFragment(
                OrderAddressFragment(
                    cartId,
                    secretKey,
                    grandTotal
                )
            )
            else loadFragment(CheckOutLoginFragment(cartId, secretKey, grandTotal))
        }
        llGrandTotal.visibility = View.INVISIBLE
        //slideUp(llGrandTotal)
        llGrandTotal.slidUp()

        tvCancel.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        btnContinueShopping.setOnClickListener {
            this@ShoppingCartFragment.activity!!.supportFragmentManager.popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        btnApply.setOnClickListener {
            if (etPromotionCode.text.toString().isNullOrEmpty()) {
                requireActivity().coordinatorLayout.snackBar("Add coupon first")
                return@setOnClickListener
            }
            onApplyCoupon(etPromotionCode.text.toString(), cartId, session.getUserId().toString())
            //requireActivity().coordinatorLayout.snackBar("Apply Coupon")
        }

        btnRemove.setOnClickListener {
            onRemoveCoupon(cartId = cartId)
        }

        nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (scrollY > oldScrollY) llGrandTotal.slidDown()
            else llGrandTotal.slidUp()
        }
    }

    private fun onApplyCoupon(couponCode: String, cartId: String, userId: String) {
        Coroutines.main {
            try {
                val applyCouponRespone: ApplyCouponRespone = repository.onApplyCoupon(
                    couponCode = couponCode,
                    id_cart = cartId,
                    id_customer = userId
                )
                if (isAdded && isVisible) {
                    applyCouponRespone.let {
                        if (applyCouponRespone.status == 1) {
                            getShoppingCart(
                                session.getUserId().toString(),
                                session.getAppSession().toString()
                            )
                        } else {
                            requireActivity().coordinatorLayout.snackBar(applyCouponRespone.message)
                        }
                    }
                }
            } catch (e: Exception) {
                requireActivity().onException(e)
            }
        }
    }

    private fun onRemoveCoupon(couponCode: String = cartRule, cartId: String) {
        Coroutines.main {
            try {
                val applyCouponRespone: ApplyCouponRespone =
                    repository.onRemoveCoupon(id_cart_rule = couponCode, id_cart = cartId,session.getUserId().toString())
                if (isAdded && isVisible) {
                    applyCouponRespone.let {
                        if (applyCouponRespone.status == 1) {
                            getShoppingCart(
                                session.getUserId().toString(),
                                session.getAppSession().toString()
                            )
                        } else {
                            requireActivity().coordinatorLayout.snackBar(applyCouponRespone.message)
                        }
                    }
                }
            } catch (e: Exception) {
                requireActivity().onException(e)
            }
        }
    }

    var cartRule: String = ""

    @SuppressLint("SetTextI18n")
    private fun getShoppingCart(userId: String, key: String) {
        val df = DecimalFormat("#.00")
        requireActivity().progress_bars_layout.show()
        Coroutines.main {
            try {
                val shoppingCartResponse: ShoppingCartResponse =
                    repository.getShoppingCart(userId, key) as ShoppingCartResponse
                shoppingCartResponse.let {
                    if (isAdded && isVisible) {
                        requireActivity().progress_bars_layout.hide()
                        if (shoppingCartResponse.status == 1) {
                            rlEmptyCard.hide()
                            val shoppingCart: ShoppingCartResult = shoppingCartResponse.result
                            shoppingCart.let {
                                val products: List<Product> = it.products
                                if (products.isNullOrEmpty()) {
                                    //llegalArgumentException
                                    MainActivity.cartCount = 0
                                    rlEmptyCard.show()
                                } else {
                                    MainActivity.cartCount = products.size
                                    var productName: StringBuilder = StringBuilder()
                                    for (item in products) {
                                        productName.append(item.name + ",")
                                    }

                                    Constant.productName = productName.toString()
                                    requireContext().logI(Constant.productName)
                                    if (Constant.productName.isNotEmpty() && Constant.productName.endsWith(
                                            ","
                                        )
                                    ) {
                                        Constant.productName = Constant.productName.dropLast(1)
                                    }
                                    requireContext().logI(Constant.productName)

                                    rvCard.layoutManager =
                                        LinearLayoutManager(this@ShoppingCartFragment.activity!!)
                                    rvCard.adapter = ShoppingCartAdapter(
                                        requireActivity(),
                                        products,
                                        repository,
                                        shoppingCart.idCart.toString(),
                                        this@ShoppingCartFragment,
                                        session
                                    )
                                    //requireActivity().coordinatorLayout.snackBar(addToCartResponse.message)
                                    val summary: CartSummary = shoppingCart.cartSummary
                                    cartId = shoppingCart.idCart.toString()
                                    secretKey = shoppingCart.secureKey.toString()
                                tvTotalDiscount.text = if(summary.totalDiscounts == 0.0)  "0,00 €" else df.format(summary.totalDiscounts).replace(".", ",") +" €"

//                                    tvTotalDiscount.text = String.format("%.2f", summary.totalDiscounts).toDouble().toString() + " €"
                                    //tvSubTotal.text = String.format("%.2f", summary.totalProducts).toDouble().toString() + " €"
                                    //tvTotalTax.text = String.format("%.2f", summary.totalShipping).toDouble().toString() + " €"
                                    tvTotalDiscount.text =
                                        if (summary.totalDiscounts == 0.0) "0,00 €" else df.format(
                                            summary.totalDiscounts
                                        ).replace(".", ",") + " €"
                                    tvSubTotal.text =
                                        if (summary.totalProducts == 0.0) "0,00 €" else df.format(
                                            summary.totalProducts
                                        ).replace(".", ",") + " €"
                                    tvTotalTax.text =
                                        if (summary.totalShipping == 0.0) "0,00 €" else df.format(
                                            summary.totalShipping
                                        ).replace(".", ",") + " €"

                                    var totalPay: Double = 0.0
//                                    for (i in products) {
//                                        if (i.cartCustomization.isNotEmpty()) {
//                                            //holder.tvPrice.text  = "${item.cartCustomization[0].custom_price/*.toInt()*/} €"
//                                            //var price = item.cartCustomization[0].custom_price.replace(",", ".").toDouble()
//                                            //var total = price * item.cartQuantity
//                                            //holder.tvGrandTotal.text = /*"€ "+*/"${total.toString().replace(".", ",")} €"
//                                            var price = i.cartCustomization[0].custom_price.replace(",", ".").toDouble()
//                                            var newPrice = price + i.priceWt
//                                            var total = newPrice * i.cartQuantity
//                                            //var totalWt = total +
//                                            totalPay += total
//                                        } else {
//                                            totalPay += i.priceWt * i.cartQuantity
//                                        }
//                                    }
//
//                                    totalPay += summary.totalShipping
//                                    totalPay -= summary.totalDiscounts

                                    totalPay = summary.totalPrice
                                    //tvGrandTotalGroup.text ="  ${String.format("%.2f", totalPay).toDouble().toString().replace(".", ",")} €"
                                    tvGrandTotalGroup.text = df.format(totalPay).replace(
                                        ".",
                                        ","
                                    ) + " €" //"  ${String.format("%.2f", totalPay).toDouble().toString().replace(".", ",")} €"
                                    grandTotal = totalPay.toString()
                                    //grandTotal = summary.totalPrice.toString()
                                    //tvGrandTotalGroup.text = summary.totalPrice.toString() + " €"
                                    //tvGrandTotal.text = summary.totalPrice.toString()

                                    val promotion: List<DiscountsItem> = summary.discounts
                                    if (promotion.isNotEmpty()) {
                                        cvRemoveCode.show()
                                        cvApplyCode.hide()
                                        tvPromotionCode.text = promotion[0].code
                                        cartRule = promotion[0].idCartRule
                                    } else {
                                        cvRemoveCode.hide()
                                        cvApplyCode.show()
                                    }

                                    cvCartTotal.show()
                                    llGrandTotal.show()
                                    //cvApplyCode.show()
                                }
                            }
                        } else {
                            MainActivity.cartCount = 0
                            rlEmptyCard.show()
                            //requireActivity().coordinatorLayout.snackBar(addToCartResponse.message)
                        }
                    }
                    return@main
                }
            } catch (e: ApiException) {
                Log.i("OkHttp", e.message.toString())
                requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: JsonSyntaxException) {
                Log.i("OkHttp", e.message.toString())
                requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: NoInternetException) {
                Log.i("OkHttp", e.message.toString())
                requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: IOException) {
                Log.i("OkHttp", e.message.toString())
                requireActivity().coordinatorLayout.snackBar(e.message!!)
            }
        }
    }

    override fun onItemDeleted(productId: String) {
        getShoppingCart(session.getUserId().toString(), session.getAppSession().toString())
    }

    private fun getPromotion() {
        Coroutines.main {
            try {
                val promotionResponse: PromotionResponse = repository.getPromotion()
                if (isAdded && isVisible) {
                    promotionResponse.let {
                        if (promotionResponse.status == 1) {
                            val promotions = promotionResponse.result
                            promotions.let {
                                for (item in promotions) {
                                    objects.add(item.banner)
                                }
                                val array = arrayOfNulls<String>(objects.size)
                                objects.toArray(array)
                                array.let { tvBanner.setTexts(array!!) }
                            }
                        }
                        return@main
                    }
                }
            } catch (e: ApiException) {
                requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: JsonSyntaxException) {
                requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: NoInternetException) {
                requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: IOException) {
                requireActivity().coordinatorLayout.snackBar(e.message!!)
            }
        }
    }

    fun loadFragment(fragment: Fragment) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
            .addToBackStack(fragment::class.java.simpleName).commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shoping_cart, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        mActivity.updateCount(requireContext(), MainActivity.cartCount)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_cart).isVisible = false
        menu.findItem(R.id.action_search).isVisible = false
    }

    override fun onPause() {
        super.onPause()
        objects.clear()
    }
    override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }
}
