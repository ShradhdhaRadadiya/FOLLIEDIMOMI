package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(
    @SerializedName("result")
    val result: ChangePasswordResult,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Int = 0
)

data class ChangePasswordResult(
    @SerializedName("firstname")
    val firstname: String = "",
    @SerializedName("id_customer")
    val idCustomer: Int = 0,
    @SerializedName("id_default_group")
    val idDefaultGroup: Int = 0,
    @SerializedName("secure_key")
    val secureKey: String = "",
    @SerializedName("id_gender")
    val idGender: String = "",
    @SerializedName("id_shop")
    val idShop: Int = 0,
    @SerializedName("company")
    val company: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("lastname")
    val lastname: String = "",
    @SerializedName("id_shop_group")
    val idShopGroup: Int = 0
)


