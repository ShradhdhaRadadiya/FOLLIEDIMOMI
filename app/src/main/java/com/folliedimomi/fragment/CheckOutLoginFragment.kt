package com.folliedimomi.fragment

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.folliedimomi.R
import com.folliedimomi._app.loadFragment
import com.folliedimomi._app.loadFragmentWithoutBackStack
import com.folliedimomi.model.LoginResponse
import com.folliedimomi.model.LoginUser
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.*
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_check_out_login.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class CheckOutLoginFragment(mCartId: String, mSecretKey: String, mGrandTotal: String) :
    Fragment(R.layout.fragment_check_out_login), View.OnClickListener, KodeinAware {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()
    private val session: Session by instance()
    var cartId: String = ""
    var secretKey: String = ""
    var grandTotal: String = ""

    init {
        cartId = mCartId
        grandTotal = mGrandTotal
        secretKey = mSecretKey
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogin.setOnClickListener(this)
        tvCreateNewAccount.setOnClickListener(this)
        tvForgotPassword.setOnClickListener(this)
        btnGuest.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnLogin -> {
                Log.e("TAG", "EMAIL IS------> ${etEmail.text.toString()}")

                if (validate()) onLogin(etEmail.text.toString(), etPassword.text.toString())
            }
            R.id.tvForgotPassword -> {
                requireActivity().loadFragment(ForgotPasswordAsGuestFragment())
            }
            R.id.tvCreateNewAccount -> requireActivity().loadFragmentWithoutBackStack(
                CheckOutRegisterFragment(cartId, secretKey, grandTotal)
            )
            R.id.btnGuest -> requireActivity().loadFragmentWithoutBackStack(
                CheckOutAsGuestFragment(
                    cartId,
                    secretKey,
                    grandTotal
                )
            )
            else -> {}//coorcontext!!.showToast("Something wrong clicked")
        }
    }

    private fun onLogin(email: String, pass: String) {
        requireActivity().progress_bars_layout.show()
        Coroutines.main {
            try {

//                val loginResponse: LoginResponse = repository.onLogin("carlolvr@gmail.com", "ytZYDUmI")
                val loginResponse: LoginResponse = repository.onLogin(email, pass)
                if (isAdded && isVisible) {
                    loginResponse.let {
                        if (loginResponse.status == 1) {
                            val user = loginResponse.result
                            setSessionUser(user)
                            onLoginComplete()
                        } else {
                            requireActivity().coordinatorLayout.snackBar(loginResponse.message!!)
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

    private fun onLoginComplete() {
        requireActivity().loadFragmentWithoutBackStack(
            OrderAddressFragment(
                cartId,
                secretKey,
                grandTotal
            )
        )
    }

    private fun validate(): Boolean {
        var isValid = true
        telEmail.error = null
        telPassword.error = null
        isValid = when {
            etEmail.text!!.toString().isEmpty() -> setError(
                telEmail,
                getString(R.string.error_please_enter_email)
            )
            !Patterns.EMAIL_ADDRESS.matcher(etEmail.text!!).matches() -> setError(
                telEmail,
                getString(R.string.error_please_enter_valid_email)
            )
            etPassword.text!!.toString().isEmpty() -> setError(
                telPassword,
                getString(R.string.error_please_enter_password)
            )
            etPassword.text!!.toString().length < 5 -> setError(
                telPassword,
                getString(R.string.error_password_length)
            )
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

    private fun setSessionUser(user: LoginUser) {
        session.setServerLoggedIn(true)
        session.setUserId(user.idCustomer.toString())
        session.setUserName("${user.firstname}  ${user.lastname}")
        session.setUserEmail(user.email)
        session.setSecureKey(user.secureKey)
        session.setShopId(user.idShop.toString())
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

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }
}
