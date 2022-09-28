package com.folliedimomi.fragment

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi.dialog.CountryDialog
import com.folliedimomi.model.CountryRespnse
import com.folliedimomi.model.RegisterResponse
import com.folliedimomi.model.StateRespnse
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.utils.*
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.JsonSyntaxException
import com.folliedimomi.dialog.StateDialog
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.fragment_register.*

import okhttp3.RequestBody
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.IOException

class RegisterFragment : Fragment(), KodeinAware, CountryDialog.ProductInterface, StateDialog.StateInterface {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private var countryId = "10"
    private var stateId = "0"

    override fun onCountryClicked(countryId: String, countryName: String) {
        if (isAdded && isVisible) {
            if (countryName != "Italy") {
                stateId = "0"
                telState.hide()
                telHouseNo.hide()
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
        //etCountry.setText(Constant.italy)
        etCountry.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (Constant.countryList.isNotEmpty()) {
                    Constant.countryListDialog = CountryDialog(this@RegisterFragment)
                    if (!Constant.countryListDialog!!.isVisible) {
                        Constant.countryListDialog!!.show(this@RegisterFragment.activity!!.supportFragmentManager, "Country search dialog")
                        Constant.stateList = emptyList()
                    }
                }
            }
            true
        }

        etState.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (Constant.stateList.isNotEmpty()) {
                    Constant.stateListDialog = StateDialog(this@RegisterFragment)
                    if (!Constant.stateListDialog!!.isVisible) {
                        Constant.stateListDialog!!.show(this@RegisterFragment.activity!!.supportFragmentManager, "State search dialog")
                    }
                } else {
                    requireActivity().coordinator.
                    snackBar(getString(R.string.does_not_have_any_state))
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
            requireActivity().progress_bars_layout.show()
            if (countryId.isEmpty() || countryId == "0") requireActivity().coordinator.snackBar(getString(R.string.select_valid_country))
            //else if (stateId.isEmpty() || stateId == "0") requireActivity().coordinator.snackBar(getString(R.string.select_valid_state))
            else {
                val mMap = HashMap<String, RequestBody>()
                mMap["controller"] = "mobileapi".convertBody()
                mMap["op"] = "customer_create".convertBody()
                mMap["firstname"] = etName.text.toString().convertBody()
                mMap["lastname"] = etSurname.text.toString().convertBody()
                mMap["email"] = etEmail.text.toString().convertBody()
                mMap["passwd"] = etPass.text.toString().convertBody()

                mMap["address1"] = etAddress.text.toString().convertBody()
                mMap["postcode"] = etPin.text.toString().convertBody()
                mMap["city"] = etCity.text.toString().convertBody()
                mMap["phone"] = etTelephone.text.toString().convertBody()
                mMap["lang_id"] = "1".convertBody()
                mMap["shop_id"] = "1".convertBody()
                mMap["id_country"] = countryId.convertBody()
                mMap["phone_mobile"] = "12542114666".convertBody()

               /* mMap["vat_number"] = "".convertBody()
                mMap["id_state"] = stateId.convertBody()
                mMap["id_social"] = "1".convertBody()
                mMap["id_number"] = etHouseNo.text.toString().convertBody()*/

                try {
                    val registerResponse: RegisterResponse = repository.onRegister(mMap)
                    if (isAdded && isVisible) {
                        registerResponse.let {
                            requireActivity().progress_bars_layout.hide()
                            if (registerResponse.status == 1) {
                                //val registerResponse : RegisterUser = registerResponse.result
                                requireActivity().toast("Grazie per esserti registrato adesso puoi accedere con le tue credenziali")
                                requireActivity().supportFragmentManager.popBackStackImmediate()
                            } else {
                                requireActivity().coordinator.snackBar(registerResponse.message)
                            }
                            return@main
                        }
                    }
                } catch (e: ApiException) {
                    requireActivity().progress_bars_layout.hide()
                    requireActivity().coordinator.snackBar(e.message!!)
                } catch (e: JsonSyntaxException) {
                    requireActivity().progress_bars_layout.hide()
                    requireActivity().coordinator.snackBar(e.message!!)
                } catch (e: NoInternetException) {
                    requireActivity().progress_bars_layout.hide()
                    requireActivity().coordinator.snackBar(e.message!!)
                } catch (e: IOException) {
                    requireActivity().progress_bars_layout.hide()
                    requireActivity().coordinator.snackBar(e.message!!)
                }
            }
        }
    }

    private fun getCountry() {
        Coroutines.main {
            try {
                val countryResponse: CountryRespnse = repository.getCountry()
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
            } catch (e: ApiException) {
                //requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: JsonSyntaxException) {
                //requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: NoInternetException) {
                //requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: IOException) {
                //requireActivity().coordinatorLayout.snackBar(e.message!!)
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
            } catch (e: ApiException) {
                //requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: JsonSyntaxException) {
                //requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: NoInternetException) {
                //requireActivity().coordinatorLayout.snackBar(e.message!!)
            } catch (e: IOException) {
                //requireActivity().coordinatorLayout.snackBar(e.message!!)
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

/*            etCountry.text!!.toString() == "Italy" -> {
                if (etState.text!!.toString().isEmpty()) setError(telState)
                if (etHouseNo.text!!.toString().isEmpty()) setError(telHouseNo)
                //requireActivity().coordinator.snackBar(getString(R.string.select_valid_country))
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

    private fun removeError()  {
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
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

}
