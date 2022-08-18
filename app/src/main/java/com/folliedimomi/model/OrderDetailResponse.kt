package com.pcs.ciprianicouture.model


import com.google.gson.annotations.SerializedName

data class OrderDetailResponse(
        @SerializedName("result")
        val result: OrderDetailResult ,
        @SerializedName("message")
        val message: String = "",
        @SerializedName("status")
        val status: Int = 0
)

data class InvoiceAddress(
        @SerializedName("country")
        val country: String = "",
        @SerializedName("firstname")
        val firstname: String = "",
        @SerializedName("phone_mobile")
        val phoneMobile: String = "",
        @SerializedName("address2")
        val addressTwo: String = "",
        @SerializedName("city")
        val city: String = "",
        @SerializedName("id_customer")
        val idCustomer: Int = 0,
        @SerializedName("address1")
        val addressOne: String = "",
        @SerializedName("vat_number")
        val vatNumber: String = "",
        @SerializedName("postcode")
        val postcode: String = "",
        @SerializedName("lastname")
        val lastname: String = "",
        @SerializedName("id_address")
        val idAddress: Int = 0,
        @SerializedName("id_country")
        val idCountry: Int = 0,
        @SerializedName("phone")
        val phone: String = "",
        @SerializedName("alias")
        val alias: String = "",
        @SerializedName("company")
        val company: String = "",
        @SerializedName("id_state")
        val idState: Int = 0
)

data class DelivaryAddress(
        @SerializedName("country")
        val country: String = "",
        @SerializedName("firstname")
        val firstname: String = "",
        @SerializedName("phone_mobile")
        val phoneMobile: String = "",
        @SerializedName("address2")
        val addressTwo: String = "",
        @SerializedName("city")
        val city: String = "",
        @SerializedName("id_customer")
        val idCustomer: Int = 0,
        @SerializedName("address1")
        val addressOne: String = "",
        @SerializedName("vat_number")
        val vatNumber: String = "",
        @SerializedName("postcode")
        val postcode: String = "",
        @SerializedName("lastname")
        val lastname: String = "",
        @SerializedName("id_address")
        val idAddress: Int = 0,
        @SerializedName("id_country")
        val idCountry: Int = 0,
        @SerializedName("phone")
        val phone: String = "",
        @SerializedName("alias")
        val alias: String = "",
        @SerializedName("company")
        val company: String = "",
        @SerializedName("id_state")
        val idState: Int = 0
)

data class TaxCalculator(
        @SerializedName("computation_method")
        val computationMethod: Int = 0,
        @SerializedName("taxes")
        val taxes: List<TaxesItem> = listOf()
)

data class Image(
        @SerializedName("cover")
        val cover: String = "",
        @SerializedName("id_product")
        val idProduct: String = "",
        @SerializedName("id_image")
        val idImage: String = "",
        @SerializedName("legend")
        val legend: Any = Any(),
        @SerializedName("id_shop_list")
        val idShopList: Any = Any(),
        @SerializedName("force_id")
        val forceId: Boolean = false,
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("position")
        val position: String = "",
        @SerializedName("image_format")
        val imageFormat: String = "",
        @SerializedName("source_index")
        val sourceIndex: String = ""
)

