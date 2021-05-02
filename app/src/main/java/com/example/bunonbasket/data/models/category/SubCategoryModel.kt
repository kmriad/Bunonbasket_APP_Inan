package com.example.bunonbasket.data.models.category

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by inan on 2/5/21
 */
data class SubCategoryModel(

    @SerializedName("message")
    @Expose
    val message: String,

    @SerializedName("results")
    @Expose
    val categories: List<SubCategory>,

    @SerializedName("success")
    @Expose
    val success: Boolean
): Serializable
