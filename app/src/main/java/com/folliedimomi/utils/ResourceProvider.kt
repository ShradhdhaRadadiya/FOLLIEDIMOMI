package com.folliedimomi.utils

import android.content.Context
import androidx.annotation.StringRes

class ResourceProvider(private val context: Context) {
    fun getString(@StringRes stringId: Int/*, vararg formatArgs: Any*/): String =
        context.getString(stringId/*, *formatArgs*/)
}

/*
class ResourceProvider(private val mContext: Context) {

    fun getString(resId: Int): String {
        return mContext.getString(resId)
    }

    fun getString(resId: Int, value: String): String {
        return mContext.getString(resId, value)
    }

}*/
