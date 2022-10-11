package com.folliedimomi.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import com.folliedimomi.databinding.DialogeFilterProductBinding

object DialogUtils {


    fun showFilterDialog(context: Context, postListener: PostInterface) {
        val binding: DialogeFilterProductBinding = DialogeFilterProductBinding.inflate(
            LayoutInflater.from(context)
        )
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)

        val window: Window = dialog.window!!
        val width = (context.resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        window.setLayout(width, height)
        window.setBackgroundDrawableResource(android.R.color.transparent)

        binding.ivClose.setOnClickListener {
            dialog.dismiss()
        }
        binding.tvCamera.setOnClickListener {
            postListener.openGalleryForImage(1)
            dialog.dismiss()
        }
        binding.tvGallery.setOnClickListener {
            postListener.openGalleryForImage(2)
            dialog.dismiss()
        }
        binding.tvNameAec.setOnClickListener {
            postListener.openGalleryForImage(3)
            dialog.dismiss()
        }
        binding.tvPossidsc.setOnClickListener {
            postListener.openGalleryForImage(4)
            dialog.dismiss()
        }

        binding.tvPossiDsc.setOnClickListener {
            postListener.openGalleryForImage(5)
            dialog.dismiss()
        }
        binding.tvPriceAsc.setOnClickListener {
            postListener.openGalleryForImage(6)
            dialog.dismiss()
        }
        binding.tvPriceDsc.setOnClickListener {
            postListener.openGalleryForImage(7)
            dialog.dismiss()
        }


        dialog.show()
    }


    interface PostInterface {
        fun openGalleryForImage(i: Int)
    }
}