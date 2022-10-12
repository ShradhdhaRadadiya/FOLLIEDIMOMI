package com.folliedimomi.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.logD(msg: String, tag: String = "OkHttp") {
    //if (BuildConfig.DEBUG) Log.d(tag, msg)
//    if (BuildConfig.DEBUG) Log.d(this::class.java.simpleName, msg)
    Log.d(this::class.java.simpleName, msg)
}

fun Context.logI(msg: String, tag: String = "OkHttp") {
//    if (BuildConfig.DEBUG) Log.i(tag, msg)
    Log.i(tag, msg)
    //if (BuildConfig.DEBUG) Log.d(this::class.java.simpleName, debugMessage)
}

fun String.convertBody(): RequestBody {
    return this.toRequestBody("text/plain".toMediaTypeOrNull())
}

fun Activity.onException(ex: Exception) {
    when (ex) {
        is ApiException, is JsonSyntaxException, is NoInternetException, is IOException, is IllegalAccessException, is IndexOutOfBoundsException -> {
            progress_bars_layout.hide()
            coordinatorLayout.snackBar(ex.message!!)
        }
        else -> throw ex
    }
}

fun Activity.onExceptionLog(ex: Exception) {
    when (ex) {
        is ApiException, is JsonSyntaxException, is NoInternetException, is IOException, is IllegalAccessException, is IndexOutOfBoundsException -> {
            progress_bars_layout.hide()
            logI(ex.message!!)
        }
        else -> throw ex
    }
}

fun Activity.exception(e: Exception): String {
    //if (e as  ApiException::class)
    try {

    } catch (e: ApiException) {
        return e.message.toString()
    } catch (e: JsonSyntaxException) {
        return e.message.toString()
    } catch (e: NoInternetException) {
        return e.message.toString()
    } catch (e: IOException) {
        return e.message.toString()
    }
    return ""
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun LinearLayout.show() {
    Coroutines.main {
        visibility = View.VISIBLE
    }
}

fun LinearLayout.hide() {
    Coroutines.main {
        visibility = View.GONE
    }
}

fun View.show() {
    Coroutines.main {
        visibility = View.VISIBLE
    }
}

fun View.hide() {
    Coroutines.main {
        visibility = View.GONE
    }
}

fun View.slidUp() {
    Coroutines.main {
        visibility = View.VISIBLE
        val animate = TranslateAnimation(0f, 0f, height.toFloat(), 0f)
        animate.duration = 100
        animate.fillAfter = true
        startAnimation(animate)
    }
}

fun View.slidDown() {
    Coroutines.main {
        val animate = TranslateAnimation(0f, 0f, 0f, height.toFloat())
        animate.duration = 100
        animate.fillAfter = true
        startAnimation(animate)
    }
}

fun View.snackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also {
        /*snackbar ->
               snackbar.setAction("Ok") {
                   snackbar.dismiss()
               }*/
    }.show()
}

fun View.snackbarAction(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}
