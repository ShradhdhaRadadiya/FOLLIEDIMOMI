package com.folliedimomi.bottom_sheet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.folliedimomi.R
import com.folliedimomi._app.Constant
import com.folliedimomi._app.getRandomString
import com.folliedimomi._app.loadFragment
import com.folliedimomi.activity.MainActivity
import com.folliedimomi.activity.SplashActivity
import com.folliedimomi.fragment.*
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.hide
import com.folliedimomi.utils.show
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.sheet_accounts.*

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class AccountSheet : BottomSheetDialogFragment(), KodeinAware{
    override val kodein: Kodein by kodein()
    private val session: Session by instance()
    private val mActivity: MainActivity by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (session.isServerLoggedIn()) {
            tvLogout.show()
            vLogout.show()
            tvLogin.hide()
            vLogin.hide()
        } else {
            tvLogin.show()
            vLogin.show()
            tvLogout.hide()
            vLogout.hide()
        }

        tvLogin.setOnClickListener {
            dismiss()
            //requireActivity().loadFragment(LoginFragment())
            //Session(this).logOut()
            Constant.isRunning = true
            requireActivity().finish()
            loadLoginScreen()

            requireActivity().loadFragment(LoginFragment())
        }

        tvLogout.setOnClickListener {
            MainActivity.cartCount = 0
            mActivity.updateCount(requireContext(), MainActivity.cartCount)
            session.logOut()
            session.setAppSession(String().getRandomString(14))
            Constant.isRunning = true
            try{
                requireActivity().llUserHeader.hide()
            }catch (e:Exception){

            }
            tvLogout.hide()
            vLogout.hide()
            tvLogin.show()
            vLogin.show()
            dismiss()
//            requireActivity().finish()
//            requireActivity().loadFragment(LoginFragment())
        }
//com.braintreepayments.api.exceptions.BraintreeException: BraintreeBrowserSwitchActivity missing,
// incorrectly configured in AndroidManifest.xml or another app defines the same browser switch url
// as this app. See https://developers.braintreepayments.com/guides/client-sdk/android/#browser-switch
// for the correct configuration
        tvMyAccount.setOnClickListener {
            dismiss()
            if (session.isServerLoggedIn()) {
                requireActivity().loadFragment(MyAccountFragment())
                dismiss()
            } else {
                Constant.isRunning = true
                loadLoginScreen()
            }
        }

        tvAddress.setOnClickListener {
            dismiss()
            if (session.isServerLoggedIn()) {
                requireActivity().loadFragment(AddressFragment())
            } else {
                Constant.isRunning = true
                loadLoginScreen()
            }
        }
//        https://cipri-couture.com/it/dove-si-trova-il-mio-pacco
        tvOrderTracking.setOnClickListener {
            requireActivity().loadFragment(WebPageFragment("${Constant.URL}it/dove-si-trova-il-mio-pacco"))
            dismiss()
        }

        tvCreditNote.setOnClickListener {
            if (session.isServerLoggedIn()) {
                requireActivity().loadFragment(OrderListFragment())
                dismiss()
            } else {
                Constant.isRunning = true
                loadLoginScreen()
            }
        }
    }

    private fun loadLoginScreen() {
        val intent: Intent? = Intent(requireActivity(), SplashActivity::class.java)
        startActivity(intent)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.sheet_accounts, container, false)
    }

    companion object {
        //private var mInterface: AddedPayment? = null
        // private var session: Session? = null
        fun getInstance(/*mSession: Session*/): AccountSheet = AccountSheet().apply {
            //session = mSession
            //mInterface = myInterface
            //orderId = myOrderId
            // pendingPayment = mPendingPayment
        }
    }

    /*interface AddedPayment {
        fun onPaymentAdded()
    }*/

    @SuppressLint("UseRequireInsteadOfGet")
    fun showError(msg: String) {
        if (isAdded && isVisible) {
            tvError.text = msg
            tvError.setTextColor(ContextCompat.getColor(this@AccountSheet.activity!!, R.color.white))
            tvError.visibility = View.VISIBLE
            android.os.Handler().postDelayed({
                if (tvError != null) {
                    tvError.visibility = View.GONE
                }
            }, 2000)
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    fun showSuccess(msg: String) {
        if (isAdded && isVisible) {
            tvError.text = msg
            tvError.setTextColor(ContextCompat.getColor(this@AccountSheet.activity!!, R.color.black))
            tvError.visibility = View.VISIBLE
            android.os.Handler().postDelayed({
                if (tvError != null) {
                    tvError.visibility = View.GONE
                }
            }, 2000)
        }
    }



}