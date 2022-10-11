package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class ProductDetailsModel(
    @SerializedName("message")
    var message: String,
    @SerializedName("result")
    var result: Result,
    @SerializedName("status")
    var status: Int
) {
    data class Result(
        @SerializedName("allow_oosp")
        var allowOosp: Int,
        @SerializedName("attachments")
        var attachments: List<Any>,
        @SerializedName("attribute_price")
        var attributePrice: Int,
        @SerializedName("bar_width")
        var bar_width: Int,
        @SerializedName("bar_class")
        var bar_class: String,
        @SerializedName("category")
        var category: String,
        @SerializedName("category_name")
        var categoryName: String,
        @SerializedName("customization_required")
        var customizationRequired: Boolean,
        @SerializedName("description")
        var description: String,
        @SerializedName("disponibilita")
        var disponibilita: String,
        @SerializedName("ecotax_rate")
        var ecotaxRate: Int,
        @SerializedName("features")
        var features: List<Any>,
        @SerializedName("final_display_price")
        var finalDisplayPrice: String,
        @SerializedName("final_price")
        var finalPrice: Double,
        @SerializedName("id_category_default")
        var idCategoryDefault: Int,
        @SerializedName("id_image")
        var idImage: String,
        @SerializedName("id_manufacturer")
        var idManufacturer: Int,
        @SerializedName("id_product")
        var idProduct: Int,
        @SerializedName("id_product_attribute")
        var idProductAttribute: Int,
        @SerializedName("id_shop_default")
        var idShopDefault: Int,
        @SerializedName("id_supplier")
        var idSupplier: Int,
        @SerializedName("id_tax_rules_group")
        var idTaxRulesGroup: Int,
        @SerializedName("link")
        var link: String,
        @SerializedName("minimal_quantity")
        var minimalQuantity: Int,
        @SerializedName("name")
        var name: String,
        @SerializedName("nopackprice")
        var nopackprice: Int,
        @SerializedName("pack")
        var pack: Boolean,
        @SerializedName("packItems")
        var packItems: List<Any>,
        @SerializedName("price")
        var price: Double,
        @SerializedName("price_tax_exc")
        var priceTaxExc: Double,
        @SerializedName("price_without_reduction")
        var priceWithoutReduction: Double,
        @SerializedName("price_without_reduction_without_tax")
        var priceWithoutReductionWithoutTax: Double,
        @SerializedName("product_combinations")
        var productCombinations: List<Any>,
        @SerializedName("product_image")
        var productImage: String,
        @SerializedName("productimages")
        var productimages: List<Productimage>,
        @SerializedName("quantity")
        var quantity: Int,
        @SerializedName("quantity_all_versions")
        var quantityAllVersions: Int,
        @SerializedName("rate")
        var rate: Int,
        @SerializedName("reduction")
        var reduction: Int,
        @SerializedName("reduction_without_tax")
        var reductionWithoutTax: Int,
        @SerializedName("sales_number")
        var salesNumber: String,
        @SerializedName("specific_prices")
        var specificPrices: List<Any>,
        @SerializedName("tax_name")
        var taxName: String,
        @SerializedName("unit_price")
        var unitPrice: Int,
        @SerializedName("video")
        var video: List<Video>,
        @SerializedName("virtual")
        var virtual: Int
    ) {
        data class Productimage(
            @SerializedName("cover")
            var cover: String,
            @SerializedName("id_image")
            var idImage: String,
            @SerializedName("image_url")
            var imageUrl: String,
            @SerializedName("legend")
            var legend: String,
            @SerializedName("position")
            var position: String
        )

        data class Video(
            @SerializedName("id_lang")
            var idLang: String,
            @SerializedName("id_product")
            var idProduct: String,
            @SerializedName("id_productvideo")
            var idProductvideo: String,
            @SerializedName("id_shop")
            var idShop: String,
            @SerializedName("id_video")
            var idVideo: String,
            @SerializedName("position")
            var position: String,
            @SerializedName("provider")
            var provider: String,
            @SerializedName("title")
            var title: String,
            @SerializedName("video_url")
            var videoUrl: String
        )
    }
}