package com.example.bunonbasket.data.models.cart

data class CartModel(
    val choice: List<Any>,
    val created_at: String,
    val id: Int,
    val price: Int,
    val product_id: Int,
    val quantity: String,
    val shipping: Int,
    val shipping_type: String,
    val tax: Int,
    val updated_at: String,
    val user_id: Int
)