package com.thoughtworks.kotlin_basic.util.model

import com.google.gson.annotations.SerializedName

data class Inventory(
    @SerializedName("SKU")
    val sku: String,

    @SerializedName("stock")
    val stock: Int,

    @SerializedName("region")
    val region: String
)