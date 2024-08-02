package com.thoughtworks.kotlin_basic.util.model

import retrofit2.Call
import retrofit2.http.GET

interface ProductsAndInventoriesAPI {
    @GET("products")
    suspend fun getProducts(): Call<List<Product>>

    @GET("inventories")
    suspend fun getInventories(): Call<List<Inventory>>
}
