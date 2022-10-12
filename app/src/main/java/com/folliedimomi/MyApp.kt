package com.folliedimomi

import android.app.Application
import com.folliedimomi._app.FontOverride
import com.folliedimomi.activity.MainActivity
import com.folliedimomi.network.MyApi
import com.folliedimomi.network.NetworkConnectionInterceptor
import com.folliedimomi.network.NetworkRepository
import com.folliedimomi.sharedPrefrense.Session
import com.folliedimomi.utils.ResourceProvider
import com.stripe.android.PaymentConfiguration
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

open class MyApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApp))
        bind() from provider { ResourceProvider(instance()) }
        bind() from singleton { NetworkConnectionInterceptor(this@MyApp) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { NetworkRepository(instance()) }
        bind() from provider { MainActivity() }
        bind() from provider { Session(this@MyApp) }
    }

    override fun onCreate() {
        super.onCreate()
        //MyApp = this;
        FontOverride().setDefaultFont(this, "SANS", "fonts/Montserrat-Bold.ttf")
        FontOverride().setDefaultFont(this, "MONOSPACE", "fonts/Montserrat-SemiBold.ttf")
        //FontOverride().setDefaultFont(this, "NORMAL", "fonts/Montserrat-Medium.ttf")
        FontOverride().setDefaultFont(this, "SERIF", "fonts/Montserrat-Light.ttf")

        /*Stripe configuration*/

        //public key: pk_live_nJaGx7PaeZHqibzdNjcHav9m
        //secret key: sk_live_6gzcliXAzt9Hvw8iWVi6LjNE

//        PaymentConfiguration.init(applicationContext, "pk_test_FUICbeGYba2jsJ5eaFZdtQHe")
        PaymentConfiguration.init(applicationContext, "pk_live_nJaGx7PaeZHqibzdNjcHav9m")
//        PaymentConfiguration.init(applicationContext, "pk_test_QqwOYzHMMYnYzhqeeIDLvlHF00znOw26EO")
    }
}