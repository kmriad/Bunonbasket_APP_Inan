package com.example.bunonbasket.data.models.banner

data class Banner(
    val message: String,
    val results: List<Result>,
    val success: Boolean
)