package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class ShoppingCartResponse(
    @SerializedName("result")
    val result: ShoppingCartResult,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Int = 0
)

data class ShoppingCartResult(
    @SerializedName("id_address_invoice")
    val idAddressInvoice: Int = 0,
    @SerializedName("cart_summary")
    val cartSummary: CartSummary,
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

data class Invoice(
    @SerializedName("country")
    val country: String = "",
    @SerializedName("firstname")
    val firstname: String = "",
    @SerializedName("other")
    val other: String = "",
    @SerializedName("city")
    val city: String = "",
    @SerializedName("id_customer")
    val idCustomer: String = "",
    @SerializedName("vat_number")
    val vatNumber: String = "",
    @SerializedName("id_supplier")
    val idSupplier: String = "",
    @SerializedName("alias")
    val alias: String = "",
    @SerializedName("force_id")
    val forceId: Boolean = false,
    @SerializedName("company")
    val company: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("id_state")
    val idState: String = "",
    @SerializedName("dni")
    val dni: String = "",
    @SerializedName("phone_mobile")
    val phoneMobile: String = "",
    @SerializedName("address2")
    val addressTwo: String = "",
    @SerializedName("address1")
    val addressOne: String = "",
    @SerializedName("postcode")
    val postcode: String = "",
    @SerializedName("id_shop_list")
    val idShopList: Any = Any(),
    @SerializedName("id_manufacturer")
    val idManufacturer: String = "",
    @SerializedName("lastname")
    val lastname: String = "",
    @SerializedName("date_add")
    val dateAdd: String = "",
    @SerializedName("id_country")
    val idCountry: String = "",
    @SerializedName("deleted")
    val deleted: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("date_upd")
    val dateUpd: String = "",
    @SerializedName("id_warehouse")
    val idWarehouse: String = ""
)


data class FormattedAddresses(
    @SerializedName("delivery")
    val delivery: Delivery,
    @SerializedName("invoice")
    val invoice: Invoice
)


data class CartSummary(
    @SerializedName("delivery")
    val delivery: Delivery,
    @SerializedName("total_shipping_tax_exc")
    val totalShippingTaxExc: Double = 0.0,
    @SerializedName("total_wrapping_tax_exc")
    val totalWrappingTaxExc: Double = 0.0,
    @SerializedName("total_price")
    val totalPrice: Double = 0.0,
    @SerializedName("is_multi_address_delivery")
    val isMultiAddressDelivery: Boolean = false,
    @SerializedName("free_ship")
    val freeShip: Boolean = false,
    @SerializedName("invoice_state")
    val invoiceState: String = "",
    @SerializedName("is_virtual_cart")
    val isVirtualCart: Int = 0,
    @SerializedName("delivery_state")
    val deliveryState: String = "",
    @SerializedName("formattedAddresses")
    val formattedAddresses: FormattedAddresses,
    @SerializedName("total_tax")
    val totalTax: Double = 0.0,
    @SerializedName("total_discounts_tax_exc")
    val totalDiscountsTaxExc: Double = 0.0,
    @SerializedName("products")
    val products: List<Product> = listOf(),
    @SerializedName("total_discounts")
    val totalDiscounts: Double = 0.0,
    @SerializedName("carrier")
    val carrier: CarrierShippingCart,
    @SerializedName("total_price_without_tax")
    val totalPriceWithoutTax: Double = 0.0,
    @SerializedName("total_shipping")
    val totalShipping: Double = 0.0,
    @SerializedName("total_products_wt")
    val totalProductsWt: Double = 0.0,
    @SerializedName("total_wrapping")
    val totalWrapping: Double = 0.0,
    @SerializedName("invoice")
    val invoice: Invoice,
    @SerializedName("total_products")
    val totalProducts: Double = 0.0,
    @SerializedName("discounts")
    val discounts: List<DiscountsItem> = listOf()
)

data class DiscountsItem(
    @SerializedName("quantity_per_user")
    val quantityPerUser: String = "",
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
    @SerializedName("id_discount")
    val idDiscount: String = "",
    @SerializedName("country_restriction")
    val countryRestriction: String = "",
    @SerializedName("highlight")
    val highlight: String = "",
    @SerializedName("id_lang")
    val idLang: String = "",
    @SerializedName("cart_rule_restriction")
    val cartRuleRestriction: String = "",
    @SerializedName("product_restriction")
    val productRestriction: String = "",
    @SerializedName("gift_product_attribute")
    val giftProductAttribute: String = "",
    @SerializedName("value_tax_exc")
    val valueTaxExc: Double = 0.0,
    @SerializedName("quantity")
    val quantity: String = "",
    @SerializedName("value_real")
    val valueReal: Double = 0.0,
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
    @SerializedName("obj")
    val obj: Obj,
    @SerializedName("reduction_tax")
    val reductionTax: String = "",
    @SerializedName("date_upd")
    val dateUpd: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("id_cart")
    val idCart: String = "",
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

data class Obj(
    @SerializedName("quantity_per_user")
    val quantityPerUser: String = "",
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
    @SerializedName("cart_rule_restriction")
    val cartRuleRestriction: String = "",
    @SerializedName("product_restriction")
    val productRestriction: String = "",
    @SerializedName("force_id")
    val forceId: Boolean = false,
    @SerializedName("gift_product_attribute")
    val giftProductAttribute: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("quantity")
    val quantity: String = "",
    @SerializedName("id_shop_list")
    val idShopList: Any = Any(),
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
    @SerializedName("reduction_tax")
    val reductionTax: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("date_upd")
    val dateUpd: String = "",
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

data class Formated(
    @SerializedName("State:name")
    val stateName: String = "",
    @SerializedName("Country:name")
    val countryName: String = "",
    @SerializedName("firstname")
    val firstname: String = "",
    @SerializedName("phone_mobile")
    val phoneMobile: String = "",
    @SerializedName("address2")
    val addressTwo: String = "",
    @SerializedName("city")
    val city: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("vat_number")
    val vatNumber: String = "",
    @SerializedName("address1")
    val addressOne: String = "",
    @SerializedName("postcode")
    val postcode: String = "",
    @SerializedName("company")
    val company: String = "",
    @SerializedName("lastname")
    val lastname: String = ""
)


data class Object(
    @SerializedName("country")
    val country: String = "",
    @SerializedName("firstname")
    val firstname: String = "",
    @SerializedName("other")
    val other: String = "",
    @SerializedName("city")
    val city: String = "",
    @SerializedName("id_customer")
    val idCustomer: String = "",
    @SerializedName("vat_number")
    val vatNumber: String = "",
    @SerializedName("id_supplier")
    val idSupplier: String = "",
    @SerializedName("alias")
    val alias: String = "",
    @SerializedName("force_id")
    val forceId: Boolean = false,
    @SerializedName("company")
    val company: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("id_state")
    val idState: String = "",
    @SerializedName("dni")
    val dni: String = "",
    @SerializedName("phone_mobile")
    val phoneMobile: String = "",
    @SerializedName("address2")
    val addressTwo: String = "",
    @SerializedName("address1")
    val addressOne: String = "",
    @SerializedName("postcode")
    val postcode: String = "",
    @SerializedName("id_manufacturer")
    val idManufacturer: String = "",
    @SerializedName("lastname")
    val lastname: String = "",
    @SerializedName("date_add")
    val dateAdd: String = "",
    @SerializedName("id_country")
    val idCountry: String = "",
    @SerializedName("deleted")
    val deleted: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("date_upd")
    val dateUpd: String = "",
    @SerializedName("id_warehouse")
    val idWarehouse: String = ""
)

data class Delivery(
    @SerializedName("ordered")
    val ordered: List<String>?,
    @SerializedName("formated")
    val formated: Formated,
    @SerializedName("object")
    val objects: Object
)

data class CarrierShippingCart(
    @SerializedName("max_depth")
    val maxDepth: Any = Any(),
    @SerializedName("id_shop_list")
    val idShopList: Any = Any(),
    @SerializedName("id_reference")
    val idReference: Any = Any(),
    @SerializedName("active")
    val active: Boolean = false,
    @SerializedName("max_height")
    val maxHeight: Any = Any(),
    @SerializedName("shipping_handling")
    val shippingHandling: Boolean = false,
    @SerializedName("shipping_external")
    val shippingExternal: Double = 0.0,
    @SerializedName("url")
    val url: Any = Any(),
    @SerializedName("need_range")
    val needRange: Double = 0.0,
    @SerializedName("max_width")
    val maxWidth: Any = Any(),
    @SerializedName("max_weight")
    val maxWeight: Any = Any(),
    @SerializedName("delay")
    val delay: Any = Any(),
    @SerializedName("deleted")
    val deleted: Int = 0,
    @SerializedName("is_module")
    val isModule: Any = Any(),
    @SerializedName("shipping_method")
    val shippingMethod: Int = 0,
    @SerializedName("is_free")
    val isFree: Boolean = false,
    @SerializedName("grade")
    val grade: Any = Any(),
    @SerializedName("name")
    val name: Any = Any(),
    @SerializedName("force_id")
    val forceId: Boolean = false,
    @SerializedName("range_behavior")
    val rangeBehavior: Any = Any(),
    @SerializedName("position")
    val position: Any = Any(),
    @SerializedName("id")
    val id: Any = Any(),
    @SerializedName("external_module_name")
    val externalModuleName: Any = Any()
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
                        val weight: String = "",
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
                        val customizationQuantity: Any = Any(),
                        @SerializedName("is_virtual")
                        val isVirtual: String = "",
                        @SerializedName("out_of_stock")
                        val outOfStock: String = "",
                        @SerializedName("id_customization")
                        val idCustomization: Any = Any(),
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
                        val supplierReference: Any = Any(),
                        @SerializedName("price_with_reduction")
                        val priceWithReduction: Int = 0,
                        @SerializedName("id_supplier")
                        val idSupplier: String = "",
                        @SerializedName("price_attribute")
                        val priceAttribute: Any = Any(),
                        @SerializedName("unity")
                        val unity: String = "",
                        @SerializedName("minimal_quantity")
                        val minimalQuantity: String = "",
                        @SerializedName("total")
                        val total: Double = 0.0,
                        @SerializedName("rate")
                        val rate: Int = 0,
                        @SerializedName("ecotax_attr")
                        val ecotaxAttr: Any = Any(),
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
                        @SerializedName("depth")
                        val depth: String = "",
                        @SerializedName("id_product_attribute")
                        val idProductAttribute: String = "",
                        @SerializedName("weight_attribute")
                        val weightAttribute: Any = Any(),
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
                        @SerializedName("category")
                        val category: String = "",
                        @SerializedName("quantity_available")
                        val quantityAvailable: String = "")*/




