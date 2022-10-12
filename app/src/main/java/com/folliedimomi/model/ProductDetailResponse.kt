package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    @SerializedName("result")
    val result: ProductDetail = ProductDetail(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Int = 0
)

data class ProductDetail(
    @SerializedName("product_image")
    val productImage: String = "",
    @SerializedName("tax_rate")
    val taxRate: Any = Any(),
    @SerializedName("reference")
    val reference: String = "",
    @SerializedName("advanced_stock_management")
    val advancedStockManagement: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("wholesale_price")
    val wholesalePrice: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("redirect_type")
    val redirectType: String = "",
    @SerializedName("height")
    val height: String = "",
    @SerializedName("new")
    val new: Any = Any(),
    @SerializedName("quantity_discount")
    val quantityDiscount: String = "",
    @SerializedName("meta_title")
    val metaTitle: String = "",
    @SerializedName("visibility")
    val visibility: String = "",
    @SerializedName("indexed")
    val indexed: String = "",
    @SerializedName("text_fields")
    val textFields: String = "",
    @SerializedName("id_shop_list")
    val idShopList: Any = Any(),
    @SerializedName("weight")
    val weight: String = "",
    @SerializedName("upc")
    val upc: String = "",
    @SerializedName("active")
    val active: String = "",
    @SerializedName("id_manufacturer")
    val idManufacturer: Int = 0,
    @SerializedName("isFullyLoaded")
    val isFullyLoaded: Boolean = false,
    @SerializedName("pack_stock_type")
    val packStockType: String = "",
    @SerializedName("tags")
    val tags: Any = Any(),
    @SerializedName("online_only")
    val onlineOnly: String = "",
    @SerializedName("id_product_redirected")
    val idProductRedirected: String = "",
    @SerializedName("meta_description")
    val metaDescription: String = "",
    @SerializedName("condition")
    val condition: String = "",
    @SerializedName("id_product")
    val idProduct: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("id_color_default")
    val idColorDefault: Int = 0,
    @SerializedName("cache_is_pack")
    val cacheIsPack: String = "",
    @SerializedName("is_virtual")
    val isVirtual: String = "",
    @SerializedName("out_of_stock")
    val outOfStock: String = "",
    @SerializedName("additional_shipping_cost")
    val additionalShippingCost: String = "",
    @SerializedName("customizable")
    val customizable: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("available_now")
    val availableNow: String = "",
    @SerializedName("ean13")
    val ean: String = "",
    @SerializedName("id_pack_product_attribute")
    val idPackProductAttribute: Any = Any(),
    @SerializedName("supplier_reference")
    val supplierReference: String = "",
    @SerializedName("meta_keywords")
    val metaKeywords: String = "",
    @SerializedName("id_supplier")
    val idSupplier: Int = 0,
    @SerializedName("specificPrice")
    val specificPrice: Int = 0,
    @SerializedName("minimal_quantity")
    val minimalQuantity: String = "",
    @SerializedName("unity")
    val unity: String = "",
    @SerializedName("cache_default_attribute")
    val cacheDefaultAttribute: String = "",
    @SerializedName("base_price")
    val basePrice: Double = 0.0,
    @SerializedName("unit_price_ratio")
    val unitPriceRatio: String = "",
    @SerializedName("id_tax_rules_group")
    val idTaxRulesGroup: Int = 0,
    @SerializedName("force_id")
    val forceId: Boolean = false,
    @SerializedName("id_category_default")
    val idCategoryDefault: Int = 0,
    @SerializedName("supplier_name")
    val supplierName: String = "",
    @SerializedName("on_sale")
    val onSale: String = "",
    @SerializedName("link_rewrite")
    val linkRewrite: String = "",
    @SerializedName("cache_has_attachments")
    val cacheHasAttachments: String = "",
    @SerializedName("quantity")
    val quantity: String = "",
    @SerializedName("available_later")
    val availableLater: String = "",
    @SerializedName("depends_on_stock")
    val dependsOnStock: Any = Any(),
    @SerializedName("uploadable_files")
    val uploadableFiles: String = "",
    @SerializedName("tax_name")
    val taxName: String = "",
    @SerializedName("available_date")
    val availableDate: String = "",
    @SerializedName("show_price")
    val showPrice: String = "",
    @SerializedName("manufacturer_name")
    val manufacturerName: String = "",
    @SerializedName("available_for_order")
    val availableForOrder: String = "",
    @SerializedName("unit_price")
    val unitPrice: Double = 0.0,
    @SerializedName("date_add")
    val dateAdd: String = "",
    @SerializedName("description_short")
    val descriptionShort: String = "",
    @SerializedName("depth")
    val depth: String = "",
    @SerializedName("id_product_attribute")
    val idProductAttribute: Int = 0,
    @SerializedName("product_combinations")
    val productCombinations: List<ProductCombinationsItem> = listOf(),
    @SerializedName("productimages")
    val productImages: List<ProductImagesItem> = listOf(),
    @SerializedName("width")
    val width: String = "",
    @SerializedName("date_upd")
    val dateUpd: String = "",
    @SerializedName("ecotax")
    val ecoTax: String = "",
    @SerializedName("location")
    val location: String = "",
    @SerializedName("category")
    val category: String = "",
    @SerializedName("id_shop_default")
    val idShopDefault: Int = 0,
    @SerializedName("price_tax_exc")
    val priceTaxExc: Double = 0.0,
    @SerializedName("price_without_reduction")
    val priceWithoutReduction: Double = 0.0,
    @SerializedName("reduction")
    val reduction: Double = 0.0,
    @SerializedName("is_discount")
    val isDiscount: Boolean = false,
    @SerializedName("specific_prices")
    val specificPrices: Any = Any(),
    @SerializedName("final_price")
    val finalPrice: Double = 0.0,
    @SerializedName("final_display_price")
    val finalDisplayPrice: String = ""
)

