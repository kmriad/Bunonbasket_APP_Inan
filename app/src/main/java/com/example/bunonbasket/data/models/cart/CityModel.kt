package com.example.bunonbasket.data.models.cart

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CityModel(
    @SerializedName("bn_name")
    @Expose
    val bn_name: String,

    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("status")
    @Expose
    val status: Int,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String,

    @SerializedName("url")
    @Expose
    val url: String
)