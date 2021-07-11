package com.example.bunonbasket.data.repository.remote

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
import com.example.bunonbasket.data.models.product.ProductDetails
import com.example.bunonbasket.data.models.wishlist.PostWishlistModel
import com.example.bunonbasket.data.models.wishlist.WishListModel
import com.example.bunonbasket.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by inan on 21/4/21
 */
interface RemoteRepositorySource {
    suspend fun fetchBanners(): Flow<Resource<BaseModel<Banner>>>
    suspend fun fetchCategories(): Flow<Resource<BaseModel<Category>>>
    suspend fun fetchBrands(): Flow<Resource<BaseModel<Brand>>>
    suspend fun fetchFeaturedProducts(): Flow<Resource<BaseModel<Product>>>
    suspend fun fetchBestSellingProducts(): Flow<Resource<BaseModel<Product>>>
    suspend fun fetchSubCategories(id: String): Flow<Resource<BaseModel<SubCategory>>>
    suspend fun fetchProductDetails(id: String): Flow<Resource<BaseDetailsModel<ProductDetails>>>
    suspend fun fetchProductBySubCategories(
        id: String,
        page: Int,
        perPage: Int
    ): Flow<Resource<BasePaginatedModel<PaginatedModel>>>

    suspend fun fetchAllProducts(
        query: String,
        page: Int,
        perPage: Int
    ): BasePaginatedModel<PaginatedModel>

    suspend fun loginUser(
        phone: String?,
        password: String?
    ): Flow<Resource<BaseDetailsModel<LoginModel>>>

    suspend fun registerUser(
        name: String,
        phone: String,
        password: String,
        userType: String
    ): Flow<Resource<BaseDetailsModel<LoginModel>>>

    suspend fun addToCart(
        productId: String,
        quantity: Int,
        token: String,
    ): Flow<Resource<BaseDetailsModel<CartModel>>>

    suspend fun fetchCart(
        token: String
    ): Flow<Resource<BaseModel<CartListModel>>>

    suspend fun updateQuantity(
        cartId: Int,
        quantity: Int,
        token: String
    ): Flow<Resource<BaseDetailsModel<QuantityUpdateModel>>>

    suspend fun fetchCities(token: String): Flow<Resource<BaseModel<CityModel>>>

    suspend fun fetchAreas(token: String, areaId: Int): Flow<Resource<BaseModel<AreaModel>>>

    suspend fun addShippingInfo(
        name: String,
        phone: String,
        address: String,
        country: String,
        city: String,
        area: String,
        authHeader: String
    ): Flow<Resource<BaseDetailsModel<ShippingInfo>>>

    suspend fun fetchShippingInfo(authHeader: String): Flow<Resource<BaseDetailsModel<ShippingInfo>>>

    suspend fun deleteItem(cartId: Int, authHeader: String): Flow<Resource<BaseDetailsModel<Any?>>>

    suspend fun doCheckout(authHeader: String): Flow<Resource<BaseDetailsModel<CheckoutModel>>>

    suspend fun fetchOrderHistory(authHeader: String): Flow<Resource<BaseModel<OrderHistoryModel>>>

    suspend fun fetchDeliveredOrders(authHeader: String): Flow<Resource<BaseModel<OrderHistoryModel>>>

    suspend fun fetchCancelledOrders(authHeader: String): Flow<Resource<BaseModel<OrderHistoryModel>>>

    suspend fun addToWishList(
        authHeader: String,
        productId: Int
    ): Flow<Resource<BaseDetailsModel<PostWishlistModel>>>

    suspend fun fetchWishList(
        authHeader: String,
    ): Flow<Resource<BaseModel<WishListModel>>>

    suspend fun fetchDeliveryStatus(
        cartId: Int,
        authHeader: String
    ): Flow<Resource<BaseDetailsModel<DeliveryStatusModel>>>
}