package com.example.bunonbasket.data.models.wishlist

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostWishlistModel(

    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("product_id")
    @Expose
    val product_id: String,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String,

    @SerializedName("user_id")
    @Expose
    val user_id: Int
)