package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class ProductListModel(
    @SerializedName("message")
    var message: String,
    @SerializedName("result")
    var result: Result,
    @SerializedName("status")
    var status: Int
) {
    data class Result(
        @SerializedName("current_id_category")
        var currentIdCategory: Int,
        @SerializedName("filter_categories")
        var filterCategories: List<FilterCategory>,
        @SerializedName("products")
        var products: List<Product>,
        @SerializedName("total_filter_products")
        var totalFilterProducts: Int
    ) {
        data class FilterCategory(
            @SerializedName("id_category")
            var idCategory: Int,
            @SerializedName("name")
            var name: String
        )

        data class Video(
            @SerializedName("id_productvideo")
            var id_productvideo: String,
            @SerializedName("id_product")
            var id_product: String,
            @SerializedName("id_video")
            var id_video: String,
            @SerializedName("title")
            var title: String,
            @SerializedName("provider")
            var provider: String,
            @SerializedName("video_url")
            var video_url: String,
            @SerializedName("position")
            var position: String,
            @SerializedName("id_lang")
            var id_lang: String,
            @SerializedName("id_shop")
            var id_shop: String,


            )

        data class Product(
            @SerializedName("active")
            var active: String,
            @SerializedName("additional_delivery_times")
            var additionalDeliveryTimes: String,
            @SerializedName("additional_shipping_cost")
            var additionalShippingCost: String,
            @SerializedName("advanced_stock_management")
            var advancedStockManagement: String,
            @SerializedName("available_date")
            var availableDate: String,
            @SerializedName("available_for_order")
            var availableForOrder: String,
            @SerializedName("available_later")
            var availableLater: String,
            @SerializedName("available_now")
            var availableNow: String,
            @SerializedName("bar_class")
            var barClass: String,
            @SerializedName("bar_width")
            var barWidth: Int,
            @SerializedName("base_price")
            var basePrice: String,
            @SerializedName("cache_default_attribute")
            var cacheDefaultAttribute: String,
            @SerializedName("cache_has_attachments")
            var cacheHasAttachments: String,
            @SerializedName("cache_is_pack")
            var cacheIsPack: String,
            @SerializedName("cart_pictures")
            var cartPictures: List<Any>,
            @SerializedName("cart_text_fields")
            var cartTextFields: List<Any>,
            @SerializedName("category")
            var category: String,
            @SerializedName("condition")
            var condition: String,
            @SerializedName("customizable")
            var customizable: String,
            @SerializedName("customization_required")
            var customizationRequired: Boolean,
            @SerializedName("date_add")
            var dateAdd: String,
            @SerializedName("date_upd")
            var dateUpd: String,
            @SerializedName("delivery_in_stock")
            var deliveryInStock: String?,
            @SerializedName("delivery_out_stock")
            var deliveryOutStock: String?,
            @SerializedName("depends_on_stock")
            var dependsOnStock: Boolean,
            @SerializedName("depth")
            var depth: String,
            @SerializedName("description")
            var description: String,
            @SerializedName("description_short")
            var descriptionShort: String,
            @SerializedName("display_price")
            var displayPrice: String,
            @SerializedName("disponibilita")
            var disponibilita: String,
            @SerializedName("ean13")
            var ean13: String,
            @SerializedName("ecotax")
            var ecotax: String,
            @SerializedName("features")
            var features: List<Any>,
            @SerializedName("force_id")
            var forceId: Boolean,
            @SerializedName("height")
            var height: String,
            @SerializedName("id")
            var id: Int,
            @SerializedName("id_category_default")
            var idCategoryDefault: String,
            @SerializedName("id_color_default")
            var idColorDefault: Int,
            @SerializedName("id_manufacturer")
            var idManufacturer: String,
            @SerializedName("id_pack_product_attribute")
            var idPackProductAttribute: Any?,
            @SerializedName("id_product")
            var idProduct: Int,
            @SerializedName("id_product_attribute")
            var idProductAttribute: Int,
            @SerializedName("id_shop_default")
            var idShopDefault: String,
            @SerializedName("id_shop_list")
            var idShopList: List<Any>,
            @SerializedName("id_supplier")
            var idSupplier: String,
            @SerializedName("id_tax_rules_group")
            var idTaxRulesGroup: String,
            @SerializedName("id_type_redirected")
            var idTypeRedirected: String,
            @SerializedName("indexed")
            var indexed: String,
            @SerializedName("isFullyLoaded")
            var isFullyLoaded: Boolean,
            @SerializedName("is_virtual")
            var isVirtual: String,
            @SerializedName("isbn")
            var isbn: String,
            @SerializedName("link_rewrite")
            var linkRewrite: String,
            @SerializedName("location")
            var location: String,
            @SerializedName("low_stock_alert")
            var lowStockAlert: String,
            @SerializedName("low_stock_threshold")
            var lowStockThreshold: Any?,
            @SerializedName("manufacturer_name")
            var manufacturerName: String,
            @SerializedName("meta_description")
            var metaDescription: String,
            @SerializedName("meta_keywords")
            var metaKeywords: String,
            @SerializedName("meta_title")
            var metaTitle: String,
            @SerializedName("minimal_quantity")
            var minimalQuantity: String,
            @SerializedName("name")
            var name: String,
            @SerializedName("new")
            var new: Int,
            @SerializedName("on_sale")
            var onSale: String,
            @SerializedName("online_only")
            var onlineOnly: String,
            @SerializedName("out_of_stock")
            var outOfStock: Int,
            @SerializedName("pack_stock_type")
            var packStockType: String,
            @SerializedName("price")
            var price: String,
            @SerializedName("product_image")
            var productImage: String,
            @SerializedName("quantity")
            var quantity: Int,
            @SerializedName("quantity_discount")
            var quantityDiscount: String,
            @SerializedName("redirect_type")
            var redirectType: String,
            @SerializedName("reference")
            var reference: String,
            @SerializedName("sales_number")
            var salesNumber: String,
            @SerializedName("show_condition")
            var showCondition: String,
            @SerializedName("show_price")
            var showPrice: String,
            @SerializedName("specificPrice")
            var specificPrice: List<Any>,
            @SerializedName("state")
            var state: String,
            @SerializedName("supplier_name")
            var supplierName: Boolean,
            @SerializedName("supplier_reference")
            var supplierReference: String,
            @SerializedName("tags")
            var tags: Boolean,
            @SerializedName("tax_name")
            var taxName: String,
            @SerializedName("tax_rate")
            var taxRate: Int,
            @SerializedName("text_fields")
            var textFields: String,
            @SerializedName("unit_price")
            var unitPrice: Int,
            @SerializedName("unit_price_ratio")
            var unitPriceRatio: String,
            @SerializedName("unity")
            var unity: String,
            @SerializedName("upc")
            var upc: String,
            @SerializedName("uploadable_files")
            var uploadableFiles: String,
            @SerializedName("video")
            var video: List<Video>,
            @SerializedName("visibility")
            var visibility: String,
            @SerializedName("weight")
            var weight: String,
            @SerializedName("wholesale_price")
            var wholesalePrice: String,
            @SerializedName("width")
            var width: String
        )
    }


}