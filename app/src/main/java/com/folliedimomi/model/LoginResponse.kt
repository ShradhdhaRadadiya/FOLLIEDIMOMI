package com.folliedimomi.model
import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("result")
    val result: LoginUser = LoginUser()
)

data class LoginUser(
    @SerializedName("id_customer")
    val idCustomer: Int = 0,
    @SerializedName("id_default_group")
    val idDefaultGroup: Int = 0,
    @SerializedName("is_guest")
    val isGuest: Int = 0,
    @SerializedName("active")
    val active: Int = 0,
    @SerializedName("secure_key")
    val secureKey: String = "",
    @SerializedName("firstname")
    val firstname: String = "",
    @SerializedName("lastname")
    val lastname: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("id_gender")
    val idGender: String = "",
    @SerializedName("company")
    val company: String = "",
    @SerializedName("id_shop")
    val idShop: Int = 0,
    @SerializedName("id_shop_group")
    val idShopGroup: Int = 0
)