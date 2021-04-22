package com.example.bunonbasket.data.models.banner

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BannerModel(
    @SerializedName("success")
    @Expose
    val success: Boolean,

    @SerializedName("results")
    @Expose
    val banners: List<Banner>,

    @SerializedName("message")
    @Expose
    val message: String
)