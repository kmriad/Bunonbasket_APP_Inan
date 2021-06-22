package com.example.bunonbasket.data.remote

import com.example.bunonbasket.data.models.LoginModel
import com.example.bunonbasket.data.models.banner.Banner
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.base.BasePaginatedModel
import com.example.bunonbasket.data.models.brands.Brand
import com.example.bunonbasket.data.models.cart.CartListModel
import com.example.bunonbasket.data.models.cart.CartModel
import com.example.bunonbasket.data.models.category.Category
import com.example.bunonbasket.data.models.category.PaginatedModel
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.data.models.category.SubCategory
import com.example.bunonbasket.data.models.product.ProductDetails
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
}