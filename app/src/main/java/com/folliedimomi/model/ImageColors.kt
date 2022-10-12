package com.folliedimomi.model

import com.google.gson.annotations.SerializedName

data class ImageColors(
    /*@SerializedName("column_name") */
    //val images: List<String> = listOf()
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("price")
    val price: Double = 0.00
    //val id: Int = 0,
    //val name: String = "",
    //val haxCode: String = "",
    //val image: String = ""
)