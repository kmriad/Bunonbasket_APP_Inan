package com.example.bunonbasket.data.remote

import com.example.bunonbasket.data.models.LoginModel
import com.example.bunonbasket.data.models.banner.Banner
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.base.BasePaginatedModel
import com.example.bunonbasket.data.models.brands.Brand
import com.example.bunonbasket.data.models.cart.*
import com.example.bunonbasket.data.models.category.Category
import com.example.bunonbasket.data.models.category.PaginatedModel
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.data.models.category.SubCategory
import com.example.bunonbasket.data.models.checkout.CheckoutModel
import com.example.bunonbasket.data.models.deliverystatus.DeliveryStatusModel
import com.example.bunonbasket.data.models.orders.OrderHistoryModel
import com.example.bunonbasket.data.models.partners.PartnerModel
import com.example.bunonbasket.data.models.product.ProductDetails
import com.example.bunonbasket.data.models.wishlist.PostWishlistModel
import com.example.bunonbasket.data.models.wishlist.WishListModel
import retrofit2.http.*

/**
 * Created by inan on 21/4/21
 */
interface BunonRetrofit {

    @GET("banner")
    suspend fun fetchBanners(): BaseModel<Banner>

    @GET("categories")
    suspend fun fetchCategories(): BaseModel<Category>

    @GET("brands")
    suspend fun fetchBrands(): BaseModel<Brand>

    @GET("feature_products")
    suspend fun fetchFeaturedProducts(): BaseModel<Product>

    @GET("best_selling_products")
    suspend fun fetchBestSellingProducts(): BaseModel<Product>

    @GET("sub_categories/{category_id}")
    suspend fun fetchSubCategories(@Path(value = "category_id") categoryId: String): BaseModel<SubCategory>

    @GET("sub_category/products/{sub_category_id}")
    suspend fun fetchProductBySubCategories(
        @Path(value = "sub_category_id") subCategoryId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): BasePaginatedModel<PaginatedModel>

    @GET("sub_category/products/{sub_category_id}")
    suspend fun fetchAllProducts(
        @Path(value = "sub_category_id") subCategoryId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): BasePaginatedModel<PaginatedModel>

    @GET("product/{product_id}")
    suspend fun fetchProductDetails(
        @Path(value = "product_id") productId: String,
    ): BaseDetailsModel<ProductDetails>

    @POST("login")
    @FormUrlEncoded
    suspend fun loginUser(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): BaseDetailsModel<LoginModel>

    @POST("register")
    @FormUrlEncoded
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("user_type") userType: String
    ): BaseDetailsModel<LoginModel>


    @POST("cart")
    @FormUrlEncoded
    suspend fun addToCart(
        @Field("product_id") productId: String,
        @Field("quantity") quantity: Int,
        @Header("Authorization") authHeader: String
    ): BaseDetailsModel<CartModel>


    @GET("cart")
    suspend fun fetchCarts(@Header("Authorization") authHeader: String): BaseModel<CartListModel>


    @POST("cart/quantity-update/{cart_id}")
    @FormUrlEncoded
    suspend fun updateQuantity(
        @Path(value = "cart_id") cartId: Int,
        @Field("quantity") quantity: Int,
        @Header("Authorization") authHeader: String
    ): BaseDetailsModel<QuantityUpdateModel>

    @GET("all-city")
    suspend fun fetchCities(
        @Header("Authorization") authHeader: String
    ): BaseModel<CityModel>

    @GET("all-area-by-city/{area_id}")
    suspend fun fetchAreas(
        @Path(value = "area_id") areaId: Int,
        @Header("Authorization") authHeader: String
    ): BaseModel<AreaModel>

    @POST("shipping_info")
    @FormUrlEncoded
    suspend fun addShippingInfo(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("address") address: String,
        @Field("country") country: String,
        @Field("city") city: String,
        @Field("area") area: String,
        @Header("Authorization") authHeader: String
    ): BaseDetailsModel<ShippingInfo>


    @GET("get/shipping_info")
    suspend fun fetchShippingInfo(@Header("Authorization") authHeader: String): BaseDetailsModel<ShippingInfo>

    @HTTP(method = "DELETE", path = "cart", hasBody = true)
    @FormUrlEncoded
    suspend fun deleteItem(
        @Field("cart_id") cartId: Int,
        @Header("Authorization") authHeader: String,
    ): BaseDetailsModel<Any?>

    @POST("checkout")
    suspend fun doCheckout(@Header("Authorization") authHeader: String): BaseDetailsModel<CheckoutModel>

    @GET("user-orders/all")
    suspend fun fetchAllOrders(@Header("Authorization") authHeader: String): BaseModel<OrderHistoryModel>

    @GET("user-orders/delivered")
    suspend fun fetchDeliveredOrders(@Header("Authorization") authHeader: String): BaseModel<OrderHistoryModel>

    @GET("user-orders/canceled")
    suspend fun fetchCancelledOrders(@Header("Authorization") authHeader: String): BaseModel<OrderHistoryModel>

    @POST("wishlist")
    @FormUrlEncoded
    suspend fun addToWishlist(
        @Header("Authorization") authHeader: String,
        @Field("product_id") productId: Int
    ): BaseDetailsModel<PostWishlistModel>

    @GET("wishlist")
    suspend fun fetchWishList(
        @Header("Authorization") authHeader: String,
    ): BaseModel<WishListModel>

    @GET("current-order/{cart_id}")
    suspend fun fetchDeliveryStatus(
        @Path(value = "cart_id") cartId: Int,
        @Header("Authorization") authHeader: String
    ): BaseDetailsModel<DeliveryStatusModel>


    @GET("partners")
    suspend fun fetchPartners(): BaseModel<PartnerModel>
}