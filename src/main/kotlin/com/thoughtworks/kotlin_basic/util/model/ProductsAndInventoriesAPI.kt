package com.thoughtworks.kotlin_basic.util.model

import retrofit2.Call
import retrofit2.http.GET

interface ProductsAndInventoriesAPI {
    @GET("products")
    fun getProducts(): Call<List<Product>>

    @GET("inventories")
    fun getInventories(): Call<List<Inventory>>
}
