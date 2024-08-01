package com.thoughtworks.kotlin_basic.util.model

data class Product(
    val sku: String,
    val name: String,
    val price: Double,
    val type: String,
    val imageUrl: String
)