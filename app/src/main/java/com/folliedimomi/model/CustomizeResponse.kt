package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class CustomizeResponse(
    @SerializedName("message")
    var message: String,
    @SerializedName("result")
    var result: Result,
    @SerializedName("status")
    var status: Int
) {
    data class Result(
        @SerializedName("fileupload1")
        var fileupload1: Fileupload1,
        @SerializedName("fileupload2")
        var fileupload2: Fileupload2,
        @SerializedName("filtercolor")
        var filtercolor: Filtercolor,
        @SerializedName("firstrow")
        var firstrow: List<Firstrow>,
        @SerializedName("fourthrow")
        var fourthrow: Fourthrow,
        @SerializedName("rowradio")
        var rowradio: Rowradio,
        @SerializedName("secondrow")
        var secondrow: List<Secondrow>,
        @SerializedName("thirdrow")
        var thirdrow: List<Thirdrow>
    ) {
        data class Fileupload1(
            @SerializedName("id_customization_field")
            var idCustomizationField: Int,
            @SerializedName("id_lang")
            var idLang: String,
            @SerializedName("key")
            var key: String,
            @SerializedName("label")
            var label: String,
            @SerializedName("name")
            var name: String,
            @SerializedName("required")
            var required: String,
            @SerializedName("type")
            var type: String
        )

        data class Fileupload2(
            @SerializedName("id_customization_field")
            var idCustomizationField: Int,
            @SerializedName("id_lang")
            var idLang: String,
            @SerializedName("key")
            var key: String,
            @SerializedName("label")
            var label: String,
            @SerializedName("name")
            var name: String,
            @SerializedName("required")
            var required: String,
            @SerializedName("type")
            var type: String
        )

        data class Filtercolor(
            @SerializedName("id_customization_field")
            var idCustomizationField: Int,
            @SerializedName("id_lang")
            var idLang: String,
            @SerializedName("key")
            var key: String,
            @SerializedName("label")
            var label: String,
            @SerializedName("name")
            var name: String,
            @SerializedName("required")
            var required: String,
            @SerializedName("type")
            var type: String
        )

        data class Firstrow(
            @SerializedName("id_customization_field")
            var idCustomizationField: Int,
            @SerializedName("id_lang")
            var idLang: String,
            @SerializedName("key")
            var key: String,
            @SerializedName("label")
            var label: String,
            @SerializedName("name")
            var name: String,
            @SerializedName("required")
            var required: String,
            @SerializedName("type")
            var type: String
        )

        data class Fourthrow(
            @SerializedName("id_customization_field")
            var idCustomizationField: Int,
            @SerializedName("id_lang")
            var idLang: String,
            @SerializedName("key")
            var key: String,
            @SerializedName("label")
            var label: String,
            @SerializedName("name")
            var name: String,
            @SerializedName("required")
            var required: String,
            @SerializedName("type")
            var type: String
        )

        data class Rowradio(
            @SerializedName("id_customization_field")
            var idCustomizationField: Int,
            @SerializedName("id_lang")
            var idLang: String,
            @SerializedName("key")
            var key: String,
            @SerializedName("label")
            var label: String,
            @SerializedName("name")
            var name: String,
            @SerializedName("required")
            var required: String,
            @SerializedName("type")
            var type: String
        )

        data class Secondrow(
            @SerializedName("id_customization_field")
            var idCustomizationField: Int,
            @SerializedName("id_lang")
            var idLang: String,
            @SerializedName("key")
            var key: String,
            @SerializedName("label")
            var label: String,
            @SerializedName("name")
            var name: String,
            @SerializedName("required")
            var required: String,
            @SerializedName("type")
            var type: String
        )

        data class Thirdrow(
            @SerializedName("id_customization_field")
            var idCustomizationField: Int,
            @SerializedName("id_lang")
            var idLang: String,
            @SerializedName("key")
            var key: String,
            @SerializedName("label")
            var label: String,
            @SerializedName("name")
            var name: String,
            @SerializedName("required")
            var required: String,
            @SerializedName("type")
            var type: String
        )
    }
}