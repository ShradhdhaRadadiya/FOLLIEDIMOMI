package com.folliedimomi._app

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.folliedimomi.R
import com.folliedimomi.activity.SplashActivity


object Utils {

    //companion object {
    var pDialog: ProgressDialog? = null
    //}

    fun showProgressDialog(activity: Activity) {
        pDialog = ProgressDialog(activity, R.style.progressDialog)
        pDialog!!.setCancelable(false)
        pDialog!!.setTitle(activity.resources.getString(R.string.app_name))
        pDialog!!.setMessage(activity.getString(R.string.please_wait))
        pDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar_Small)
        pDialog!!.show()
    }

    fun hideProgressDialog(activity: Activity) {
        pDialog?.let {
            if (pDialog!!.isShowing) {
                pDialog!!.dismiss()
            }
        }
    }

    fun internetDialog(activity: Activity) {
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setMessage("No internet connection on your device. Please turn it on and try again.")
            .setTitle("No Internet Connection")
            .setCancelable(false)
            .setPositiveButton(" Try Again ") { dialog, _ ->
                dialog.dismiss()
                if (activity.isNetworkAvailable()) {
                    val intent = Intent(activity.applicationContext, SplashActivity::class.java)
                    activity.startActivity(intent)
                    activity.finish()
                } else {
                    internetDialog(activity)
                }
                /** for open setting menu  */
                /*Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(dialogIntent);*/
            }
        alertDialogBuilder.setNegativeButton(" Cancel ") { dialog, _ ->
            dialog.dismiss()
            activity.finish()
        }
        val alert = alertDialogBuilder.create()
        alert.show()
    }

}