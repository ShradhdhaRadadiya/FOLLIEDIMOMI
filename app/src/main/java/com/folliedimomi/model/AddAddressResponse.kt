package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class AddAddressResponse(
    @SerializedName("result")
    val result: Result,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Int = 0
)

data class AddAddress(
    @SerializedName("firstname")
    val firstname: String = "",
    @SerializedName("phone_mobile")
    val phoneMobile: String = "",
    @SerializedName("address2")
    val address2: String = "",
    @SerializedName("city")
    val city: String = "",
    @SerializedName("id_customer")
    val idCustomer: Int = 0,
    @SerializedName("vat_number")
    val vatNumber: String = "",
    @SerializedName("address1")
    val address1: String = "",
    @SerializedName("postcode")
    val postcode: String = "",
    @SerializedName("lastname")
    val lastname: String = "",
    @SerializedName("id_address")
    val idAddress: Int = 0,
    @SerializedName("id_country")
    val idCountry: Int = 0,
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("alias")
    val alias: String = "",
    @SerializedName("company")
    val company: String = "",
    @SerializedName("id_state")
    val idState: Int = 0
)


