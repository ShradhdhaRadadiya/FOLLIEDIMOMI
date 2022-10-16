package com.folliedimomi.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.folliedimomi.R
import com.folliedimomi.fragment.ChangePasswordFragment
import com.folliedimomi.fragment.LoginFragment
import com.folliedimomi.fragment.RegisterFragment
import com.folliedimomi.fragment.SplashFragment
import com.folliedimomi.sharedPrefrense.Session

class SplashActivity : AppCompatActivity(), SplashFragment.OnFragmentInteractionListener,
    LoginFragment.OnFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (Session(this).isServerLoggedIn()) {
            showDashboard()
        } else {
            loadLoginFragment()
        }

    }

    //carlolvr@gmail.com
//    rTUg1Y8y

    override fun onSplashComplete() {
        if (Session(this).isServerLoggedIn()) {
            showDashboard()
        } else {
            loadLoginFragment()
        }
    }

    private fun showDashboard() {
        val intent: Intent? = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loadLoginFragment() {
//        loadFragment(LoginFragment())
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerSplashLayout, LoginFragment()).commit()
    }

    override fun onLoginComplete() {
        showDashboard()
    }


    override fun onLoadRegister() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerSplashLayout, RegisterFragment())
            .addToBackStack("RegisterFragment").commit()
    }

    override fun onLoadForgotPassword() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerSplashLayout, ChangePasswordFragment())
            .addToBackStack("ForgotPasswordFragment").commit()
        //this.showToast("Forgot password Clicked")
    }
}