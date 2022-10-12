package com.folliedimomi.model

import com.google.gson.annotations.SerializedName


data class ProductResponse(
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("result")
    val result: List<Product> = listOf()
)

data class Product(
    @SerializedName("id_product")
    val idProduct: Int = 0,
    @SerializedName("id_supplier")
    val idSupplier: Int = 0,
    @SerializedName("id_customization")
    val idCustomization: Int = 0,
    @SerializedName("id_manufacturer")
    val idManufacturer: Int = 0,
    @SerializedName("id_category_default")
    val idCategoryDefault: Int = 0,
    @SerializedName("id_shop_default")
    val idShopDefault: Int = 0,
    @SerializedName("id_tax_rules_group")
    val idTaxRulesGroup: Int = 0,
    @SerializedName("on_sale")
    val onSale: String = "",
    @SerializedName("online_only")
    val onlineOnly: String = "",
    @SerializedName("ean13")
    val ean13: String = "",
    @SerializedName("upc")
    val upc: String = "",
    @SerializedName("ecotax")
    val ecotax: String = "",
    @SerializedName("quantity")
    val quantity: Int = 0,
    @SerializedName("cart_quantity")
    val cartQuantity: Int = 0,
    @SerializedName("minimal_quantity")
    val minimalQuantity: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("wholesale_price")
    val wholesalePrice: String = "",
    @SerializedName("unity")
    val unity: String = "",
    @SerializedName("unit_price_ratio")
    val unitPriceRatio: String = "",
    @SerializedName("additional_shipping_cost")
    val additionalShippingCost: String = "",
    @SerializedName("reference")
    val reference: String = "",
    @SerializedName("supplier_reference")
    val supplierReference: String = "",
    @SerializedName("location")
    val location: String = "",
    @SerializedName("width")
    val width: String = "",
    @SerializedName("height")
    val height: String = "",
    @SerializedName("depth")
    val depth: String = "",
    @SerializedName("weight")
    val weight: String = "",
    @SerializedName("out_of_stock")
    val outOfStock: String = "",
    @SerializedName("quantity_discount")
    val quantityDiscount: String = "",
    @SerializedName("customizable")
    val customizable: String = "0",
    @SerializedName("uploadable_files")
    val uploadableFiles: String = "",
    @SerializedName("text_fields")
    val textFields: String = "",
    @SerializedName("active")
    val active: String = "",
    @SerializedName("redirect_type")
    val redirectType: String = "",
    @SerializedName("id_product_redirected")
    val idProductRedirected: String = "",
    @SerializedName("available_for_order")
    val availableForOrder: String = "",
    @SerializedName("available_date")
    val availableDate: String = "",
    @SerializedName("condition")
    val condition: String = "",
    @SerializedName("show_price")
    val showPrice: String = "",
    @SerializedName("indexed")
    val indexed: String = "",
    @SerializedName("visibility")
    val visibility: String = "",
    @SerializedName("cache_is_pack")
    val cacheIsPack: String = "",
    @SerializedName("cache_has_attachments")
    val cacheHasAttachments: String = "",
    @SerializedName("is_virtual")
    val isVirtual: String = "",
    @SerializedName("cache_default_attribute")
    val cacheDefaultAttribute: String = "",
    @SerializedName("date_add")
    val dateAdd: String = "",
    @SerializedName("date_upd")
    val dateUpd: String = "",
    @SerializedName("advanced_stock_management")
    val advancedStockManagement: String = "",
    @SerializedName("pack_stock_type")
    val packStockType: String = "",
    @SerializedName("id_shop")
    val idShop: String = "",
    @SerializedName("id_product_attribute")
    val idProductAttribute: Int = 0,
    @SerializedName("product_attribute_minimal_quantity")
    val productAttributeMinimalQuantity: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("description_short")
    val descriptionShort: String = "",
    @SerializedName("available_now")
    val availableNow: String = "",
    @SerializedName("available_later")
    val availableLater: String = "",
    @SerializedName("link_rewrite")
    val linkRewrite: String = "",
    @SerializedName("meta_description")
    val metaDescription: String = "",
    @SerializedName("meta_keywords")
    val metaKeywords: String = "",
    @SerializedName("meta_title")
    val metaTitle: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("id_image")
    val idImage: String = "",
    @SerializedName("legend")
    val legend: String = "",
    @SerializedName("category_default")
    val categoryDefault: String = "",
    @SerializedName("new")
    val new: String = "",
    @SerializedName("orderprice")
    val orderprice: String = "",
    @SerializedName("allow_oosp")
    val allowOosp: Int = 0,
    @SerializedName("category")
    val category: String = "",
    @SerializedName("link")
    val link: String = "",
    @SerializedName("attribute_price")
    val attributePrice: Double = 0.0,
    @SerializedName("price_tax_exc")
    val priceTaxExc: Double = 0.0,
    @SerializedName("price_without_reduction")
    val priceWithoutReduction: Double = 0.0,
    @SerializedName("reduction")
    val reduction: Double = 0.0,
    @SerializedName("specific_prices")
    val specificPrices: Any = Any(),
    @SerializedName("quantity_all_versions")
    val quantityAllVersions: Int = 0,
    @SerializedName("features")
    val features: List<Any> = listOf(),
    @SerializedName("attachments")
    val attachments: List<Any> = listOf(),
    @SerializedName("virtual")
    val virtual: Double = 0.0,
    @SerializedName("pack")
    val pack: Any = Any(),
    @SerializedName("packItems")
    val packItems: List<Any> = listOf(),
    @SerializedName("nopackprice")
    val nopackprice: Any = Any(),
    @SerializedName("customization_required")
    val customizationRequired: Boolean = false,
    @SerializedName("rate")
    val rate: Double = 0.0,
    @SerializedName("total")
    val total: Double = 0.0,
    @SerializedName("total_wt")
    val totalWt: Double = 0.0,
    @SerializedName("price_wt")
    val priceWt: Double = 0.0,
    @SerializedName("stock_quantity")
    val stockQuantity: Int = 0,
    @SerializedName("price_with_reduction_without_tax")
    val priceWithReductionWithoutTax: Double = 0.0,
    @SerializedName("tax_name")
    val taxName: String = "",
    @SerializedName("product_image")
    val productImage: String = "",
    @SerializedName("manufacturer_name")
    val manufacturerName: Any = Any(),
    @SerializedName("attributes")
    val attributes: String = "",
    @SerializedName("attributes_small")
    val attributes_small: String = "",
    @SerializedName("product_id")
    val idProducts: Int = 0,
    @SerializedName("is_discount")
    val isDiscount: Boolean = false,
    @SerializedName("cart_customization")
    val cartCustomization: List<CartCustom> = listOf(),
    var qty: Int = 0
) {
    // val specificPrices : Reduction = Reduction(),

    /*val  title get() = {
            if (specificPrices.toString() == "false") specificPrices
            //specificPrices
    }
//        val title get() = _specificPrices   ?: throw IllegalArgumentException("Title is required")
//
            init {
                    this.title
            }
    }*/
}

data class CartCustom(
    @SerializedName("id_customization")
    val idCustomization: String = "",
    @SerializedName("quantity")
    val quantity: String = "",
    @SerializedName("in_cart")
    val inCart: String = "",
    @SerializedName("index")
    val index: String = "",
    @SerializedName("label")
    val label: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("value")
    val value: String = "",
    @SerializedName("custom_price")
    val custom_price: String = ""
)