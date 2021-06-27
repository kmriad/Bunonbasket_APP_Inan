package com.example.bunonbasket.data.models.cart

import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.data.models.product.ChoiceOption
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CartListModel(
    @SerializedName("choice")
    @Expose
    val choice: List<ChoiceOption>,

    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("price")
    @Expose
    val price: Int,

    @SerializedName("product")
    @Expose
    val product: Product,

    @SerializedName("product_id")
    @Expose
    val product_id: Int,

    @SerializedName("quantity")
    @Expose
    val quantity: Int,

    @SerializedName("shipping")
    @Expose
    val shipping: String,

    @SerializedName("shipping_type")
    @Expose
    val shipping_type: String,

    @SerializedName("tax")
    @Expose
    val tax: Int,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String,

    @SerializedName("user_id")
    @Expose
    val user_id: Int,

    var isSelected: Boolean = false,
)