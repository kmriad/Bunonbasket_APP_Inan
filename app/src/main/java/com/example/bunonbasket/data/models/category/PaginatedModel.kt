package com.example.bunonbasket.data.models.category

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PaginatedModel(

    @SerializedName("current_page")
    @Expose
    val current_page: Int,

    @SerializedName("data")
    @Expose
    val data: List<Product>,

    @SerializedName("first_page_url")
    @Expose
    val first_page_url: String,

    @SerializedName("from")
    @Expose
    val from: Int,

    @SerializedName("last_page")
    @Expose
    val last_page: Int,

    @SerializedName("last_page_url")
    @Expose
    val last_page_url: String,

    @SerializedName("next_page_url")
    @Expose
    val next_page_url: Any,

    @SerializedName("path")
    @Expose
    val path: String,

    @SerializedName("per_page")
    @Expose
    val per_page: String,

    @SerializedName("prev_page_url")
    @Expose
    val prev_page_url: Any,

    @SerializedName("to")
    @Expose
    val to: Int,

    @SerializedName("total")
    @Expose
    val total: Int
)