data class ProductsOrderDetail(
        @SerializedName("product_quantity_in_stock")
        val productQuantityInStock: String = "",
        @SerializedName("download_nb")
        val downloadNb: String = "",
        @SerializedName("product_image")
        val productImage: String = "",
        @SerializedName("advanced_stock_management")
        val advancedStockManagement: String = "",
        @SerializedName("price")
        val price: String = "",
        @SerializedName("product_id")
        val productId: String = "",
        @SerializedName("wholesale_price")
        val wholesalePrice: String = "",
        @SerializedName("id_shop")
        val idShop: String = "",
        @SerializedName("redirect_type")
        val redirectType: String = "",
        @SerializedName("height")
        val height: String = "",
        @SerializedName("product_supplier_reference")
        val productSupplierReference: String = "",
        @SerializedName("quantity_discount")
        val quantityDiscount: String = "",
        @SerializedName("total_price_tax_excl")
        val totalPriceTaxExcl: String = "",
        @SerializedName("indexed")
        val indexed: String = "",
        @SerializedName("text_fields")
        val textFields: String = "",
        @SerializedName("upc")
        val upc: String = "",
        @SerializedName("active")
        val active: String = "",
        @SerializedName("pack_stock_type")
        val packStockType: String = "",
        @SerializedName("id_product_redirected")
        val idProductRedirected: String = "",
        @SerializedName("id_product")
        val idProduct: String = "",
        @SerializedName("customizationQuantityTotal")
        val customizationQuantityTotal: Int = 0,
        @SerializedName("out_of_stock")
        val outOfStock: String = "",
        @SerializedName("additional_shipping_cost")
        val additionalShippingCost: String = "",
        @SerializedName("customizable")
        val customizable: String = "",
        @SerializedName("ean13")
        val ean: String = "",
        @SerializedName("total_shipping_price_tax_incl")
        val totalShippingPriceTaxIncl: String = "",
        @SerializedName("product_quantity")
        val productQuantity: String = "",
        @SerializedName("minimal_quantity")
        val minimalQuantity: String = "",
        @SerializedName("tax_computation_method")
        val taxComputationMethod: String = "",
        @SerializedName("cache_default_attribute")
        val cacheDefaultAttribute: String = "",
        @SerializedName("group_reduction")
        val groupReduction: String = "",
        @SerializedName("product_quantity_return")
        val productQuantityReturn: String = "",
        @SerializedName("id_category_default")
        val idCategoryDefault: String = "",
        @SerializedName("total_shipping_price_tax_excl")
        val totalShippingPriceTaxExcl: String = "",
        @SerializedName("on_sale")
        val onSale: String = "",
        @SerializedName("discount_quantity_applied")
        val discountQuantityApplied: String = "",
        @SerializedName("cache_has_attachments")
        val cacheHasAttachments: String = "",
        @SerializedName("image_size")
        val imageSize: String = "",
        @SerializedName("uploadable_files")
        val uploadableFiles: String = "",
        @SerializedName("show_price")
        val showPrice: String = "",
        @SerializedName("product_ean13")
        val productEan: String = "",
        @SerializedName("available_for_order")
        val availableForOrder: String = "",
        @SerializedName("purchase_supplier_price")
        val purchaseSupplierPrice: String = "",
        @SerializedName("date_add")
        val dateAdd: String = "",
        @SerializedName("depth")
        val depth: String = "",
        @SerializedName("width")
        val width: String = "",
        @SerializedName("ecotax")
        val ecotax: String = "",
        @SerializedName("tax_calculator")
        val taxCalculator: TaxCalculator,
        @SerializedName("original_product_price")
        val originalProductPrice: String = "",
        @SerializedName("unit_price_tax_excl")
        val unitPriceTaxExcl: String = "",
        @SerializedName("ecotax_tax_rate")
        val ecotaxTaxRate: String = "",
        @SerializedName("download_hash")
        val downloadHash: String = "",
        @SerializedName("product_price")
        val productPrice: Double = 0.0,
        @SerializedName("tax_rate")
        val taxRate: Double = 0.0,
        @SerializedName("reference")
        val reference: String = "",
        @SerializedName("id_order")
        val idOrder: String = "",
        @SerializedName("product_quantity_discount")
        val productQuantityDiscount: String = "",
        @SerializedName("image")
        val image: Image,
        @SerializedName("visibility")
        val visibility: String = "",
        @SerializedName("product_reference")
        val productReference: String = "",
        @SerializedName("weight")
        val weight: String = "",
        @SerializedName("product_quantity_reinjected")
        val productQuantityReinjected: String = "",
        @SerializedName("id_manufacturer")
        val idManufacturer: String = "",
        @SerializedName("id_address_delivery")
        val idAddressDelivery: String = "",
        @SerializedName("product_name")
        val productName: String = "",
        @SerializedName("online_only")
        val onlineOnly: String = "",
        @SerializedName("reduction_amount")
        val reductionAmount: String = "",
        @SerializedName("condition")
        val condition: String = "",
        @SerializedName("product_quantity_refunded")
        val productQuantityRefunded: String = "",
        @SerializedName("product_attribute_id")
        val productAttributeId: String = "",
        @SerializedName("cache_is_pack")
        val cacheIsPack: String = "",
        @SerializedName("is_virtual")
        val isVirtual: String = "",
        @SerializedName("product_upc")
        val productUpc: String = "",
        @SerializedName("product_weight")
        val productWeight: String = "",
        @SerializedName("reduction_amount_tax_incl")
        val reductionAmountTaxIncl: String = "",
        @SerializedName("supplier_reference")
        val supplierReference: String = "",
        @SerializedName("id_supplier")
        val idSupplier: String = ""/*,
        @SerializedName("customizedDatas")
        val customizedDatas: Double = 0.0,*/,
        @SerializedName("unity")
        val unity: String = "",
        @SerializedName("total_price_tax_incl")
        val totalPriceTaxIncl: String = "",
        @SerializedName("id_tax_rules_group")
        val idTaxRulesGroup: String = "",
        @SerializedName("unit_price_ratio")
        val unitPriceRatio: String = "",
        @SerializedName("download_deadline")
        val downloadDeadline: String = "",
        @SerializedName("quantity")
        val quantity: String = "",
        @SerializedName("total_price")
        val totalPrice: String = "",
        @SerializedName("product_price_wt")
        val productPriceWt: Double = 0.0,
        @SerializedName("tax_name")
        val taxName: String = "",
        @SerializedName("available_date")
        val availableDate: String = "",
        @SerializedName("id_order_detail")
        val idOrderDetail: String = "",
        @SerializedName("id_order_invoice")
        val idOrderInvoice: String = "",
        @SerializedName("reduction_percent")
        val reductionPercent: String = "",
        @SerializedName("total_wt")
        val totalWt: String = "",
        @SerializedName("unit_price_tax_incl")
        val unitPriceTaxIncl: String = "",
        @SerializedName("current_stock")
        val currentStock: Int = 0,
        @SerializedName("product_price_wt_but_ecotax")
        val productPriceWtButEcotax: Double = 0.0,
        @SerializedName("date_upd")
        val dateUpd: String = "",
        @SerializedName("original_wholesale_price")
        val originalWholesalePrice: String = "",
        @SerializedName("location")
        val location: String = "",
        @SerializedName("id_warehouse")
        val idWarehouse: String = "",
        @SerializedName("id_shop_default")
        val idShopDefault: String = "",
        @SerializedName("reduction_amount_tax_excl")
        val reductionAmountTaxExcl: String = "",
        @SerializedName("order_cart_customization")
        val orderCartCustomization: OrderCustomizationResponse = OrderCustomizationResponse()
)

