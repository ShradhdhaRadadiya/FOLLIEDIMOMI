package com.folliedimomi.fragment


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.folliedimomi.R
import com.folliedimomi.model.UpdateProfile
import com.folliedimomi.model.UpdateProfileResponse
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.android.material.textfield.TextInputLayout

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.RequestBody
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private val session: Session by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val str = session.getUserName().toString().trim().split("  ")
        if (str.isNotEmpty()) etFirstName.setText(str[0])
        if (str.size > 1) etLastName.setText(str[1])
        etCompany.setText(session.getCompany().toString())
        etMobile.setText(session.getMobile().toString())
        etEmail.setText(session.getUserEmail().toString())

        btnUpdateProfile.setOnClickListener {
            //requireActivity().coordinatorLayout.snackBar("Update profile")
            //if (validate())
            onUpdateProfile()
        }
    }

    private fun onUpdateProfile() {
        Coroutines.main {
            requireActivity().progress_bars_layout.show()

            val mMap = HashMap<String, RequestBody>()
            mMap["controller"] = "customerapi".convertBody()
            mMap["op"] = "update_customer_profile".convertBody()
            mMap["id_customer"] = session.getUserId().toString().convertBody()
            mMap["email"] = etEmail.text.toString().convertBody()
            mMap["company"] = etCompany.text.toString().convertBody()
            mMap["firstname"] = etFirstName.text.toString().convertBody()
            mMap["lastname"] = etLastName.text.toString().convertBody()
            mMap["phone"] = etMobile.text.toString().convertBody()
            mMap["id_social"] = "1".convertBody()


            try {
                val updateProfileResponse: UpdateProfileResponse = repository.onUpdateProfile(mMap)
                if (isAdded && isVisible) {
                    updateProfileResponse.let {
                        if (updateProfileResponse.status == 1) {
                            val user = updateProfileResponse.result
                            setSessionUser(user)
                            //requireActivity().coordinatorLayout.snackBar(updateProfileResponse.message)
                            requireActivity().progress_bars_layout.hide()
                            requireActivity().supportFragmentManager.popBackStackImmediate()
                            return@main
                        } else {
                            requireActivity().progress_bars_layout.hide()
                            requireActivity().coordinatorLayout.snackBar(updateProfileResponse.message)
                        }
                    }
                }
            } catch (ex: Exception) {
                requireActivity().onException(ex)
            }
        }
    }

    private fun setSessionUser(user: UpdateProfile) {
        session.setServerLoggedIn(true)
        session.setUserId(user.idCustomer.toString())
        session.setUserName("${user.firstname}  ${user.lastname}")
        session.setUserEmail(user.email)
        session.setSecureKey(user.secureKey)
        session.setShopId(user.idShop.toString())
        session.setCompany(etCompany.text.toString())
        session.setMobile(etMobile.text.toString())
    }

    private fun validate(): Boolean {
        var isValid = true
        telFirstName.error = null
        telLastName.error = null
        telCompany.error = null
        telMobile.error = null
        isValid = when {
            etFirstName.text!!.toString().isEmpty() -> setError(telFirstName)
            etLastName.text!!.toString().isEmpty() -> setError(telLastName)
            etCompany.text!!.toString().isEmpty() -> setError(telCompany)
            etMobile.text!!.toString().isEmpty() -> setError(telMobile)
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
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
