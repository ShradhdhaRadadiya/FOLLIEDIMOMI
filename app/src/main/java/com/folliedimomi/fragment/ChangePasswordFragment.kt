package com.folliedimomi.fragment


import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.folliedimomi.R
import com.folliedimomi.model.ForgotPasswordResponse
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.utils.*
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.fragment_change_password.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.IOException

class ChangePasswordFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val repository: NetworkRepository by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogin.setOnClickListener {
            if (validate()) onSendEmail(etEmail.text.toString())
        }
        tvCreateNewAccount.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }
    }

    private fun onSendEmail(email: String) {
        //requireActivity().coordinator.snackBar("Under development")
        onChangePassword(email)
    }

    private fun onChangePassword(email: String) {
        Coroutines.main {
            requireActivity().progress_bars_layout.show()

            try {
                val registerResponse: ForgotPasswordResponse =
                    repository.onForgotPassword(email = email)
                if (isAdded && isVisible) {
                    registerResponse.let {
                        if (registerResponse.status == 1) {
                            requireActivity().progress_bars_layout.hide()
                            //val registerResponse : RegisterUser = registerResponse.result
                            requireActivity().coordinator.snackBar("L'email di recupero password è stata inviata con successo")
                            //requireActivity().supportFragmentManager.popBackStackImmediate()
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


    private fun validate(): Boolean {
        telEmail.error = null
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
            else -> {
                telEmail.error = null
            }
        }
        return isValid
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }
}
