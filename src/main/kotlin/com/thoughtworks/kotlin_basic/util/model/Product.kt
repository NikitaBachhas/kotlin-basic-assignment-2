package com.thoughtworks.kotlin_basic.util.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("SKU")
    val sku: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("type")
    val type: String,

    @SerializedName("imageUrl")
    val imageUrl: String
)