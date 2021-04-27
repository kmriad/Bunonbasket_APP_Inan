package com.example.bunonbasket.data.models.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Home(
    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("current_stock")
    @Expose
    val current_stock: Int,

    @SerializedName("discount")
    @Expose
    val discount: Int,

    @SerializedName("discount_type")
    @Expose
    val discount_type: String,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("num_of_sale")
    @Expose
    val num_of_sale: Int,

    @SerializedName("purchase_price")
    @Expose
    val purchase_price: Int,

    @SerializedName("rating")
    @Expose
    val rating: Int,

    @SerializedName("thumbnail_img")
    @Expose
    val thumbnail_img: String
)