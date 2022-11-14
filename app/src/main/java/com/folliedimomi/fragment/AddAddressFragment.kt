package com.folliedimomi.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi.activity.MainActivity
import com.folliedimomi.dialog.CountryDialog
import com.folliedimomi.dialog.StateDialog
import com.folliedimomi.model.AddAddressResponse
import com.folliedimomi.model.CountryRespnse
import com.folliedimomi.model.CountryState
import com.folliedimomi.model.StateRespnse
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_address.*
import okhttp3.RequestBody
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class AddAddressFragment(onAddressAddedListener: OnAddressAddedListner) : Fragment(),
    CountryDialog.ProductInterface, StateDialog.StateInterface, KodeinAware {
    override val kodein: Kodein by kodein()

    private val repository: NetworkRepository by instance()
    private val mActivity: MainActivity by instance()
    private val session: Session by instance()
    private var countryId = "10"
    private var stateId = "0"
    private var onAddressAddedListeners: OnAddressAddedListner? = null

    init {
        onAddressAddedListeners = onAddressAddedListener
    }

    override fun onStateClicked(stateId: String, stateName: String) {
        if (isAdded && isVisible) {
            this.stateId = stateId
            etState.setText(stateName)
        }
    }

    override fun onCountryClicked(countryId: String, countryName: String) {
        if (isAdded && isVisible) {
            if (countryName != "Italy") {
                telState.hide()
                telIdentification.hide()
            } else {
                telState.show()
                telIdentification.show()
            }

            this.countryId = countryId
            etCountry.setText(countryName)
            Constant.stateList = emptyList()
            etState.setText("")
            stateId = "0"
            if (countryName == "Italy") getState(countryId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAddAddress.setOnClickListener {
            if (validate()) onAddAddress()
        }

        etCountry.setText(Constant.italy)
        etCountry.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (Constant.countryList.isNotEmpty()) {
                    Constant.countryListDialog = CountryDialog(this@AddAddressFragment)
                    if (!Constant.countryListDialog!!.isVisible) {
                        Constant.countryListDialog!!.show(
                            this@AddAddressFragment.activity!!.supportFragmentManager,
                            "Country search dialog"
                        )
                    }
                }
            }
            true
        }

        etState.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (Constant.stateList.isNotEmpty()) {
                    Constant.stateListDialog = StateDialog(this@AddAddressFragment)
                    if (!Constant.stateListDialog!!.isVisible) {
                        Constant.stateListDialog!!.show(
                            this@AddAddressFragment.activity!!.supportFragmentManager,
                            "State search dialog"
                        )
                    }
                } else {
                    requireActivity().coordinatorLayout.snackBar(getString(R.string.does_not_have_any_state))
                }
            }
            true
        }

