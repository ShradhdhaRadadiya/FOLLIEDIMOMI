package com.folliedimomi.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.folliedimomi.R
import com.folliedimomi._app.showToast
import com.folliedimomi.model.LoginResponse
import com.folliedimomi.model.LoginUser
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.fragment_login.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.IOException

class LoginFragment : Fragment(), View.OnClickListener, KodeinAware {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogin.setOnClickListener(this)
        tvCreateNewAccount.setOnClickListener(this)
        tvForgotPassword.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnLogin ->
            {
                Log.e("TAG","EMAIL IS------> ${etEmail.text.toString()}")
                if (validate()) onLogin(etEmail.text.toString(), etPassword.text.toString())

            }

            R.id.tvForgotPassword -> listener!!.onLoadForgotPassword()
            R.id.tvCreateNewAccount -> listener!!.onLoadRegister()
            else -> requireContext().showToast("Something wrong clicked")
        }
    }

    private fun onLogin(email: String, pass: String) {
        requireActivity().progress_bars_layout.show()
        Coroutines.main {
            try {
                Log.e("TAG","EMAIL IS- onLogin-----> ${etEmail.text}")

//                val loginResponse: LoginResponse = repository.onLogin("carlolvr@gmail.com", "ytZYDUmI")
                val loginResponse: LoginResponse = repository.onLogin(email, pass)
                if (isAdded && isVisible){loginResponse.let {
                    if (loginResponse.status == 1) {
                        val user = loginResponse.result
                        setSessionUser(user)
                        listener!!.onLoginComplete()
                    } else {
                        coordinator.snackBar(loginResponse.message!!)
                    }
                    requireActivity().progress_bars_layout.hide()
                    return@main
                }}
            } catch (e: ApiException) {
                requireActivity().progress_bars_layout.hide()
                coordinator.snackBar(e.message!!)
            } catch (e: NoInternetException) {
                requireActivity().progress_bars_layout.hide()
                coordinator.snackBar(e.message!!)
            } catch (e: IOException) {
                requireActivity().progress_bars_layout.hide()
                coordinator.snackBar(e.message!!)
            }
        }
    }

    private fun setSessionUser(user: LoginUser) {
        context?.let {
            Session(it).setServerLoggedIn(true)
            Session(it).setUserId(user.idCustomer.toString())
            Session(it).setUserName("${user.firstname}  ${user.lastname}")
            Session(it).setUserEmail(user.email)
            Session(it).setSecureKey(user.secureKey)
            Session(it).setShopId(user.idShop.toString())
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        when {
            etEmail.text!!.toString().isEmpty() -> {
                telEmail.isErrorEnabled = true
                telEmail.error = getString(R.string.error_please_enter_email)
                isValid = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(etEmail.text!!).matches() -> {
                telEmail.isErrorEnabled = true
                telEmail.error = getString(R.string.error_please_enter_valid_email)
                isValid = false
            }
            etPassword.text!!.toString().isEmpty() -> {
                telEmail.error = null
                telPassword.isErrorEnabled = true
                telPassword.error = getString(R.string.error_please_enter_password)
                isValid = false
            }
            etPassword.text!!.toString().length < 5 -> {
                telEmail.error = null
                telPassword.isErrorEnabled = true
                telPassword.error = getString(R.string.error_password_length)
                isValid = false
            }
            else -> {
                telEmail.error = null
                telPassword.error = null
            }
        }
        return isValid
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onLoginComplete()
        fun onLoadRegister()
        fun onLoadForgotPassword()
    }


}
