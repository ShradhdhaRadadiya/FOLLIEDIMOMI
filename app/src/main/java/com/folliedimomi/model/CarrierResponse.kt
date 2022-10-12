package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class CarrierResponse(
    @SerializedName("result")
    val result: List<Carrier> = listOf(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Int = 0
)

data class Carrier(
    @SerializedName("id_carrier")
    val idCarrier: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("delay")
    val delay: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("name")
    val name: String = ""
)


