package com.example.bunonbasket.data.models.brands

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BrandModel(
    @SerializedName("message")
    @Expose
    val message: String,

    @SerializedName("results")
    @Expose
    val brands: List<Brand>,

    @SerializedName("success")
    @Expose
    val success: Boolean
)