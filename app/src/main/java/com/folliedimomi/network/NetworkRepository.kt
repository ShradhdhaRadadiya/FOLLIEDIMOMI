package com.folliedimomi.network

//import com.dp.directportal.data.db.AppDatabase
import com.folliedimomi.model.*
import com.pcs.ciprianicouture.model.*
import com.folliedimomi.utils.convertBody
import okhttp3.RequestBody

class NetworkRepository(private val api: MyApi/*, private val session: AppSession*/) : SafeApiRequest() {


    suspend fun onStripeAuth(amount: String, userId: String, cardHolder: String, currency: String = ""): StripeAuthentication {
        return apiRequest {
            api.onStripeAuth(amount = amount, user_id = userId, card_holder = cardHolder, currency = currency)
        }
    }
    suspend fun onPayPalAuth(nonce: String, amount: String, deviceData: String): PaypalPaymentModel {
        return apiRequest {
            api.onPaypalAuth(nonce = nonce, amount = amount, deviceData = deviceData)
        }
    }

    suspend fun onLogin(email: String, psw: String): LoginResponse {
        return apiRequest {
            api.onSupplierLogin(email = email, password = psw)
        }
    }

    suspend fun onForgotPassword(email: String): ForgotPasswordResponse {
        return apiRequest {
            api.onForgotPassword(email = email)
        }
    }

    suspend fun onRegister(mMap: HashMap<String, RequestBody>): RegisterResponse {
        return apiRequest {
            api.onRegister(map = mMap)
        }
    }

    suspend fun onChangePassword(mMap: HashMap<String, RequestBody>): ChangePasswordResponse {
        return apiRequest {
            api.onChangePassword(map = mMap)
        }
    }

    suspend fun onUpdateProfile(mMap: HashMap<String, RequestBody>): UpdateProfileResponse {
        return apiRequest {
            api.onUpdateProfile(map = mMap)
        }
    }

    suspend fun getProducts(catId: String, userId: String): ProductResponse {
        val response = apiRequest {
            api.getProducts(category_id = catId, id_customer = userId)
        }
        return response
    }

    suspend fun getProductDetail(productId: String, userId: String, idProductAttribute: String): ProductDetailResponse {
        val response = apiRequest {
            api.getProductDetail(id_product = productId, id_customer = userId, id_product_attribute = idProductAttribute)
        }
        return response
    }

    suspend fun getProductImages(productId: String, attributeId: String): ProductImages {
        val response = apiRequest {
            api.getProductImages(id_product = productId, id_attribute = attributeId)
        }
        return response
    }

    suspend fun getFilterProducts(sortBy: String, catId: String, userId: String): FilterProduct {
        return apiRequest {
            api.getFilterProducts(sort_by = sortBy, current_id_category = catId, id_customer = userId)
        }
    }

    suspend fun getCustomization(productId: String, userId: String): CustomizeResponse {
        return apiRequest {
            api.getCustomization(id_product = productId, id_customer = userId)
        }
    }

    suspend fun searchProducts(searchText: String, userId: String): ProductResponse {
        return apiRequest {
            api.getSearchProducts(queryString = searchText, id_customer = userId)
        }
    }

    suspend fun getSortBy(): SortByResponse {
        return apiRequest {
            api.getSortBy()
        }
    }

    suspend fun getPromotion(): PromotionResponse {
        return apiRequest {
            api.getPromotion()
        }
    }

    suspend fun getCoupon(id_cart: String = "", id_customer: String = ""): CouponCodeResponse {
        return apiRequest {
            api.getCoupon(id_cart = id_cart, id_customer = id_customer)
        }
    }

    suspend fun onApplyCoupon(couponCode: String, id_cart: String, id_customer: String = ""): ApplyCouponRespone {
        return apiRequest {
            api.onApplyCoupon(coupancode = couponCode, id_cart = id_cart, id_customer = id_customer)
        }
    }

    suspend fun onRemoveCoupon(id_cart_rule: String, id_cart: String): ApplyCouponRespone {
        return apiRequest {
            api.onRemoveCoupon(id_cart_rule = id_cart_rule, id_cart = id_cart)
        }
    }

    /*suspend fun onUpdateCart(id_customer :String,id_product: String,id_product_attribute: String,quantity: String,customersessionid: String,upDown: String): AddToCartResponse {
        return apiRequest {
            api.onUpdateCart(id_customer = id_customer,id_product = id_product,id_product_attribute = id_product_attribute,quantity = quantity,customersessionid = customersessionid, updown = upDown)
        }
    }*/

