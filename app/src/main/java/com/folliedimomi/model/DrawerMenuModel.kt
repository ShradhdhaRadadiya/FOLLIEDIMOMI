package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class DrawerMenuModel(
    @SerializedName("message")
    var message: String,
    @SerializedName("result")
    var result: List<Result>,
    @SerializedName("status")
    var status: Int
) {
    data class Result(
        @SerializedName("id")
        var id: Int,
        @SerializedName("linkhref")
        var linkhref: String,
        @SerializedName("submenu")
        var submenu: List<Submenu>?,
        @SerializedName("title")
        var title: String
    ) {
        data class Submenu(
            @SerializedName("id")
            var id: Int,
            @SerializedName("linkhref")
            var linkhref: String,
            @SerializedName("title")
            var title: String
        )
    }
}