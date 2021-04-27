package com.example.bunonbasket.data.models.brands

import com.example.bunonbasket.utils.Constants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Brand(

    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("logo")
    @Expose
    val logo: String,

    @SerializedName("meta_description")
    @Expose
    val meta_description: Any,

    @SerializedName("meta_title")
    @Expose
    val meta_title: String,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("products")
    @Expose
    val products: List<Any>,

    @SerializedName("slug")
    @Expose
    val slug: String,

    @SerializedName("top")
    @Expose
    val top: Int,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String
) {
    var fullImageUrl: String? = ""
        get() = "${Constants.BASE_URL}/$logo"
        set(value) {
            field = value
        }
}