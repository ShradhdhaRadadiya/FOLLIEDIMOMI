package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class CouponCodeResponse(
    @SerializedName("result")
    val result: List<CouponCode> = listOf(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Int = 0
)

data class CouponCode(
    @SerializedName("quantity_per_user")
    val quantityPerUser: String = "",
    @SerializedName("cumulable")
    val cumulable: Boolean = false,
    @SerializedName("code")
    val code: String = "",
    @SerializedName("shop_restriction")
    val shopRestriction: String = "",
    @SerializedName("free_shipping")
    val freeShipping: String = "",
    @SerializedName("id_customer")
    val idCustomer: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("gift_product")
    val giftProduct: String = "",
    @SerializedName("minimum_amount_tax")
    val minimumAmountTax: String = "",
    @SerializedName("minimum_amount")
    val minimumAmount: String = "",
    @SerializedName("group_restriction")
    val groupRestriction: String = "",
    @SerializedName("country_restriction")
    val countryRestriction: String = "",
    @SerializedName("highlight")
    val highlight: String = "",
    @SerializedName("id_lang")
    val idLang: String = "",
    @SerializedName("minimal")
    val minimal: String = "",
    @SerializedName("cart_rule_restriction")
    val cartRuleRestriction: String = "",
    @SerializedName("product_restriction")
    val productRestriction: String = "",
    @SerializedName("gift_product_attribute")
    val giftProductAttribute: String = "",
    @SerializedName("value")
    val value: String = "",
    @SerializedName("quantity")
    val quantity: String = "",
    @SerializedName("quantity_for_user")
    val quantityForUser: Int = 0,
    @SerializedName("minimum_amount_currency")
    val minimumAmountCurrency: String = "",
    @SerializedName("active")
    val active: String = "",
    @SerializedName("reduction_percent")
    val reductionPercent: String = "",
    @SerializedName("date_to")
    val dateTo: String = "",
    @SerializedName("priority")
    val priority: String = "",
    @SerializedName("date_add")
    val dateAdd: String = "",
    @SerializedName("reduction_amount")
    val reductionAmount: String = "",
    @SerializedName("id_cart_rule")
    val idCartRule: String = "",
    @SerializedName("reduction_tax")
    val reductionTax: String = "",
    @SerializedName("date_upd")
    val dateUpd: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("id_discount_type")
    val idDiscountType: Int = 0,
    @SerializedName("minimum_amount_shipping")
    val minimumAmountShipping: String = "",
    @SerializedName("carrier_restriction")
    val carrierRestriction: String = "",
    @SerializedName("reduction_currency")
    val reductionCurrency: String = "",
    @SerializedName("reduction_product")
    val reductionProduct: String = "",
    @SerializedName("partial_use")
    val partialUse: String = "",
    @SerializedName("date_from")
    val dateFrom: String = ""
)



