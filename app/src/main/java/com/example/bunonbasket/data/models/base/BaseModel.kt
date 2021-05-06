package com.example.bunonbasket.data.models.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseModel<T>(

    @SerializedName("message")
    @Expose
    val message: String,

    @SerializedName("results")
    @Expose
    val results: List<T>,

    @SerializedName("success")
    @Expose
    val success: Boolean
)
