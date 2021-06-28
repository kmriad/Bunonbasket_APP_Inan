package com.example.bunonbasket.data.models.checkout

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CheckoutModel(

    @SerializedName("code")
    @Expose
    val code: String,

    @SerializedName("coupon_discount")
    @Expose
    val coupon_discount: Int,

    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("date")
    @Expose
    val date: String,

    @SerializedName("delivery_status")
    @Expose
    val delivery_status: String,

    @SerializedName("delivery_viewed")
    @Expose
    val delivery_viewed: Int,

    @SerializedName("grand_total")
    @Expose
    val grand_total: Int,

    @SerializedName("guest_id")
    @Expose
    val guest_id: Any,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("order_details")
    @Expose
    val order_details: List<OrderDetail>,

    @SerializedName("payment_details")
    @Expose
    val payment_details: Any,

    @SerializedName("payment_status")
    @Expose
    val payment_status: String,

    @SerializedName("payment_status_viewed")
    @Expose
    val payment_status_viewed: Int,

    @SerializedName("payment_type")
    @Expose
    val payment_type: String,

    @SerializedName("pickup_point_id")
    @Expose
    val pickup_point_id: Any,

    @SerializedName("shipping_address")
    @Expose
    val shipping_address: Any,

    @SerializedName("shipping_cost")
    @Expose
    val shipping_cost: Int,

    @SerializedName("shipping_type")
    @Expose
    val shipping_type: Any,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String,

    @SerializedName("user_id")
    @Expose
    val user_id: Int,

    @SerializedName("viewed")
    @Expose
    val viewed: Int
)