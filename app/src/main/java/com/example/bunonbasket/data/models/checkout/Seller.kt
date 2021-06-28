package com.example.bunonbasket.data.models.checkout

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Seller(

    @SerializedName("account_holder_name")
    @Expose
    val account_holder_name: Any,

    @SerializedName("account_number")
    @Expose
    val account_number: Any,

    @SerializedName("admin_to_pay")
    @Expose
    val admin_to_pay: Int,

    @SerializedName("bank_brance")
    @Expose
    val bank_brance: Any,

    @SerializedName("bank_name")
    @Expose
    val bank_name: Any,

    @SerializedName("cash_on_delivery_status")
    @Expose
    val cash_on_delivery_status: Int,

    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("instamojo_api_key")
    @Expose
    val instamojo_api_key: Any,

    @SerializedName("instamojo_status")
    @Expose
    val instamojo_status: Int,

    @SerializedName("instamojo_token")
    @Expose
    val instamojo_token: Any,

    @SerializedName("nid")
    @Expose
    val nid: Any,

    @SerializedName("paypal_client_id")
    @Expose
    val paypal_client_id: Any,

    @SerializedName("paypal_client_secret")
    @Expose
    val paypal_client_secret: Any,

    @SerializedName("paypal_status")
    @Expose
    val paypal_status: Int,

    @SerializedName("paystack_public_key")
    @Expose
    val paystack_public_key: Any,

    @SerializedName("paystack_secret_key")
    @Expose
    val paystack_secret_key: Any,

    @SerializedName("paystack_status")
    @Expose
    val paystack_status: Int,

    @SerializedName("razorpay_api_key")
    @Expose
    val razorpay_api_key: Any,

    @SerializedName("razorpay_secret")
    @Expose
    val razorpay_secret: Any,

    @SerializedName("razorpay_status")
    @Expose
    val razorpay_status: Int,

    @SerializedName("ssl_password")
    @Expose
    val ssl_password: Any,

    @SerializedName("ssl_store_id")
    @Expose
    val ssl_store_id: Any,

    @SerializedName("sslcommerz_status")
    @Expose
    val sslcommerz_status: Int,

    @SerializedName("stripe_key")
    @Expose
    val stripe_key: Any,

    @SerializedName("stripe_secret")
    @Expose
    val stripe_secret: Any,

    @SerializedName("stripe_status")
    @Expose
    val stripe_status: Int,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String,

    @SerializedName("user_id")
    @Expose
    val user_id: Int,

    @SerializedName("verification_info")
    @Expose
    val verification_info: Any,

    @SerializedName("verification_status")
    @Expose
    val verification_status: Int,

    @SerializedName("voguepay_merchand_id")
    @Expose
    val voguepay_merchand_id: Any,

    @SerializedName("voguepay_status")
    @Expose
    val voguepay_status: Int
)