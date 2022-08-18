package com.folliedimomi.model


import com.folliedimomi.model.CountryState
import com.google.gson.annotations.SerializedName

data class StateRespnse(
    @SerializedName("result")
        val result: List<CountryState> = listOf(),
    @SerializedName("message")
        val message: String = "",
    @SerializedName("status")
        val status: Int = 0
)






