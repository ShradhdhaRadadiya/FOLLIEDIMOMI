package com.folliedimomi.model

import com.google.gson.annotations.SerializedName


data class ProductImages(
        @SerializedName("status")
        val status: Int = 0,
        @SerializedName("message")
        val message: String = "",
        @SerializedName("result")
        val result: ProductImageResult = ProductImageResult()
)

data class ProductImageResult(
        @SerializedName("productdetailsliderimages")
        val productdetailsliderimages: List<Productdetailsliderimage> = emptyList()
)

data class Productdetailsliderimage(
        @SerializedName("id_image")
        val idImage: String = "",
        @SerializedName("id_product_attribute")
        val idProductAttribute: String = "",
        @SerializedName("legend")
        val legend: String = ""
)
