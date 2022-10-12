package com.folliedimomi.model

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("result")
    val result: List<Orders> = listOf(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Int = 0
)

data class Orders(
    @SerializedName("order_status")
    val order_status: Int = 0,
    @SerializedName("id_address_invoice")
    val idAddressInvoice: Int = 0,
    @SerializedName("total_paid_tax_excl")
    val totalPaidTaxExcl: String = "",
    @SerializedName("id_customer")
    val idCustomer: Int = 0,
    @SerializedName("carrier_tax_rate")
    val carrierTaxRate: String = "",
    @SerializedName("invoice_date")
    val invoiceDate: String = "",
    @SerializedName("total_discounts")
    val totalDiscounts: String = "",
    @SerializedName("reference")
    val reference: String = "",
    @SerializedName("id_order")
    val idOrder: Int = 0,
    @SerializedName("id_lang")
    val idLang: Int = 0,
    @SerializedName("total_paid")
    val totalPaid: String = "",
    @SerializedName("total_shipping_tax_incl")
    val totalShippingTaxIncl: String = "",
    @SerializedName("secure_key")
    val secureKey: String = "",
    @SerializedName("total_shipping")
    val totalShipping: String = "",
    @SerializedName("id_shop")
    val idShop: Int = 0,
    @SerializedName("payment")
    val payment: String = "",
    @SerializedName("delivery_number")
    val deliveryNumber: String = "",
    @SerializedName("id_currency")
    val idCurrency: Int = 0,
    @SerializedName("conversion_rate")
    val conversionRate: String = "",
    @SerializedName("invoice_number")
    val invoiceNumber: String = "",
    @SerializedName("id_carrier")
    val idCarrier: Int = 0,
    @SerializedName("module")
    val module: String = "",
    @SerializedName("current_state")
    val currentState: Int = 0,
    @SerializedName("id_address_delivery")
    val idAddressDelivery: Int = 0,
    @SerializedName("total_shipping_tax_excl")
    val totalShippingTaxExcl: String = "",
    @SerializedName("total_paid_real")
    val totalPaidReal: String = "",
    @SerializedName("delivery_date")
    val deliveryDate: String = "",
    @SerializedName("total_paid_tax_incl")
    val totalPaidTaxIncl: String = "",
    @SerializedName("id_cart")
    val idCart: Int = 0,
    @SerializedName("id_shop_group")
    val idShopGroup: Int = 0
)





