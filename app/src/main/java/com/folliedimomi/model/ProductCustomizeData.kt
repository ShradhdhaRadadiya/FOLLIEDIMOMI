package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class ProductCustomizeData(
    @SerializedName("id_customization")
    val idCustomization: String = "",
    @SerializedName("quantity")
    val quantity: String = "",
    @SerializedName("in_cart")
    val inCart: String = "",
    @SerializedName("index")
    val index: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("value")
    val value: String = ""
)


