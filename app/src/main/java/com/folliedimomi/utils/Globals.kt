package com.folliedimomi.utils

import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD

object Globals {
    var progressFlower: KProgressHUD? = null

    //show progress dialog
    fun showProgress(context: Context) {
        progressFlower = ProgressUtil.instance!!.initProgressBar(context)
        if (!progressFlower!!.isShowing) {
            progressFlower!!.dismiss()
            progressFlower!!.show()
        }
    }

    //hide progress dialog
    fun hideProgress() {
        if (progressFlower != null) {
            if (progressFlower!!.isShowing) progressFlower!!.dismiss()
        }
    }
}