package com.folliedimomi.network

import com.folliedimomi._app.Constant
import com.folliedimomi.model.*
import com.pcs.ciprianicouture.model.OrderDetailResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface MyApi {
//https://cipri-couture.com/paypalapi/src/sendpaypalrequest.php?amount=1
    //http://baleshawebsolutions.com/stripeweb/newcreate_payment.php
    // ?currency=usd&amount=1100&card_holder=Vipul&user_id=132
    //https://cipriani-couture.com/stripeweb/newcreate_payment.php?
    //currency=usd&amount=1100&card_holder=Vipul&user_id=132
    /** Stripe Auth */
    @FormUrlEncoded
    @POST("stripeweb/newcreate_payment.php")
    suspend fun onStripeAuth(
        @Field("amount") amount: String,
        @Field("user_id") user_id: String = "",
        @Field("card_holder") card_holder: String = "",
        @Field("currency") currency: String = "usd"
    ): Response<StripeAuthentication>


    //https://cipri-couture.com/braintree/src/create-payment.php

    @FormUrlEncoded
    @POST("braintree/src/create-payment.php")
    suspend fun onPaypalAuth(
        @Field("payment_method_nonce") nonce: String,
        @Field("amount") amount: String = "",
        @Field("deviceDataFromTheClient") deviceData: String = "",
    ): Response<PaypalPaymentModel>

    /** Login */
    @POST("index.php")
    @FormUrlEncoded
    suspend fun onSupplierLogin(
        @Field(encoded = true, value = "email") email: String,
        @Field("passwd") password: String,
        @Field("op") op: String = "customer_login",
        @Field("controller") controller: String = "mobileapi",
        @Field("id_lang") id_lang: String = Constant.LANG
    ): Response<LoginResponse>

    /** Register */
    @Multipart
    @POST("index.php")
    suspend fun onRegister(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody>): Response<RegisterResponse>

    /** Forgot Password */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun onForgotPassword(
        @Field("controller") controller: String = "customerapi",
        @Field("op") op: String = "forgot_password",
        @Field("email") email: String,
        @Field("id_lang") id_lang: String = Constant.LANG
    ): Response<ForgotPasswordResponse>

    /** Register */
    @Multipart
    @POST("index.php")
    suspend fun onChangePassword(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody>): Response<ChangePasswordResponse>

    /** Update Profile */
    @Multipart
    @POST("index.php")
    suspend fun onUpdateProfile(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody>): Response<UpdateProfileResponse>

    /** Product List */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getProducts(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "category_products",
        @Field("lang_id") id_lang: String = Constant.LANG,
        @Field("category_id") category_id: String,
        @Field("id_customer") id_customer: String = "",
        @Field("page") page: String = "1"
    ): Response<ProductResponse>

    /** Product Detail */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getProductDetail(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "product_detail",
        @Field("lang_id") id_lang: String = Constant.LANG,
        @Field("id_product") id_product: String,
        @Field("id_customer") id_customer: String = "",
        @Field("id_product_attribute") id_product_attribute: String = ""
    ): Response<ProductDetailResponse>

    //https://cipriani-couture.com/en/index.php
    // ?controller=mobileapi
    // &op=productdetailslider
    // &lang_id=2
    // &idproduct=507
    // &id_attribute=2085
    /** Product Detail */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getProductImages(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "productdetailslider",
        @Field("lang_id") id_lang: String = Constant.LANG,
        @Field("idproduct") id_product: String,
        @Field("id_attribute") id_attribute: String = ""
    ): Response<ProductImages>


    /** Product List */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getSearchProducts(
        @Field("controller") controller: String = "customerapi",
        @Field("op") op: String = "search_product",
        @Field("lang_id") id_lang: String = Constant.LANG,
        @Field("queryString") queryString: String,
        @Field("id_customer") id_customer: String = ""
    ): Response<ProductResponse>

    /** Filter Product List */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getFilterProducts(
        @Field("controller") controller: String = "customerapi",
        @Field("op") op: String = "filter_product",
        @Field("lang_id") id_lang: String = Constant.LANG,
        @Field("current_id_category") current_id_category: String,
        @Field("sort_by") sort_by: String,
        @Field("id_customer") id_customer: String = ""
    ): Response<FilterProduct>

    /** Customization */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getCustomization(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "get_product_customization",
        @Field("lang_id") id_lang: String = Constant.LANG,
        @Field("productid") id_product: String, // id_product
        @Field("id_customer") id_customer: String = ""
    ): Response<CustomizeResponse>

    /** SortBy */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getSortBy(
        @Field("controller") controller: String = "customerapi",
        @Field("op") op: String = "filter_sortby",
        @Field("lang_id") id_lang: String = Constant.LANG
    ): Response<SortByResponse>

    /** Home : Promotion */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getPromotion(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "header_topbanner_slider",
        @Field("lang_id") id_lang: String = Constant.LANG
    ): Response<PromotionResponse>

    /** Coupon : Promotion */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getCoupon(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "coupon_list",
        @Field("lang_id") id_lang: String = Constant.LANG,
        @Field("id_cart") id_cart: String = "",
        @Field("id_customer") id_customer: String = ""
    ): Response<CouponCodeResponse>

    /** Coupon : Promotion */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun onApplyCoupon(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "applycouponcode",
        @Field("lang_id") id_lang: String = Constant.LANG,
        @Field("coupancode") coupancode: String,
        @Field("id_cart") id_cart: String = "",
        @Field("id_customer") id_customer: String = ""
    ): Response<ApplyCouponRespone>

    /** Coupon : Promotion */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun onRemoveCoupon(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "removecouponcode",
        @Field("lang_id") id_lang: String = Constant.LANG,
        @Field("id_cart_rule") id_cart_rule: String,
        @Field("id_cart") id_cart: String = ""
    ): Response<ApplyCouponRespone>

    /** Update Cart */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun onUpdateCart(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "addtocart",
        @Field("lang_id") id_lang: String = Constant.LANG,
        @Field("shop_id") shop_id: String = "1",
        @Field("id_customer") id_customer: String,
        @Field("id_product") id_product: String,
        @Field("id_product_attribute") id_product_attribute: String,
        @Field("quantity") quantity: String,
        @Field("customersessionid") customersessionid: String = "",
        @Field("updown") updown: String = "up"
    ): Response<AddToCartResponse>

    /** Update Cart Qty */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun onUpdateQty(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "updateCartProductQty",
        @Field("lang_id") id_lang: String = Constant.LANG,
        @Field("productid") id_product: String,
        @Field("id_product_attribute") id_product_attribute: String,
        @Field("quantity") quantity: String,
        @Field("updown") updownlabel: String = "up",
        @Field("id_cart") id_cart: String = "up"
//        @Field("customersessionid") customersessionid: String = "",
    ): Response<UpdateQty>

    /** Add to cart */
    @Multipart
    @POST("index.php")//
    suspend fun addToCart(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody>): Response<AddToCartResponse>

    /** Remove from cart */
    @Multipart
    @POST("index.php")//
    suspend fun removeFromCart(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody>): Response<AddToCartResponse>


    /** Product List */
    @Multipart
    @POST("index.php")//
    suspend fun productList(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody>):
            Response<ProductListModel>

    /** Product List */
    @Multipart
    @POST("index.php")//
    suspend fun productDetail(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody>):
            Response<ProductDetailsModel>

    /** Remove from cart */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun removeFromCartGet(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "removecartitem",
        @Field("id_cart") id_cart: String = "",
        @Field("id_product") id_product: String = "",
        @Field("id_product_attribute") id_product_attribute: String = "",
        @Field("id_lang") secure_key: String = Constant.LANG,
        @Field("id_customer") id_customer: String = "",
        @Field("customersessionid") customersessionid: String,
//            @Field("id_customization") idCustomization: Int,

    ): Response<AddToCartResponse>

    /** ShoppingCart */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getShoppingCart(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "cart_list",
        @Field("lang_id") secure_key: String = Constant.LANG,
        @Field("id_customer") id_customer: String,
        @Field("customersessionid") customersessionid: String /*@Field("secure_key") secure_key: String*/
    ): Response<ShoppingCartResponse>

//https://cipriani-couture.com/en/index.php?controller=mobileapi&op=cart_list&customersessionid=s136
    /** ShoppingCart */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getGuestShoppingCart(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "cart_list",
        @Field("id_customer") id_customer: String,
        @Field("secure_key") secure_key: String
    ): Response<ShoppingCartResponse>


    /** Create Order */
    @Multipart
    @POST("index.php")
    suspend fun createOrder(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody>): Response<CreateOrderResponse>

    /** Confirm Order */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun orderConfirm(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "order_confirm_thankyou",
        @Field("lang_id") secure_key: String = Constant.LANG,
        @Field("id_order") id_order: String,
        @Field("id_cart") id_cart: String,
        @Field("id_customer") id_customer: String
    ): Response<OrderCompleteResponse>

    /** Order list */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getOrder(
        @Field("controller") controller: String = "customerapi",
        @Field("op") op: String = "order_list",
        @Field("lang_id") secure_key: String = Constant.LANG,
        @Field("id_customer") id_customer: String
    ): Response<OrderResponse>

    /** Order Detail */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getOrderDetail(
        @Field("controller") controller: String = "customerapi",
        @Field("op") op: String = "order_detail",
        @Field("id_order") id_order: String
        // @Field("lang_id") secure_key: String = Constant.LANG
    ): Response<OrderDetailResponse>

    /** Addresses */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getAddresses(
        @Field("controller") controller: String = "customerapi",
        @Field("op") op: String = "address_list",
        @Field("lang_id") lang_id: String = Constant.LANG,
        @Field("id_customer") id_customer: String
    ): Response<AddressResponse>

    /** Add Address */
    @Multipart
    @POST("index.php")
    suspend fun addAddress(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody>): Response<AddAddressResponse>

    /** Carrier */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getCarrier(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "carrier_list",
        @Field("lang_id") lang_id: String = Constant.LANG,
        @Field("id_customer") id_customer: String,
        @Field("id_cart") id_cart: String,
        @Field("id_address_delivery") id_address_delivery: String,
        @Field("id_address_invoice") id_address_invoice: String,
        @Field("shop_id") shop_id: String
    ): Response<CarrierResponse>

    /** Addresses */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun deleteAddresses(
        @Field("controller") controller: String = "customerapi",
        @Field("op") op: String = "delete_address",
        @Field("id_lang") lang_id: String = Constant.LANG,
        @Field("id_address") id_address: String
    ): Response<ResponseApi>

    /** Country List */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getCountryList(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "country_list",
        @Field("lang_id") id_lang: String = Constant.LANG
    ): Response<CountryRespnse>

    /** State List */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getStateList(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "state_list",
        @Field("lang_id") id_lang: String = Constant.LANG,
        @Field("id_country") id_country: String
    ): Response<StateRespnse>

    /** State List
     * https://cipri-couture.com/en/index.php?controller=mobileapi&op=product_color_images*/
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getColors(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "product_color_images",
        @Field("lang_id") id_lang: String = Constant.LANG
    ): Response<List<ImageColors>>

    /** Delete Order*/
    /*@Multipart
    @POST("suppliermyorders.php")//
    suspend fun deleteOrder(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody>)
            : Response<Delete>*/

    @Multipart
    @POST("upload")
    fun uploadMultipleFilesDynamic(
        @Part("description") description: RequestBody,
        @Part files: List<MultipartBody.Part>
    ): Call<ResponseBody>

    @GET("docs/country/")
    suspend fun getProject(): Response<Project>

    //http://baleshawebsolutions.com/stripeweb/newcreate_payment.php?currency=usd&amount=1100&card_holder=Vipul&user_id=132
    /*@GET("quotes")
    suspend fun getQuotes() : Response<QuotesResponse>*/

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): MyApi {

            /*val logger: HttpLoggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)*/  //Logging Interceptor

            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            val okkHttpclient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.MINUTES)
                .writeTimeout(30, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.MINUTES)
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(Constant.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

    /**AdvanceFilter */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getAdvanceFilter(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "advance_filter",
        @Field("id_parent") id_parent: String = "",
        @Field("id_category") id_category: String = "",
        @Field("id_manufacturer") id_manufacturer: String = "",
        @Field("start_price") start_price: String = "",
        @Field("end_price") end_price: String = "",
        //  @Field("featured") featured: String = "0",
        /*    @Field("lang_id") id_lang: String = Constant.LANG,
            @Field("page") page: String = "1"*/
    ): Response<AdvanceFilterModel>

    /**AdvanceFilter */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getDrawerMenu(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "header_menu",
        @Field("lang_id") id_parent: String = Constant.LANG,

        ): Response<DrawerMenuModel>

    /**AdvanceFilter */
    @FormUrlEncoded
    @POST("index.php")
    suspend fun getDisplayTitleVideo(
        @Field("controller") controller: String = "mobileapi",
        @Field("op") op: String = "homepage_blocks"


    ): Response<DashVideoTitleModel>


}

