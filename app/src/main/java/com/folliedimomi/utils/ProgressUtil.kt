package com.folliedimomi.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import com.kaopiz.kprogresshud.KProgressHUD

class ProgressUtil {

    fun initProgressBar(context: Context?): KProgressHUD {
        return KProgressHUD.create(context)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(false)
            .setAnimationSpeed(1)
            .setDimAmount(0.5f)
            .show()
    }

    fun initProgressBar(context: Context?, message: String?): KProgressHUD {
        return KProgressHUD.create(context)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel(message, Color.WHITE)
            .setCancellable(false)
            .setAnimationSpeed(1)
            .setDimAmount(0.5f)
            .show()
    }

    //show progress dialog
    fun showDialog(dialog: KProgressHUD?, pb: ProgressBar?, isLoaderRequired: Boolean) {
        if (dialog != null && !dialog.isShowing) {
            dialog.show()
        } else if (pb != null && isLoaderRequired) {
            pb.visibility = View.VISIBLE
        }
    }

    //hide progress dialog
    fun hideDialog(dialog: KProgressHUD?, pb: ProgressBar?) {
        if (dialog != null && dialog.isShowing) {
            dialog.dismiss()
        } else if (pb != null) {
            pb.visibility = View.GONE
        }
    }

    companion object {
        private var mInstance: ProgressUtil? = null
        val instance: ProgressUtil?
            get() {
                if (mInstance == null) {
                    mInstance = ProgressUtil()
                }
                return mInstance
            }
    }
}