package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class CountryRespnse(
        @SerializedName("result")
        val result: List<CountryState> = listOf(),
        @SerializedName("message")
        val message: String = "",
        @SerializedName("status")
        val status: Int = 0
)







