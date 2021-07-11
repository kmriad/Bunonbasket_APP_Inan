package com.example.bunonbasket.data.models.deliverystatus

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Detial(

    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("delivery_status")
    @Expose
    val delivery_status: String,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("order_id")
    @Expose
    val order_id: Int,

    @SerializedName("payment_status")
    @Expose
    val payment_status: String,

    @SerializedName("pickup_point_id")
    @Expose
    val pickup_point_id: Any,

    @SerializedName("price")
    @Expose
    val price: Int,

    @SerializedName("product")
    @Expose
    val product: Product,

    @SerializedName("product_id")
    @Expose
    val product_id: Int,

    @SerializedName("quantity")
    @Expose
    val quantity: Int,

    @SerializedName("seller_id")
    @Expose
    val seller_id: Int,

    @SerializedName("shipping_cost")
    @Expose
    val shipping_cost: Int,

    @SerializedName("shipping_type")
    @Expose
    val shipping_type: String,

    @SerializedName("tax")
    @Expose
    val tax: Int,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String,

    @SerializedName("variation")
    @Expose
    val variation: Any
)