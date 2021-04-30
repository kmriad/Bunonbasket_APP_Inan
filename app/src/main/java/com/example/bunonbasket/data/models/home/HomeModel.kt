package com.example.bunonbasket.data.models.home

import com.example.bunonbasket.data.models.category.Product
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HomeModel(
    @SerializedName("message")
    @Expose
    val message: String,

    @SerializedName("results")
    @Expose
    val results: List<Product>,

    @SerializedName("success")
    @Expose
    val success: Boolean
)