//        getCountry()
        Constant.countryList = emptyList()
        val coun: ArrayList<CountryState> = arrayListOf()
        CountryState("Italy",10)
        Constant.countryList = coun as List<CountryState>
        //getState(countryId)
    }

    private fun onAddAddress() {
        Coroutines.main {
            if (countryId.isEmpty() || countryId == "0") requireActivity().coordinatorLayout.snackBar(
                getString(R.string.select_valid_country)
            )
            //else if (stateId.isEmpty() || stateId == "0") requireActivity().coordinatorLayout.snackBar(getString(R.string.select_valid_state))
            else {
                requireActivity().progress_bars_layout.show()
                val mMap = HashMap<String, RequestBody>()
                mMap["controller"] = "mobileapi".convertBody()
                mMap["op"] = "add_customer_address".convertBody()
                mMap["id_customer"] = session.getUserId().toString().convertBody()
                mMap["id_state"] = stateId.convertBody()
                mMap["id_country"] = countryId.convertBody()
                mMap["alias"] = etIdentificationNumber.text.toString().convertBody()
                mMap["firstname"] = etFirstName.text.toString().convertBody()
                mMap["lastname"] = etLastName.text.toString().convertBody()
                mMap["vat_number"] = etVatNumber.text.toString().convertBody()

                mMap["address1"] = etAddressOne.text.toString().convertBody()
                mMap["address2"] = etAddressTitle.text.toString().convertBody()
                mMap["postcode"] = etPostCode.text.toString().convertBody()

                mMap["city"] = etCity.text.toString().convertBody()

                mMap["phone"] = etPhone.text.toString().convertBody()
                mMap["phone_mobile"] = etMobile.text.toString().convertBody()
                mMap["company"] = etCompany.text.toString().convertBody()
                mMap["shop_id"] = session.getShopId().toString().convertBody()
                mMap["lang_id"] = Constant.LANG.convertBody()

                try {
                    val addAddressResponse: AddAddressResponse = repository.addAddress(mMap)
                    if (isAdded && isVisible) {
                        requireActivity().progress_bars_layout.hide()
                        addAddressResponse?.let {
                            if (addAddressResponse.status == 1) {
                                onAddressAddedListeners!!.onAddressAdded()
                                requireActivity().supportFragmentManager.popBackStackImmediate()
                            } else requireActivity().coordinatorLayout.snackBar(addAddressResponse.message)
                            return@main
                        }
                    }
                } catch (e: Exception) {
                    requireActivity().onException(e)
                }
            }
        }
    }

    private fun setError(
        tel: TextInputLayout,
        msg: String = getString(R.string.required)
    ): Boolean {
        tel.isErrorEnabled = true
        tel.error = msg
        return false
    }

    private fun validate(): Boolean {
        var isValid = true
        removeBillError()
        isValid = when {


            /*etBillAddressOne.text!!.toString().isEmpty() -> setError(telIBillAddressOne)
            //etBillAddressTwo.text!!.toString().isEmpty() -> setError(telIBillAddressTwo)
            //etBillPostCode.text!!.toString().isEmpty() -> setError(telIBillPin)
            etBillCity.text!!.toString().isEmpty() -> setError(telIBillCity)
            etBillCountry.text!!.toString().isEmpty() -> setError(telIBillCountry)
            etBillState.text!!.toString().isEmpty() -> setError(telIBillState)
            //etBillCompany.text!!.toString().isEmpty() -> setError(telIBillCompany)
            etBillMobile.text!!.toString().isEmpty() -> setError(telIBillMobile)
            etBillAddressTitle.text!!.toString().isEmpty() -> setError(telIBillAddressTitle)*/


            //etEmail.text!!.toString().isEmpty() -> setError(telEmail)
            /*etEmail.text!!.toString().isEmpty() -> setError(telEmail)*/
            //!Patterns.EMAIL_ADDRESS.matcher(etEmail.text!!).matches() -> setError(telEmail, getString(R.string.error_please_enter_valid_email))
            etFirstName.text!!.toString().isEmpty() -> setError(telFirstName)
            etLastName.text!!.toString().isEmpty() -> setError(telLastName)
            //etIdentificationNumber.text!!.toString().isEmpty() -> setError(telIdentification)
            //etIdentificationNumber.text!!.toString().isEmpty() -> setError(telIdentification)
            etCompany.text!!.toString().isNotEmpty() && etVatNumber.text!!.toString()
                .isEmpty() -> setError(telVatNumber)
            etAddressOne.text!!.toString().isEmpty() -> setError(telAddressOne)
            //etAddressTwo.text!!.toString().isEmpty() -> setError(telAddressTwo)
            //etPostCode.text!!.toString().isEmpty() -> setError(telPin)
            etCity.text!!.toString().isEmpty() -> setError(telCity)
            etCountry.text!!.toString().isEmpty() -> setError(telCountry)
            etCountry.text!!.toString() == "Italy" && etState.text!!.toString()
                .isEmpty() -> setError(telState)
            etCountry.text!!.toString() == "Italy" && etIdentificationNumber.text!!.toString()
                .isEmpty() -> setError(telIdentification)

            /*etCountry.text!!.toString() == "Italy" -> {
                 if (etState.text!!.toString().isEmpty()) setError(telState)
                if (etIdentificationNumber.text!!.toString().isEmpty())  setError(telIdentification)
                //requireActivity().coordinatorLayout.snackBar(getString(R.string.select_valid_country))
                false
            }*/
            //etState.text!!.toString().isEmpty() -> setError(telState)
            //etCompany.text!!.toString().isEmpty() -> setError(telCompany)
            etMobile.text!!.toString().isEmpty() -> setError(telMobile)
            etAddressTitle.text!!.toString().isEmpty() -> setError(telAddressTitle)
            else -> true
        }
        return isValid
    }

    private fun removeBillError() {
        telFirstName.error = null
        telLastName.error = null
        telIdentification.error = null
        telAddressOne.error = null
        telAddressTwo.error = null
        telCity.error = null
        telCountry.error = null
        telState.error = null
        telCompany.error = null
        telVatNumber.error = null
        telMobile.error = null
        telAddressTitle.error = null
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_address, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val item: MenuItem = menu!!.findItem(R.id.action_cart)
        val search: MenuItem = menu!!.findItem(R.id.action_search)
        item.isVisible = false
        search.isVisible = false
    }

    interface OnAddressAddedListner {
        fun onAddressAdded()
    }

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }
}
