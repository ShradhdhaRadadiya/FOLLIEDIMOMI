package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class Reduction(
        @SerializedName("id_customer")
        val idCustomer: String = "",
        @SerializedName("from_quantity")
        val fromQuantity: String = "",
        @SerializedName("score")
        val score: String = "",
        @SerializedName("id_specific_price")
        val idSpecificPrice: String = "",
        @SerializedName("id_product")
        val idProduct: String = "",
        @SerializedName("id_country")
        val idCountry: String = "",
        @SerializedName("id_product_attribute")
        val idProductAttribute: String = "",
        @SerializedName("price")
        val price: String = "",
        @SerializedName("reduction_tax")
        val reductionTax: String = "",
        @SerializedName("id_cart")
        val idCart: String = "",
        @SerializedName("reduction_type")
        val reductionType: String = "",
        @SerializedName("id_shop")
        val idShop: String = "",
        @SerializedName("from")
        val from: String = "",
        @SerializedName("id_currency")
        val idCurrency: String = "",
        @SerializedName("to")
        val to: String = "",
        @SerializedName("id_specific_price_rule")
        val idSpecificPriceRule: String = "",
        @SerializedName("id_group")
        val idGroup: String = "",
        @SerializedName("reduction")
        val reduction: String = "",
        @SerializedName("id_shop_group")
        val idShopGroup: String = ""
)


