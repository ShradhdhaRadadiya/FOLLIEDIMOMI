package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class OrderCompleteResponse(
    @SerializedName("result")
    val result: OrderComplete,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Int = 0
)


data class OrderComplete(
    @SerializedName("id_carrier")
    val idCarrier: Int = 0,
    @SerializedName("id_order")
    val idOrder: Int = 0,
    @SerializedName("total_paid_tax_excl")
    val totalPaidTaxExcl: String = "",
    @SerializedName("total_paid")
    val totalPaid: String = "",
    @SerializedName("id_customer")
    val idCustomer: Int = 0,
    @SerializedName("total_paid_tax_incl")
    val totalPaidTaxIncl: String = "",
    @SerializedName("id_cart")
    val idCart: Int = 0,
    @SerializedName("current_state")
    val currentState: Int = 0,
    @SerializedName("payment")
    val payment: String = ""
)


