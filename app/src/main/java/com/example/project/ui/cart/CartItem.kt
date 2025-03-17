package com.example.project.ui.cart

data class CartItem(
    val id: Int,
    val name: String,
    val color: String,
    val size: String,
    val price: Double,
    var quantity: Int,
    val imageResId: Int
)
