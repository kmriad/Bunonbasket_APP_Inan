package com.example.bunonbasket.data.models.category

import com.example.bunonbasket.utils.Constants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Category(
    @SerializedName("banner")
    @Expose
    val banner: String,

    @SerializedName("cat_short")
    @Expose
    val cat_short: Int,

    @SerializedName("commision_rate")
    @Expose
    val commision_rate: Int,

    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("featured")
    @Expose
    val featured: Int,

    @SerializedName("icon")
    @Expose
    val icon: String,

    @SerializedName("id")
    @Expose
    val id: Int,

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
    val products: List<Product>,

    @SerializedName("slug")
    @Expose
    val slug: String,

    @SerializedName("sub_categories")
    @Expose
    val sub_categories: List<SubCategory>,

    @SerializedName("top")
    @Expose
    val top: Int,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String
) : Serializable {
    var fullImageUrl: String? = ""
        get() = "${Constants.BASE_URL}/$icon"
        set(value) {
            field = value
        }
}