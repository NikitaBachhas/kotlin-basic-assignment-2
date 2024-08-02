package com.thoughtworks.kotlin_basic.util.viewmodel

import com.thoughtworks.kotlin_basic.util.model.Inventory
import com.thoughtworks.kotlin_basic.util.model.Product
import com.thoughtworks.kotlin_basic.util.model.ProductsAndInventoriesService
import kotlinx.coroutines.*
import retrofit2.*

class ProductService {
    fun fetchAndDisplayProducts() = runBlocking{

        val apiService = ProductsAndInventoriesService.getProductsAndInventoriesService()

        val exceptionHandler = CoroutineExceptionHandler { CoroutineContext, throwable -> "Exception: ${throwable.localizedMessage}"}

        val job = CoroutineScope(Dispatchers.Default + exceptionHandler).launch {

            val productsCall = apiService.getProducts()
            val inventoriesCall = apiService.getInventories()

            withContext(Dispatchers.IO){

                productsCall.enqueue(object : Callback<List<Product>> {

                    override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {

                        if (response.isSuccessful) {
                            val products = response.body() ?: return
                            inventoriesCall.enqueue(object : Callback<List<Inventory>> {

                                override fun onResponse(call: Call<List<Inventory>>, response: Response<List<Inventory>>) {

                                    if (response.isSuccessful) {
                                        val inventories = response.body() ?: return
                                        val productMap = products.associateBy { it.sku }
                                        val stockMap = inventories.groupBy { it.sku }
                                            .mapValues { (_, inventories) -> inventories.sumOf { it.quantity } }

                                        productMap.forEach { (sku, product) ->
                                            val totalStock = stockMap[sku] ?: 0
                                            val finalPrice = when {
                                                totalStock > 100 -> product.price
                                                totalStock in 31..100 -> product.price * 1.2
                                                else -> product.price * 1.5
                                            }
                                            println("SKU: $sku, Name: ${product.name}, Price: $finalPrice, Stock: $totalStock, Image URL: ${product.image}")
                                        }
                                    }

                                    else {
                                        throw Exception("Error: ${response.message()}")
                                    }
                                }

                                override fun onFailure(call: Call<List<Inventory>>, t: Throwable) {
                                    t.printStackTrace()
                                }
                            })
                        }

                        else {
                            throw Exception("Error: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            }
        }
        job.join()
    }
}