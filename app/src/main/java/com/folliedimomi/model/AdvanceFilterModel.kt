package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class AdvanceFilterModel(
    @SerializedName("message")
    var message: String,
    @SerializedName("result")
    var result: List<Result>,
    @SerializedName("status")
    var status: Int
) {
    data class Result(
        @SerializedName("data")
        var `data`: List<Data>,
        @SerializedName("name")
        var name: String,
        @SerializedName("title")
        var title: String,
        @SerializedName("type")
        var type: String
    ) {
        data class Data(
            @SerializedName("category_id")
            var categoryId: Int = 0,
            @SerializedName("category_name")
            var categoryName: String = "",
            @SerializedName("description")
            var description: String,
            @SerializedName("level_depth")
            var levelDepth: Int,
            @SerializedName("link_rewrite")
            var linkRewrite: String,
            @SerializedName("nb_products_recursive")
            var nbProductsRecursive: Int,
            @SerializedName("position")
            var position: Int,
            @SerializedName("selected")
            var selected: String,
            @SerializedName("id_manufacturer")
            var idManufacturer: String = "0",
            @SerializedName("name")
            var name: String
        )
    }
}