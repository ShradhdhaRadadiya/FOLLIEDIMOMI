package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class CreateOrderResponse(
        @SerializedName("result")
        val result: CreateOrder,
        @SerializedName("message")
        val message: String = "",
        @SerializedName("status")
        val status: Int = 0
)


data class CreateOrder(
        @SerializedName("id_carrier")
        val idCarrier: Int = 0,
        @SerializedName("total_paid_tax_excl")
        val totalPaidTaxExcl: String = "",
        @SerializedName("id_customer")
        val idCustomer: Int = 0,
        @SerializedName("current_state")
        val currentState: Int = 0,
        @SerializedName("total_shipping_tax_excl")
        val totalShippingTaxExcl: String = "",
        @SerializedName("carrier_tax_rate")
        val carrierTaxRate: String = "",
        @SerializedName("invoice_date")
        val invoiceDate: String = "",
        @SerializedName("date_add")
        val dateAdd: String = "",
        @SerializedName("reference")
        val reference: String = "",
        @SerializedName("total_paid_real")
        val totalPaidReal: String = "",
        @SerializedName("id_order")
        val idOrder: Int = 0,
        @SerializedName("total_paid")
        val totalPaid: String = "",
        @SerializedName("total_paid_tax_incl")
        val totalPaidTaxIncl: String = "",
        @SerializedName("total_shipping_tax_incl")
        val totalShippingTaxIncl: String = "",
        @SerializedName("id_cart")
        val idCart: Int = 0,
        @SerializedName("secure_key")
        val secureKey: String = "",
        @SerializedName("total_shipping")
        val totalShipping: String = "",
        @SerializedName("payment")
        val payment: String = ""
)


