package com.thoughtworks.kotlin_basic.util.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductsAndInventoriesService {
    private val BASE_URL = "http://localhost:3000/"

    fun getProductsAndInventoriesAPI(): ProductsAndInventoriesAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductsAndInventoriesAPI::class.java)
    }
}
