package com.thoughtworks.kotlin_basic.util

import com.thoughtworks.kotlin_basic.util.model.Inventory
import com.thoughtworks.kotlin_basic.util.model.Product
import com.thoughtworks.kotlin_basic.util.model.ProductsAndInventoriesAPI
import com.thoughtworks.kotlin_basic.util.viewmodel.ProductService
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class ProductServiceTest {

    private val pISvc: ProductsAndInventoriesAPI = mockk()
    private val productService = ProductService()

    private fun captureConsoleOutput(action: () -> Unit): String {
        val originalOut = System.out
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        action()

        System.out.flush()
        System.setOut(originalOut)
        return outputStream.toString()
    }

    @Test
    fun testFetchAndDisplayProducts() {
        @Test
        fun `should return normal price if demand of product is normal` () {
            val mockProductsFetched = listOf(
                Product("ABC123", "Electronic Watch", 100.0, "NORMAL", "image1.jpg")
            )

            val mockInventoriesFetched = listOf(
                Inventory("ABC123", 50, "Region1"),
                Inventory("ABC123", 60, "Region2"),
            )

            every { pISvc.getProducts().execute() } returns Response.success(mockProductsFetched)
            every { pISvc.getInventories().execute() } returns Response.success(mockInventoriesFetched)

            val output = captureConsoleOutput { productService.fetchAndDisplayProducts() }

            assertEquals(
            "SKU: ABC123, Name: Electronic Watch, Price: 100.0, Stock: 110, Image URL: image1.jpg",
                output )
        }
    }
}