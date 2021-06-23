package com.example.bunonbasket.data.models.cart

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QuantityUpdateModel(

    @SerializedName("choice")
    @Expose
    val choice: Any,

    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("price")
    @Expose
    val price: Int,

    @SerializedName("product_id")
    @Expose
    val product_id: Int,

    @SerializedName("quantity")
    @Expose
    val quantity: String,

    @SerializedName("shipping")
    @Expose
    val shipping: String,

    @SerializedName("shipping_type")
    @Expose
    val shipping_type: String,

    @SerializedName("tax")
    @Expose
    val tax: Int,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String,

    @SerializedName("user_id")
    @Expose
    val user_id: Int
)