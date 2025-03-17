package com.example.project.ui.product

data class Product(
    var id: String = "",
    var name: String = "",
    var price: Double = 0.0,
    var images: List<String> = emptyList(),
    var sizes: List<String> = emptyList(),
    var description: String = "",
    var category: String = "",
    var subcategory: String = ""
)
