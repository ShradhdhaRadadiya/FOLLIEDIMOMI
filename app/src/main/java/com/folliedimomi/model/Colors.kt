package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

class Colors : ArrayList<Colors.ColorsItem>() {
    data class ColorsItem(
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("name")
        val name: String = "",
        @SerializedName("image")
        val image: String = ""
    )
}