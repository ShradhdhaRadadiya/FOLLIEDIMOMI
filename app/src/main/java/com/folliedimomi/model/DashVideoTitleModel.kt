package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class DashVideoTitleModel(
    @SerializedName("message")
    var message: String,
    @SerializedName("result")
    var result: Result,
    @SerializedName("status")
    var status: Int
) {
    data class Result(
        @SerializedName("home_image")
        var homeImage: String,
        @SerializedName("home_video")
        var homeVideo: String,
        @SerializedName("top_bar")
        var topBar: String
    )
}