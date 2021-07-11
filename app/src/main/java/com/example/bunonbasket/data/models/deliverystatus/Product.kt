package com.example.bunonbasket.data.models.deliverystatus

import com.example.bunonbasket.utils.Constants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Product(

    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("added_by")
    @Expose
    val added_by: String,
    @SerializedName("user_id")
    @Expose
    val user_id: Int,
    @SerializedName("category_id")
    @Expose
    val category_id: Int,
    @SerializedName("subcategory_id")
    @Expose
    val subcategory_id: Int,
    @SerializedName("subsubcategory_id")
    @Expose
    val subsubcategory_id: String,
    @SerializedName("brand_id")
    @Expose
    val brand_id: String,
    @SerializedName("photos")
    @Expose
    val photos: List<String>,
    @SerializedName("thumbnail_img")
    @Expose
    val thumbnail_img: String,
    @SerializedName("featured_img")
    @Expose
    val featured_img: String,
    @SerializedName("flash_deal_img")
    @Expose
    val flash_deal_img: String,
    @SerializedName("video_provider")
    @Expose
    val video_provider: String,
    @SerializedName("video_link")
    @Expose
    val video_link: String,
    @SerializedName("tags")
    @Expose
    val tags: String,
    @SerializedName("description")
    @Expose
    val description: String,
    @SerializedName("unit_price")
    @Expose
    val unit_price: Int,
    @SerializedName("purchase_price")
    @Expose
    val purchase_price: Int,
    @SerializedName("choice_options")
    @Expose
    val choice_options: List<String>,
    @SerializedName("colors")
    @Expose
    val colors: List<String>,
    @SerializedName("variations")
    @Expose
    val variations: String,
    @SerializedName("todays_deal")
    @Expose
    val todays_deal: Int,
    @SerializedName("published")
    @Expose
    val published: Int,
    @SerializedName("featured")
    @Expose
    val featured: Int,
    @SerializedName("current_stock")
    @Expose
    val current_stock: Int,
    @SerializedName("unit")
    @Expose
    val unit: String,
    @SerializedName("discount")
    @Expose
    val discount: Int,
    @SerializedName("discount_type")
    @Expose
    val discount_type: Int,
    @SerializedName("tax")
    @Expose
    val tax: Int,
    @SerializedName("tax_type")
    @Expose
    val tax_type: Int,
    @SerializedName("shipping_type")
    @Expose
    val shipping_type: String,
    @SerializedName("shipping_cost")
    @Expose
    val shipping_cost: Int,
    @SerializedName("num_of_sale")
    @Expose
    val num_of_sale: Int,
    @SerializedName("meta_title")
    @Expose
    val meta_title: String,
    @SerializedName("meta_description")
    @Expose
    val meta_description: String,
    @SerializedName("meta_img")
    @Expose
    val meta_img: String,
    @SerializedName("pdf")
    @Expose
    val pdf: String,
    @SerializedName("slug")
    @Expose
    val slug: String,
    @SerializedName("rating")
    @Expose
    val rating: Int,
    @SerializedName("created_at")
    @Expose
    val created_at: String,
    @SerializedName("updated_at")
    @Expose
    val updated_at: String
) {
    var fullImageUrl: String? = ""
        get() = "${Constants.BASE_URL}/$thumbnail_img"
        set(value) {
            field = value
        }
}
