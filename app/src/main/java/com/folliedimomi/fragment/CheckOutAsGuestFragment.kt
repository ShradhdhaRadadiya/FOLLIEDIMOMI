package com.folliedimomi.fragment

import android.os.Bundle
import android.util.Patterns
import android.view.Menu
import android.view.MenuInflater
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi.dialog.CountryDialog
import com.folliedimomi.model.CountryRespnse
import com.folliedimomi.model.StateRespnse
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.android.material.textfield.TextInputLayout
import com.folliedimomi.dialog.StateDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_check_out_as_guest.*

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class CheckOutAsGuestFragment(mCartId: String, mSecretKey: String, mGrandTotal: String) :
        Fragment(R.layout.fragment_check_out_as_guest),
        KodeinAware, CountryDialog.ProductInterface, StateDialog.StateInterface {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private val session: Session by instance()
    private var isBillingShow: Boolean = false
    var cartId: String = ""
    var secretKey: String = ""
    var grandTotal: String = ""
    private var countryId = "10"
    private var billCountryId = "10"
    private var isBillCountry = false
    private var stateId = "0"
    private var billStateId = "0"
    private var isBillState = false

    init {
        cartId = mCartId
        grandTotal = mGrandTotal
        secretKey = mSecretKey
    }

    override fun onStateClicked(stateId: String, stateName: String) {
        if (isAdded && isVisible) {
            if (isBillState) {
                this.billStateId = stateId
                etBillState.setText(stateName)
            } else {
                this.stateId = stateId
                etState.setText(stateName)
            }
        }
    }

    override fun onCountryClicked(countryId: String, countryName: String) {
        if (isAdded && isVisible) {
            Constant.stateList = emptyList()
            //etCountry.setText(countryName)
            if (isBillCountry) {
                if (countryName != "Italy") {
                    telIBillState.hide()
                    telIBillIdentification.hide()
                } else {
                    telIBillState.show()
                    telIBillIdentification.show()
                }

                this.billCountryId = countryId
                etBillCountry.setText(countryName)
                etBillState.setText("")
                billStateId = "0"
            } else {
                if (countryName != "Italy") {
                    telState.hide()
                    telIdentificationNumber.hide()
                } else {
                    telState.show()
                    telIdentificationNumber.show()
                }

                this.countryId = countryId
                etCountry.setText(countryName)
                etState.setText("")
                stateId = "0"
            }
            if (countryName == "Italy") getState(countryId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAddAddress.setOnClickListener {
            if (validate()) {
                if (isBillingShow) {
                    if (validateBilling()) onAddAddress()
                } else {
                    onAddAddress()
                }
            }
        }

        tvCancel.setOnClickListener {
            this@CheckOutAsGuestFragment.activity!!.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        btnIsDifferentBilling.setOnClickListener {
            isBillingShow = !isBillingShow
            if (isBillingShow) {
                llBillingAddress.show()
            } else {
                llBillingAddress.hide()
            }
        }

        etCountry.setText(Constant.italy)
        etCountry.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (Constant.countryList.isNotEmpty()) {
                    Constant.countryListDialog = CountryDialog(this@CheckOutAsGuestFragment)
                    if (!Constant.countryListDialog!!.isVisible) {
                        Constant.countryListDialog!!.show(this@CheckOutAsGuestFragment.activity!!.supportFragmentManager, "Country search dialog")
                        isBillCountry = false
                    }
                }
            }
            true
        }

        etBillCountry.setText(Constant.italy)
        etBillCountry.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (Constant.countryList.isNotEmpty()) {
                    Constant.countryListDialog = CountryDialog(this@CheckOutAsGuestFragment)
                    if (!Constant.countryListDialog!!.isVisible) {
                        Constant.countryListDialog!!.show(this@CheckOutAsGuestFragment.activity!!.supportFragmentManager, "Country search dialog")
                        isBillCountry = true
                    }
                }
            }
            true
        }

        etState.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (Constant.stateList.isNotEmpty()) {
                    Constant.stateListDialog = StateDialog(this@CheckOutAsGuestFragment)
                    if (!Constant.stateListDialog!!.isVisible) {
                        Constant.stateListDialog!!.show(this@CheckOutAsGuestFragment.activity!!.supportFragmentManager, "State search dialog")
                        isBillState = false
                    }
                } else {
                    requireActivity().coordinatorLayout.snackBar(getString(R.string.does_not_have_any_state))
                }
            }
            true
        }

        etBillState.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (Constant.stateList.isNotEmpty()) {
                    Constant.stateListDialog = StateDialog(this@CheckOutAsGuestFragment)
                    if (!Constant.stateListDialog!!.isVisible) {
                        Constant.stateListDialog!!.show(this@CheckOutAsGuestFragment.activity!!.supportFragmentManager, "State search dialog")
                        isBillState = true
                    }
                } else {
                    requireActivity().coordinatorLayout.snackBar(getString(R.string.does_not_have_any_state))
                }
            }
            true
        }

        getCountry()
        //getState(countryId)
    }

    private fun onAddAddress() {
        var bundle: Bundle = Bundle()

        if (countryId.isEmpty() || countryId == "0") requireActivity().coordinatorLayout.snackBar(getString(R.string.select_valid_country))
        //else if (stateId.isEmpty() || stateId == "0") requireActivity().coordinatorLayout.snackBar(getString(R.string.select_valid_state))
        else if (isBillingShow && /*billCountryId.isEmpty() || */billCountryId == "0") requireActivity().coordinatorLayout.snackBar(getString(R.string.select_valid_country))
        //else if (isBillingShow && /*billStateId.isEmpty() ||*/ billStateId == "0") requireActivity().coordinatorLayout.snackBar(getString(R.string.select_valid_state))
        else {
            bundle.putString("email", etEmail.text.toString())
            bundle.putString("first_name", etFirstName.text.toString())
            bundle.putString("last_name", etLastName.text.toString())
            bundle.putString("id_number", etIdentificationNumber.text.toString())
            bundle.putString("address_one", etAddressOne.text.toString())
            bundle.putString("address_two", etAddressTwo.text.toString())
            bundle.putString("pin", etPostCode.text.toString())
            bundle.putString("city", etCity.text.toString())
            bundle.putString("country", countryId)
            bundle.putString("state", stateId)
            bundle.putString("company", etCompany.text.toString())
            bundle.putString("mobile", etMobile.text.toString())

            bundle.putBoolean("is_different_billing", isBillingShow)
            if (isBillingShow) {
                bundle.putString("bill_first_name", etBillFirstName.text.toString())
                bundle.putString("bill_last_name", etBillLastName.text.toString())
                bundle.putString("bill_id_number", etBillIdentificationNumber.text.toString())
                bundle.putString("bill_address_one", etBillAddressOne.text.toString())
                bundle.putString("bill_address_two", etBillAddressTwo.text.toString())
                bundle.putString("bill_pin", etBillPostCode.text.toString())
                bundle.putString("bill_city", etBillCity.text.toString())
                bundle.putString("bill_country", billCountryId)
                bundle.putString("bill_state", billStateId)
                bundle.putString("bill_company", etBillCompany.text.toString())
                bundle.putString("bill_mobile", etBillMobile.text.toString())

                bundle.putString("bill_vat_number", etBillVatNumber.text.toString())
                bundle.putString("bill_home_phone", etBillMobile.text.toString())
                bundle.putString("bill_address_title", etBillMobile.text.toString())
                bundle.putString("bill_other_information", etBillMobile.text.toString())
            }

            val fragment = OrderReviewFragment(cartId, secretKey, grandTotal, "", "", "", true)
            fragment.arguments = bundle
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment).addToBackStack(fragment::class.java.simpleName).commit()
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        removeError()
        isValid = when {
            etEmail.text!!.toString().isEmpty() -> setError(telEmail)
            /*etEmail.text!!.toString().isEmpty() -> setError(telEmail)*/
            !Patterns.EMAIL_ADDRESS.matcher(etEmail.text!!).matches() -> setError(telEmail, getString(R.string.error_please_enter_valid_email))
            etFirstName.text!!.toString().isEmpty() -> setError(telFirstName)
            etLastName.text!!.toString().isEmpty() -> setError(telLastName)
            //etIdentificationNumber.text!!.toString().isEmpty() -> setError(telIdentificationNumber)
            etAddressOne.text!!.toString().isEmpty() -> setError(telAddressOne)
            etAddressTwo.text!!.toString().isEmpty() -> setError(telAddressTwo)
            //etPostCode.text!!.toString().isEmpty() -> setError(telPin)
            etCity.text!!.toString().isEmpty() -> setError(telCity)
            etCountry.text!!.toString().isEmpty() -> setError(telCountry)

            etCountry.text!!.toString() == "Italy" && etState.text!!.toString().isEmpty() -> setError(telState)
            etCountry.text!!.toString() == "Italy" && etIdentificationNumber.text!!.toString().isEmpty()-> setError(telIdentificationNumber)
            /*etCountry.text!!.toString() == "Italy" -> {
                if (etState.text!!.toString().isEmpty()) setError(telState)
                if (etIdentificationNumber.text!!.toString().isEmpty()) setError(telIdentificationNumber)
                false
            }*/
            //etState.text!!.toString().isEmpty() -> setError(telState)
            //etCompany.text!!.toString().isEmpty() -> setError(telCompany)
            etMobile.text!!.toString().isEmpty() -> setError(telMobile)
            else -> true
        }
        return isValid
    }

    private fun validateBilling(): Boolean {
        var isValid = true
        removeBillError()
        isValid = when {
            etBillFirstName.text!!.toString().isEmpty() -> setError(telIBillFirstName)
            etBillLastName.text!!.toString().isEmpty() -> setError(telIBillLastName)
            //etBillIdentificationNumber.text!!.toString().isEmpty() -> setError(telIBillIdentification)
            etBillCompany.text!!.toString().isNotEmpty() && etBillVatNumber.text!!.toString().isEmpty() -> setError(telIBillVatNumber)
            etBillAddressOne.text!!.toString().isEmpty() -> setError(telIBillAddressOne)
            //etBillAddressTwo.text!!.toString().isEmpty() -> setError(telIBillAddressTwo)
            //etBillPostCode.text!!.toString().isEmpty() -> setError(telIBillPin)
            etBillCity.text!!.toString().isEmpty() -> setError(telIBillCity)
            etBillCountry.text!!.toString().isEmpty() -> setError(telIBillCountry)
            etBillCountry.text!!.toString() == "Italy" && etBillState.text!!.toString().isEmpty() -> setError(telIBillState)
            etBillCountry.text!!.toString() == "Italy" && etBillIdentificationNumber.text!!.toString().isEmpty() -> setError(telIBillIdentification)
            //etBillState.text!!.toString().isEmpty() -> setError(telIBillState)
            //etBillCompany.text!!.toString().isEmpty() -> setError(telIBillCompany)
            etBillMobile.text!!.toString().isEmpty() -> setError(telIBillMobile)
            etBillAddressTitle.text!!.toString().isEmpty() -> setError(telIBillAddressTitle)
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
        telEmail.error = null
        telFirstName.error = null
        telLastName.error = null
        telIdentificationNumber.error = null
        telAddressOne.error = null
        telAddressTwo.error = null
        telEmail.error = null
        telPin.error = null
        telCity.error = null
        telCountry.error = null
        telState.error = null
        telCompany.error = null
        telMobile.error = null
    }

    private fun removeBillError() {
        telIBillFirstName.error = null
        telIBillLastName.error = null
        telIBillIdentification.error = null
        telIBillVatNumber.error = null
        telIBillAddressOne.error = null
        telIBillAddressTwo.error = null
        telIBillPin.error = null
        telIBillCity.error = null
        telIBillCountry.error = null
        telIBillState.error = null
        telIBillCompany.error = null
        telIBillMobile.error = null
    }

    private fun getCountry() {
        Coroutines.main {
            try {
                val countryResponse: CountryRespnse = repository.getCountry()
                if (isAdded && isVisible) {
                    countryResponse.let {
                        if (countryResponse.status == 1) {
                            Constant.countryList = countryResponse.result
                            for (i in Constant.countryList) {
                                if (i.name == "Italy") {
                                    countryId = i.id.toString()
                                    getState(countryId)
                                }
                            }
                        } else Constant.countryList = emptyList()
                        return@main
                    }
                }
            } catch (e: Exception) {
                requireActivity().onException(e)
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
