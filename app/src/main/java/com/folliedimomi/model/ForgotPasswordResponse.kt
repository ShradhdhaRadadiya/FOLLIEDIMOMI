package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
        @SerializedName("result")
        val result: List<ForgotPassword> = listOf(),
        @SerializedName("message")
        val message: String = "",
        @SerializedName("status")
        val status: Int = 0
)

data class ForgotPassword(
        @SerializedName("customer_email")
        val customerEmail: String = "",
        @SerializedName("confirmation")
        val confirmation: Int = 0,
        @SerializedName("status")
        val status: Int = 0
)





