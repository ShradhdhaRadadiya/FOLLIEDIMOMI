package com.folliedimomi.model

import com.google.gson.annotations.SerializedName

data class Promotion(
        @SerializedName("banner")
        val banner: String = "",
        @SerializedName("id")
        val id: Int = 0
)


data class PromotionResponse(
        @SerializedName("result")
        val result: List<Promotion> = listOf(),
        @SerializedName("message")
        val message: String = "",
        @SerializedName("status")
        val status: Int = 0
)
