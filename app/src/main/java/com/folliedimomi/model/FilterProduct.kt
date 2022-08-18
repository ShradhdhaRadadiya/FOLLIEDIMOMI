package com.folliedimomi.model
import com.google.gson.annotations.SerializedName


data class FilterProduct(
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("result")
    val result: FilterProductResult = FilterProductResult()
)

data class FilterProductResult(
    @SerializedName("current_id_category")
    val currentIdCategory: Int = 0,
    @SerializedName("filter_categories")
    val filterCategories: List<FilterCategory> = listOf(),
    @SerializedName("products")
    val products: List<Product> = listOf(),
    @SerializedName("total_filter_products")
    val totalFilterProducts: Int = 0
)

data class FilterCategory(
    @SerializedName("id_category")
    val idCategory: Int = 0,
    @SerializedName("name")
    val name: String = ""
)

/*
data class Product(
    @SerializedName("id_manufacturer")
    val idManufacturer: Int = 0,
    @SerializedName("id_supplier")
    val idSupplier: Int = 0,
    @SerializedName("id_category_default")
    val idCategoryDefault: Int = 0,
    @SerializedName("id_shop_default")
    val idShopDefault: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("description_short")
    val descriptionShort: String = "",
    @SerializedName("quantity")
    val quantity: String = "",
    @SerializedName("minimal_quantity")
    val minimalQuantity: String = "",
    @SerializedName("available_now")
    val availableNow: String = "",
    @SerializedName("available_later")
    val availableLater: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("specificPrice")
    val specificPrice: Int = 0,
    @SerializedName("additional_shipping_cost")
    val additionalShippingCost: String = "",
    @SerializedName("wholesale_price")
    val wholesalePrice: String = "",
    @SerializedName("on_sale")
    val onSale: String = "",
    @SerializedName("online_only")
    val onlineOnly: String = "",
    @SerializedName("unity")
    val unity: String = "",
    @SerializedName("unit_price_ratio")
    val unitPriceRatio: String = "",
    @SerializedName("ecotax")
    val ecotax: String = "",
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
    @SerializedName("ean13")
    val ean13: String = "",
    @SerializedName("upc")
    val upc: String = "",
    @SerializedName("link_rewrite")
    val linkRewrite: String = "",
    @SerializedName("meta_description")
    val metaDescription: String = "",
    @SerializedName("meta_keywords")
    val metaKeywords: String = "",
    @SerializedName("meta_title")
    val metaTitle: String = "",
    @SerializedName("quantity_discount")
    val quantityDiscount: String = "",
    @SerializedName("customizable")
    val customizable: String = "",
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
    @SerializedName("date_add")
    val dateAdd: String = "",
    @SerializedName("date_upd")
    val dateUpd: String = "",
    @SerializedName("id_tax_rules_group")
    val idTaxRulesGroup: Int = 0,
    @SerializedName("id_color_default")
    val idColorDefault: Int = 0,
    @SerializedName("advanced_stock_management")
    val advancedStockManagement: String = "",
    @SerializedName("out_of_stock")
    val outOfStock: String = "",
    @SerializedName("isFullyLoaded")
    val isFullyLoaded: Boolean = false,
    @SerializedName("cache_is_pack")
    val cacheIsPack: String = "",
    @SerializedName("cache_has_attachments")
    val cacheHasAttachments: String = "",
    @SerializedName("is_virtual")
    val isVirtual: String = "",
    @SerializedName("cache_default_attribute")
    val cacheDefaultAttribute: String = "",
    @SerializedName("category")
    val category: String = "",
    @SerializedName("pack_stock_type")
    val packStockType: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("force_id")
    val forceId: Boolean = false,
    @SerializedName("product_image")
    val productImage: String = "",
    @SerializedName("id_product")
    val idProduct: Int = 0,
    @SerializedName("id_product_attribute")
    val idProductAttribute: Int = 0,
    @SerializedName("tax_name")
    val taxName: Any = Any(),
    @SerializedName("tax_rate")
    val taxRate: Any = Any(),
    @SerializedName("manufacturer_name")
    val manufacturerName: Any = Any(),
    @SerializedName("supplier_name")
    val supplierName: Any = Any(),
    @SerializedName("unit_price")
    val unitPrice: Any = Any(),
    @SerializedName("new")
    val new: Any = Any(),
    @SerializedName("tags")
    val tags: Any = Any(),
    @SerializedName("base_price")
    val basePrice: Any = Any(),
    @SerializedName("depends_on_stock")
    val dependsOnStock: Any = Any(),
    @SerializedName("id_pack_product_attribute")
    val idPackProductAttribute: Any = Any(),
    @SerializedName("id_shop_list")
    val idShopList: Any = Any()
)*/
