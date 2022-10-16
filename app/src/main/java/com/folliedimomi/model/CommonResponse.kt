package com.folliedimomi.model

import com.google.gson.annotations.SerializedName

data class CommonResponse (
    @SerializedName("message")
    var message: String,
    @SerializedName("result")
    var result: Any,
    @SerializedName("status")
    var status: Int
)