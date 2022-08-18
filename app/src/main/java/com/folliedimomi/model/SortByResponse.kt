package com.folliedimomi.model
import com.google.gson.annotations.SerializedName


data class SortByResponse(
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("result")
    val result: List<SortBy> = listOf()
)

data class SortBy(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("sort_by")
    val sortBy: String = ""
)