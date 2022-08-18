package com.folliedimomi.model

import com.google.gson.annotations.SerializedName

data class ApplyCouponRespone(
        @SerializedName("status")
        val status: Int = 0,
        @SerializedName("message")
        val message: String = ""
)
