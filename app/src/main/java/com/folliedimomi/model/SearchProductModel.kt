package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class SearchProductModel(
    @SerializedName("message")
    var message: String,
    @SerializedName("result")
    var result: List<ProductListModel.Result.Product>,
    @SerializedName("status")
    var status: Int
)