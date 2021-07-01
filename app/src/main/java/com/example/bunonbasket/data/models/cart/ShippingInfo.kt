package com.example.bunonbasket.data.models.cart

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ShippingInfo(

    @SerializedName("address")
    @Expose
    val address: String?,

    @SerializedName("city")
    @Expose
    val city: String?,

    @SerializedName("country")
    @Expose
    val country: String?,

    @SerializedName("phone")
    @Expose
    val phone: String?,

    @SerializedName("postal_code")
    @Expose
    val postal_code: String?,

    @SerializedName("user_id")
    @Expose
    val user_id: Int?,

    @SerializedName("name")
    @Expose
    val name: String?
) : Serializable