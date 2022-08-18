package com.folliedimomi.fragment

import android.os.Bundle
import android.util.Patterns
import android.view.*
import androidx.fragment.app.Fragment
import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi._app.loadFragmentWithoutBackStack
import com.folliedimomi.dialog.CountryDialog
import com.folliedimomi.model.CountryRespnse
import com.folliedimomi.model.RegisterResponse
import com.folliedimomi.model.RegisterUser
import com.folliedimomi.model.StateRespnse
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.android.material.textfield.TextInputLayout
import com.pcs.ciprianicouture.dialog.StateDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_check_out_register.*
import okhttp3.RequestBody
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.lang.Exception

class CheckOutRegisterFragment(mCartId: String, mSecretKey: String, mGrandTotal: String) : Fragment(),
    KodeinAware, CountryDialog.ProductInterface, StateDialog.StateInterface {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private val session: Session by instance()
    private var countryId = "10"
    private var stateId = "0"

    var cartId: String = ""
    var secretKey: String = ""
    var grandTotal: String = ""

    init {
        cartId = mCartId
        grandTotal = mGrandTotal
        secretKey = mSecretKey
    }

    override fun onCountryClicked(countryId: String, countryName: String) {
        if (isAdded && isVisible) {
            if (countryName != "Italy") {
                telState.hide()
                telHouseNo.hide()
                stateId = "0"
            } else{
                telState.show()
                telHouseNo.show()
            }

            this.countryId = countryId
            etCountry.setText(countryName)
            if (countryName == "Italy") getState(countryId)
        }
    }

    override fun onStateClicked(stateId: String, stateName: String) {
        if (isAdded && isVisible) {
                this.stateId = stateId
                etState.setText(stateName)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCountry()
        //getState(countryId)
        etCountry.setText(Constant.italy)
        etCountry.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (Constant.countryList.isNotEmpty()) {
                    Constant.countryListDialog = CountryDialog(this@CheckOutRegisterFragment)
                    if (!Constant.countryListDialog!!.isVisible) {
                        Constant.countryListDialog!!.show(this@CheckOutRegisterFragment.activity!!.supportFragmentManager, "Country search dialog")
                        Constant.stateList = emptyList()
                    }
                }
            }
            true
        }

        etState.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (Constant.stateList.isNotEmpty()) {
                    Constant.stateListDialog = StateDialog(this@CheckOutRegisterFragment)
                    if (!Constant.stateListDialog!!.isVisible) {
                        Constant.stateListDialog!!.show(this@CheckOutRegisterFragment.activity!!.supportFragmentManager, "State search dialog")
                    }
                } else {
                    requireActivity().coordinatorLayout.snackBar(getString(R.string.does_not_have_any_state))
                }
            }
            true
        }

        btnRegister.setOnClickListener {
            if (validate()) {
                onRegister()
            }
        }

        tvAlreadyHaveAccount.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }
    }

    private fun onRegister() {
        Coroutines.main {
            if (countryId.isEmpty() || countryId == "0") requireActivity().coordinator.snackBar(getString(R.string.select_valid_country))
            //else if (stateId.isEmpty() || stateId == "0") requireActivity().coordinatorLayout.snackBar(getString(R.string.select_valid_state))
            else {
                requireActivity().progress_bars_layout.show()
                val mMap = HashMap<String, RequestBody>()
                mMap["controller"] = "mobileapi".convertBody()
                mMap["op"] = "customer_create".convertBody()
                mMap["id_social"] = "1".convertBody()
                mMap["firstname"] = etName.text.toString().convertBody()
                mMap["lastname"] = etSurname.text.toString().convertBody()
                mMap["email"] = etEmail.text.toString().convertBody()
                mMap["passwd"] = etPass.text.toString().convertBody()
                mMap["company"] = etCompany.text.toString().convertBody()
                mMap["address1"] = etAddress.text.toString().convertBody()
                mMap["postcode"] = etPin.text.toString().convertBody()
                mMap["city"] = etCity.text.toString().convertBody()
                mMap["phone"] = etTelephone.text.toString().convertBody()
                mMap["vat_number"] = "nun".convertBody()
                mMap["id_state"] = stateId.convertBody()
                mMap["id_country"] = countryId.convertBody()
                mMap["lang_id"] = "1".convertBody()
                mMap["shop_id"] = "1".convertBody()
                mMap["id_number"] = etHouseNo.text.toString().convertBody()

                try {
                    val registerResponse: RegisterResponse = repository.onRegister(mMap)
                    if (isAdded && isVisible) {
                        registerResponse.let {
                            if (registerResponse.status == 1) {
                                val user = registerResponse.result
                                setSessionUser(user)
                                requireActivity().coordinator.snackBar(registerResponse.message)
                                requireActivity().loadFragmentWithoutBackStack(OrderAddressFragment(cartId, secretKey, grandTotal))
                                //requireActivity().supportFragmentManager.popBackStackImmediate()
                            } else {
                                requireActivity().coordinator.snackBar(registerResponse.message)
                            }
                            requireActivity().progress_bars_layout.hide()
                            return@main
                        }
                    }
                } catch (ex: Exception) {
                    requireActivity().onException(ex)
                }
            }
        }
    }

    private fun setSessionUser(user: RegisterUser) {
            session.setServerLoggedIn(true)
            session.setUserId(user.idCustomer.toString())
            session.setUserName("${user.firstname}  ${user.lastname}")
            session.setUserEmail(user.email)
            session.setSecureKey(user.secureKey)
            session.setShopId(user.idShop.toString())
    }

    private fun getCountry() {
        requireActivity().progress_bars_layout.show()
        Coroutines.main {
            try {
                val countryResponse: CountryRespnse = repository.getCountry()
                requireActivity().progress_bars_layout.hide()
                if (isAdded && isVisible) {
                    countryResponse.let {
                        if (countryResponse.status == 1) {
                            Constant.countryList = countryResponse.result
                            for (i in Constant.countryList){
                                if (i.name == "Italy") {
                                    countryId = i.id.toString()
                                    getState(countryId)
                                }
                            }
                        }
                        else Constant.countryList = emptyList()
                        return@main
                    }
                }
            } catch (ex: Exception) {
                requireActivity().onException(ex)
            }
        }
    }

    private fun getState(countryId: String) {
        Coroutines.main {
            try {
                val countryResponse: StateRespnse = repository.getStateByCountry(countryId)
                if (isAdded && isVisible) {
                    countryResponse.let {
                        if (countryResponse.status == 1) Constant.stateList = countryResponse.result
                        else Constant.stateList = emptyList()
                        return@main
                    }
                }
            } catch (e: Exception) {
                requireActivity().onException(e)
            }
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        removeError()
        isValid = when {
            etName.text!!.toString().isEmpty() -> setError(telName)
            etSurname.text!!.toString().isEmpty() -> setError(telSurname)
            etAddress.text!!.toString().isEmpty() -> setError(telAddress)
            etEmail.text!!.toString().isEmpty() -> setError(telEmail)
            !Patterns.EMAIL_ADDRESS.matcher(etEmail.text!!).matches() -> setError(telEmail, getString(R.string.error_please_enter_valid_email))
            etPin.text!!.toString().isEmpty() -> setError(telPin)
            etCity.text!!.toString().isEmpty() -> setError(telCity)
            etCountry.text!!.toString().isEmpty() -> setError(telCountry)
            etCountry.text!!.toString() == "Italy" && etState.text!!.toString().isEmpty() -> setError(telState)
            etCountry.text!!.toString() == "Italy" && etHouseNo.text!!.toString().isEmpty()-> setError(telHouseNo)

            /*etCountry.text!!.toString() == "Italy" -> {
                if (etState.text!!.toString().isEmpty())  setError(telState)
                if (etHouseNo.text!!.toString().isEmpty())  setError(telHouseNo)
                //requireActivity().coordinatorLayout.snackBar(getString(R.string.select_valid_country))
                false
            }*/
            //etState.text!!.toString().isEmpty() -> setError(telState)
            etTelephone.text!!.toString().isEmpty() -> setError(telTelephone)
            etPass.text!!.toString().isEmpty() -> setError(telPass)
            else -> true
        }
        return isValid
    }

    private fun setError(tel: TextInputLayout, msg: String = getString(R.string.required)): Boolean {
        tel.isErrorEnabled = true
        tel.error = msg
        return false
    }

    private fun removeError() {
        telName.error = null
        telName.error = null
        telSurname.error = null
        telAddress.error = null
        telEmail.error = null
        telPin.error = null
        telCity.error = null
        telTelephone.error = null
        telCity.error = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_check_out_register, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_cart).isVisible = false
        menu.findItem(R.id.action_search).isVisible = false
    }

}
