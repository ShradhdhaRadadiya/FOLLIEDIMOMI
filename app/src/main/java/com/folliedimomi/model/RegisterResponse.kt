package com.folliedimomi.model
import com.google.gson.annotations.SerializedName


data class RegisterResponse(
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("result")
    val result: RegisterUser = RegisterUser()
)

data class RegisterUser(
    @SerializedName("id_customer")
    val idCustomer: Int = 0,
    @SerializedName("active")
    val active: Int = 0,
    @SerializedName("secure_key")
    val secureKey: String = "",
    @SerializedName("id_default_group")
    val idDefaultGroup: Int = 0,
    @SerializedName("firstname")
    val firstname: String = "",
    @SerializedName("lastname")
    val lastname: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("id_gender")
    val idGender: Int = 0,
    @SerializedName("company")
    val company: String = "",
    @SerializedName("id_shop")
    val idShop: Int = 0,
    @SerializedName("id_shop_group")
    val idShopGroup: Int = 0,
    @SerializedName("addressInfo")
    val addressInfo: RegisterAddressInfo = RegisterAddressInfo()
)

data class RegisterAddressInfo(
    @SerializedName("id_customer")
    val idCustomer: String = "",
    @SerializedName("id_manufacturer")
    val idManufacturer: String = "",
    @SerializedName("id_supplier")
    val idSupplier: String = "",
    @SerializedName("id_warehouse")
    val idWarehouse: String = "",
    @SerializedName("id_country")
    val idCountry: String = "",
    @SerializedName("id_state")
    val idState: String = "",
    @SerializedName("country")
    val country: String = "",
    @SerializedName("alias")
    val alias: String = "",
    @SerializedName("company")
    val company: String = "",
    @SerializedName("lastname")
    val lastname: String = "",
    @SerializedName("firstname")
    val firstname: String = "",
    @SerializedName("address1")
    val address1: String = "",
    @SerializedName("address2")
    val address2: String = "",
    @SerializedName("postcode")
    val postcode: String = "",
    @SerializedName("city")
    val city: String = "",
    @SerializedName("other")
    val other: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("phone_mobile")
    val phoneMobile: String = "",
    @SerializedName("vat_number")
    val vatNumber: String = "",
    @SerializedName("dni")
    val dni: String = "",
    @SerializedName("date_add")
    val dateAdd: String = "",
    @SerializedName("date_upd")
    val dateUpd: String = "",
    @SerializedName("deleted")
    val deleted: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("id_shop_list")
    val idShopList: Any = Any(),
    @SerializedName("force_id")
    val forceId: Boolean = false
)