    suspend fun onUpdateCartQty(id_customer: String, id_product: String, id_product_attribute: String, quantity: String, customersessionid: String, upDown: String, idCart: String): UpdateQty {
        return apiRequest {
            api.onUpdateQty(id_product = id_product, id_product_attribute = id_product_attribute, quantity = quantity, customersessionid = customersessionid, updownlabel = upDown, id_cart = idCart)
        }
    }

    suspend fun addToCart(mMap: HashMap<String, RequestBody>/*customerId: String, productId: String, qty: String, shopId: String*/): AddToCartResponse {
        return apiRequest {
            api.addToCart(map = mMap)
        }
    }

    suspend fun removeItemFromCart(cartId: String, customerId: String, productId: String): AddToCartResponse {
        val mMap = HashMap<String, RequestBody>()
        mMap["controller"] = "mobileapi".convertBody()
        mMap["op"] = "removecartitem".convertBody()
        mMap["id_cart"] = cartId.convertBody()
        mMap["id_product"] = productId.convertBody()
        mMap["id_customer"] = customerId.convertBody()
        /*  mMap["id_product_attribute"] = "".convertBody()
          mMap["id_customization"] = "0".convertBody()
          mMap["id_address_delivery"] = "".convertBody()*/
        return apiRequest {
            api.removeFromCart(map = mMap)
        }
    }

    suspend fun removeFromCartGet(cartId: String, customerId: String, productId: String, productAttribute: String, sessionId: String, idCustomization: Int): AddToCartResponse {
        return apiRequest {
            api.removeFromCartGet(id_cart = cartId, id_product = productId, id_product_attribute = productAttribute, id_customer = customerId, customersessionid = sessionId,idCustomization = idCustomization)
        }
    }

    suspend fun getShoppingCart(userId: String, secureKey: String): ShoppingCartResponse {
        return apiRequest {
            api.getShoppingCart(id_customer = userId, customersessionid = secureKey)
        }
    }
    /*suspend fun getShoppingCart(userId: String, secureKey: String): ShoppingCartResponse {
        return apiRequest {
            api.getShoppingCart(id_customer = userId, secure_key = secureKey)
        }
    }*/

    suspend fun createOrder(map: HashMap<String, RequestBody>): CreateOrderResponse {
        return apiRequest {
            api.createOrder(map = map)
        }
    }

    suspend fun getOrder(userId: String): OrderResponse {
        return apiRequest {
            api.getOrder(id_customer = userId)
        }
    }

    suspend fun getOrderDetail(orderId: String): OrderDetailResponse {
        return apiRequest {
            api.getOrderDetail(id_order = orderId)
        }
    }

    suspend fun getAddresses(customerId: String): AddressResponse {
        return apiRequest {
            api.getAddresses(id_customer = customerId)
        }
    }

    suspend fun addAddress(map: HashMap<String, RequestBody>): AddAddressResponse {
        return apiRequest {
            api.addAddress(map = map)
        }
    }

    suspend fun deleteAddress(addressId: String): ResponseApi {
        return apiRequest {
            api.deleteAddresses(id_address = addressId)
        }
    }

    suspend fun getCarrier(userId: String, cartId: String, addId: String, invoiceId: String, shopId: String): CarrierResponse {
        return apiRequest {
            api.getCarrier(id_customer = userId, id_cart = cartId, id_address_delivery = addId, id_address_invoice = invoiceId, shop_id = shopId)
        }
    }

    suspend fun orderConfirm(userId: String, cartId: String, orderId: String): OrderCompleteResponse {
        return apiRequest {
            api.orderConfirm(id_customer = userId, id_cart = cartId, id_order = orderId)
        }
    }

    suspend fun getCountry(): CountryRespnse {
        return apiRequest {
            api.getCountryList()
        }
    }

    suspend fun getStateByCountry(countryId: String): StateRespnse {
        return apiRequest {
            api.getStateList(id_country = countryId)
        }
    }

    suspend fun getColors(): List<ImageColors> {
        return apiRequest {
            api.getColors()
        }
    }

    suspend fun getProjectFromServer(): Project {
        val response = apiRequest {
            api.getProject()
        }
        /*response.let {
            it?.let {
                quotes.postValue(response.result)
                //isStoreReady.postValue(true)
            }
        }*/
        return response
    }
}