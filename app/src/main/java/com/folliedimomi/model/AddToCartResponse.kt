package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class AddToCartResponse(
    @SerializedName("result")
    val result: Result,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Int = 0
)

data class Result(
    @SerializedName("id_address_invoice")
    val idAddressInvoice: Int = 0,
    @SerializedName("id_customer")
    val idCustomer: Int = 0,
    @SerializedName("id_cart")
    val idCart: Int = 0,
    @SerializedName("secure_key")
    val secureKey: String = "",
    @SerializedName("id_address_delivery")
    val idAddressDelivery: Int = 0,
    @SerializedName("id_currency")
    val idCurrency: Int = 0,
    @SerializedName("products")
    val products: List<Product> = listOf()
)


/*data class ProductsItem(@SerializedName("reduction_applies")
                        val reductionApplies: Boolean = false,
                        @SerializedName("legend")
                        val legend: String = "",
                        @SerializedName("reference")
                        val reference: String = "",
                        @SerializedName("quantity_discount_applies")
                        val quantityDiscountApplies: Boolean = false,
                        @SerializedName("advanced_stock_management")
                        val advancedStockManagement: String = "",
                        @SerializedName("price")
                        val price: Double = 0.0,
                        @SerializedName("wholesale_price")
                        val wholesalePrice: String = "",
                        @SerializedName("reduction_type")
                        val reductionType: String = "",
                        @SerializedName("id_shop")
                        val idShop: String = "",
                        @SerializedName("height")
                        val height: String = "",
                        @SerializedName("unique_id")
                        val uniqueId: String = "",
                        @SerializedName("active")
                        val active: String = "",
                        @SerializedName("weight")
                        val weight: Int = 0,
                        @SerializedName("upc")
                        val upc: String = "",
                        @SerializedName("id_manufacturer")
                        val idManufacturer: String = "",
                        @SerializedName("id_address_delivery")
                        val idAddressDelivery: String = "",
                        @SerializedName("cart_quantity")
                        val cartQuantity: String = "",
                        @SerializedName("id_product")
                        val idProduct: String = "",
                        @SerializedName("name")
                        val name: String = "",
                        @SerializedName("customization_quantity")
                        val customizationQuantity: Null = null,
                        @SerializedName("is_virtual")
                        val isVirtual: String = "",
                        @SerializedName("out_of_stock")
                        val outOfStock: String = "",
                        @SerializedName("id_customization")
                        val idCustomization: Null = null,
                        @SerializedName("additional_shipping_cost")
                        val additionalShippingCost: String = "",
                        @SerializedName("available_now")
                        val availableNow: String = "",
                        @SerializedName("ean13")
                        val ean: String = "",
                        @SerializedName("price_with_reduction_without_tax")
                        val priceWithReductionWithoutTax: Double = 0.0,
                        @SerializedName("price_wt")
                        val priceWt: Int = 0,
                        @SerializedName("supplier_reference")
                        val supplierReference: Null = null,
                        @SerializedName("price_with_reduction")
                        val priceWithReduction: Int = 0,
                        @SerializedName("id_supplier")
                        val idSupplier: String = "",
                        @SerializedName("price_attribute")
                        val priceAttribute: String = "",
                        @SerializedName("unity")
                        val unity: String = "",
                        @SerializedName("minimal_quantity")
                        val minimalQuantity: String = "",
                        @SerializedName("total")
                        val total: Double = 0.0,
                        @SerializedName("rate")
                        val rate: Int = 0,
                        @SerializedName("ecotax_attr")
                        val ecotaxAttr: String = "",
                        @SerializedName("id_image")
                        val idImage: String = "",
                        @SerializedName("unit_price_ratio")
                        val unitPriceRatio: String = "",
                        @SerializedName("id_category_default")
                        val idCategoryDefault: String = "",
                        @SerializedName("on_sale")
                        val onSale: String = "",
                        @SerializedName("link_rewrite")
                        val linkRewrite: String = "",
                        @SerializedName("quantity")
                        val quantity: Int = 0,
                        @SerializedName("available_later")
                        val availableLater: String = "",
                        @SerializedName("tax_name")
                        val taxName: String = "",
                        @SerializedName("available_for_order")
                        val availableForOrder: String = "",
                        @SerializedName("stock_quantity")
                        val stockQuantity: Int = 0,
                        @SerializedName("total_wt")
                        val totalWt: Int = 0,
                        @SerializedName("date_add")
                        val dateAdd: String = "",
                        @SerializedName("description_short")
                        val descriptionShort: String = "",
                        @SerializedName("attributes_small")
                        val attributesSmall: String = "",
                        @SerializedName("depth")
                        val depth: String = "",
                        @SerializedName("id_product_attribute")
                        val idProductAttribute: String = "",
                        @SerializedName("weight_attribute")
                        val weightAttribute: String = "",
                        @SerializedName("price_without_reduction")
                        val priceWithoutReduction: Int = 0,
                        @SerializedName("allow_oosp")
                        val allowOosp: Int = 0,
                        @SerializedName("width")
                        val width: String = "",
                        @SerializedName("date_upd")
                        val dateUpd: String = "",
                        @SerializedName("ecotax")
                        val ecotax: String = "",
                        @SerializedName("attributes")
                        val attributes: String = "",
                        @SerializedName("category")
                        val category: String = "",
                        @SerializedName("quantity_available")
                        val quantityAvailable: String = "")*/

