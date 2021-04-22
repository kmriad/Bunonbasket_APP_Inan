package com.example.bunonbasket.data.models.banner

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("photo")
    @Expose
    val photo: String,

    @SerializedName("published")
    @Expose
    val published: Int,

    @SerializedName("link")
    @Expose
    val link: String,

    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String
)