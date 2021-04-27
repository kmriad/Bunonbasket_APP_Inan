package com.example.bunonbasket.data.models.category

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("added_by")
    @Expose
    val added_by: String,

    @SerializedName("brand_id")
    @Expose
    val brand_id: Any,

    @SerializedName("category_id")
    @Expose
    val category_id: Int,

    @SerializedName("choice_options")
    @Expose
    val choice_options: String,

    @SerializedName("colors")
    @Expose
    val colors: String,

    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("current_stock")
    @Expose
    val current_stock: Int,

    @SerializedName("description")
    @Expose
    val description: Any,

    @SerializedName("discount")
    @Expose
    val discount: Int,

    @SerializedName("discount_type")
    @Expose
    val discount_type: String,

    @SerializedName("featured")
    @Expose
    val featured: Int,

    @SerializedName("featured_img")
    @Expose
    val featured_img: String,

    @SerializedName("flash_deal_img")
    @Expose
    val flash_deal_img: String,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("meta_description")
    @Expose
    val meta_description: Any,

    @SerializedName("meta_img")
    @Expose
    val meta_img: String,

    @SerializedName("meta_title")
    @Expose
    val meta_title: String,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("num_of_sale")
    @Expose
    val num_of_sale: Int,

    @SerializedName("pdf")
    @Expose
    val pdf: Any,

    @SerializedName("photos")
    @Expose
    val photos: String,

    @SerializedName("published")
    @Expose
    val published: Int,

    @SerializedName("purchase_price")
    @Expose
    val purchase_price: Int,

    @SerializedName("rating")
    @Expose
    val rating: Int,

    @SerializedName("shipping_cost")
    @Expose
    val shipping_cost: Int,

    @SerializedName("shipping_type")
    @Expose
    val shipping_type: String,

    @SerializedName("slug")
    @Expose
    val slug: String,

    @SerializedName("subcategory_id")
    @Expose
    val subcategory_id: Int,

    @SerializedName("subsubcategory_id")
    @Expose
    val subsubcategory_id: Any,

    @SerializedName("tags")
    @Expose
    val tags: String,

    @SerializedName("tax")
    @Expose
    val tax: Int,

    @SerializedName("tax_type")
    @Expose
    val tax_type: String,

    @SerializedName("thumbnail_img")
    @Expose
    val thumbnail_img: String,

    @SerializedName("todays_deal")
    @Expose
    val todays_deal: Int,

    @SerializedName("unit")
    @Expose
    val unit: String,

    @SerializedName("unit_price")
    @Expose
    val unit_price: Int,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String,

    @SerializedName("user_id")
    @Expose
    val user_id: Int,

    @SerializedName("variations")
    @Expose
    val variations: String,

    @SerializedName("video_link")
    @Expose
    val video_link: Any,

    @SerializedName("video_provider")
    @Expose
    val video_provider: String
)