data class OrderCustomizationResponse(
        @SerializedName("quantity_refunded")
        val quantityRefunded: Int = 0,
        @SerializedName("quantity")
        val quantity: Int = 0,
        @SerializedName("data")
        val data: List<OrderCustomization> = listOf(),
        @SerializedName("custom_price")
        val customPrice: Int = 0
)

data class OrderCustomization(
        @SerializedName("id_customization")
        val idCustomization: String = "",
        @SerializedName("id_product")
        val idProduct: String = "",
        @SerializedName("id_product_attribute")
        val idProductAttribute: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("id_customization_field")
        val idCustomizationField: String = "",
        @SerializedName("index")
        val index: String = "",
        @SerializedName("id_address_delivery")
        val idAddressDelivery: String = "",
        @SerializedName("type")
        val type: String = "",
        @SerializedName("value")
        val value: String = ""
)


data class AddressInfo(
        @SerializedName("invoice_address")
        val invoiceAddress: InvoiceAddress,
        @SerializedName("delivary_address")
        val delivaryAddress: DelivaryAddress
)
/*data class Legend(@SerializedName("1")
                  val : String = "",
                  @SerializedName("2")
                  val : String = "")*/

/*data class Name(
        @SerializedName("1")
        val : String = "",
        @SerializedName("2")
        val : String = ""
)*/

data class TaxesItem(
        @SerializedName("deleted")
        val deleted: String = "",
        @SerializedName("rate")
        val rate: String = "",
        @SerializedName("name")
        val name: Any = Any(),
        @SerializedName("id_shop_list")
        val idShopList: Any = Any(),
        @SerializedName("active")
        val active: String = "",
        @SerializedName("force_id")
        val forceId: Boolean = false,
        @SerializedName("id")
        val id: Int = 0
)

data class OrderDetailResult(
        @SerializedName("id_address_invoice")
        val idAddressInvoice: Int = 0,
        @SerializedName("id_carrier")
        val idCarrier: Int = 0,
        @SerializedName("address_info")
        val addressInfo: AddressInfo,
        @SerializedName("total_paid_tax_excl")
        val totalPaidTaxExcl: String = "",
        @SerializedName("id_customer")
        val idCustomer: Int = 0,
        @SerializedName("current_state")
        val currentState: Int = 0,
        @SerializedName("id_address_delivery")
        val idAddressDelivery: Int = 0,
        @SerializedName("total_shipping_tax_excl")
        val totalShippingTaxExcl: String = "",
        @SerializedName("carrier_tax_rate")
        val carrierTaxRate: String = "",
        @SerializedName("invoice_date")
        val invoiceDate: String = "",
        @SerializedName("products")
        val products: List<ProductsOrderDetail> = listOf(),
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
        @SerializedName("total_shipping")
        val totalShipping: String = "",
        @SerializedName("payment")
        val payment: String = ""
)