// val specificPrices: Reduction = Reduction()
data class ProductCombinationsItem(
    @SerializedName("is_color_group")
    val isColorGroup: String = "",
    @SerializedName("quantity")
    val quantity: Int = 0,
    @SerializedName("id_attribute_group")
    val idAttributeGroup: String = "",
    @SerializedName("id_attribute")
    val idAttribute: String = "",
    @SerializedName("group_name")
    val groupName: String = "",
    @SerializedName("available_date")
    val availableDate: String = "",
    @SerializedName("ean13")
    val ean: String = "",
    @SerializedName("upc")
    val upc: String = "",
    @SerializedName("weight")
    val weight: String = "",
    @SerializedName("supplier_reference")
    val supplierReference: String = "",
    @SerializedName("unit_price_impact")
    val unitPriceImpact: String = "",
    @SerializedName("default_on")
    val defaultOn: String = "",
    @SerializedName("reference")
    val reference: String = "",
    @SerializedName("minimal_quantity")
    val minimalQuantity: String = "",
    @SerializedName("id_product")
    val idProduct: String = "",
    @SerializedName("id_product_attribute")
    val idProductAttribute: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("wholesale_price")
    val wholesalePrice: String = "",
    @SerializedName("id_shop")
    val idShop: String = "",
    @SerializedName("location")
    val location: String = "",
    @SerializedName("ecotax")
    val ecotax: String = "",
    @SerializedName("attribute_name")
    val attributeName: String = "",
    @SerializedName("attribute_image")
    val attributeImage: String = ""
)


data class ProductImagesItem(
    @SerializedName("cover")
    val cover: String = "",
    @SerializedName("id_image")
    val idImage: String = "",
    @SerializedName("legend")
    val legend: String = "",
    @SerializedName("image_url")
    val imageUrl: String = "",
    @SerializedName("position")
    val position: String = ""
)




