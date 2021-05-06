package com.example.bunonbasket.data.models.category

import com.example.bunonbasket.utils.Constants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SubCategory(
    @SerializedName("category_id")
    @Expose
    val category_id: Int,

    @SerializedName("created_at")
    @Expose
    val created_at: String,

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

    @SerializedName("updated_at")
    @Expose
    val updated_at: String,

    var isSelected: Boolean = false,
) : Serializable {
    var fullImageUrl: String? = ""
        get() = "${Constants.BASE_URL}/$icon"
        set(value) {
            field = value
        }
}