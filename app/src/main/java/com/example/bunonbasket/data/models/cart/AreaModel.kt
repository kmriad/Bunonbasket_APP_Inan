package com.example.bunonbasket.data.models.cart

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AreaModel(

    @SerializedName("cost")
    @Expose
    val cost: Int,

    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("district_id")
    @Expose
    val district_id: Int,

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
    val updated_at: String
)