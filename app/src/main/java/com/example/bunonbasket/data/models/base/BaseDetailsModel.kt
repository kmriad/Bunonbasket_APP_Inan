package com.example.bunonbasket.data.models.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by inan on 24/5/21
 */
data class BaseDetailsModel<T>(
    @SerializedName("message")
    @Expose
    val message: String,

    @SerializedName("results")
    @Expose
    val results: T,

    @SerializedName("success")
    @Expose
    val success: Boolean
)
