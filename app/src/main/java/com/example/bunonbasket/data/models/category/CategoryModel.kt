package com.example.bunonbasket.data.models.category

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoryModel(

    @SerializedName("message")
    @Expose
    val message: String,

    @SerializedName("results")
    @Expose
    val categories: List<Category>,

    @SerializedName("success")
    @Expose
    val success: Boolean
)