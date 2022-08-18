package com.folliedimomi.model

import com.google.gson.annotations.SerializedName

data class CountryState(
        @SerializedName("name")
        val name: String = "",
        @SerializedName("id")
        val id: Int = 0
)