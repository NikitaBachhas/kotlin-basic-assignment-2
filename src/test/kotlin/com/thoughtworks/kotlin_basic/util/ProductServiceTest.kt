package com.thoughtworks.kotlin_basic.util

import com.thoughtworks.kotlin_basic.util.model.Inventory
import com.thoughtworks.kotlin_basic.util.model.Product
import com.thoughtworks.kotlin_basic.util.model.ProductsAndInventoriesAPI
import com.thoughtworks.kotlin_basic.util.viewmodel.ProductService
import io.mockk.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class ProductServiceTest {

    private val pISvc: ProductsAndInventoriesAPI = mockk()
    private val productService = ProductService()

    private suspend fun captureConsoleOutput(action: () -> Unit): String {
        delay(5000)
        val originalOut = System.out
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        action()

        System.out.flush()
        System.setOut(originalOut)
        return outputStream.toString()
    }

    @Test
    fun `fetchAndDisplayProducts - should return normal price if demand of product is normal`() = runTest {
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
            output
        )
    }

    @Test
    fun `fetchAndDisplayProducts - should return normal price if demand of product is high demand and stock is more than 100`() = runTest  {
        val mockProductsFetched = listOf(
            Product("DEF456", "Sports Shoes", 120.0, "HIGH_DEMAND", "image2.jpg")
        )

        val mockInventoriesFetched = listOf(
            Inventory("DEF456", 60, "Region1"),
            Inventory("DEF456", 60, "Region2"),
        )

        every { pISvc.getProducts().execute() } returns Response.success(mockProductsFetched)
        every { pISvc.getInventories().execute() } returns Response.success(mockInventoriesFetched)

        val output = captureConsoleOutput { productService.fetchAndDisplayProducts() }

        assertEquals(
            "SKU: DEF456, Name: Sports Shoes, Price: 120.0, Stock: 120, Image URL: image2.jpg",
            output
        )
    }

    @Test
    fun `fetchAndDisplayProducts - should return 120 percent price if demand of product is high demand and stock is between 30 and 100`() = runTest {
        val mockProductsFetched = listOf(
            Product("DEF456", "Sports Shoes", 120.0, "HIGH_DEMAND", "image2.jpg")
        )

        val mockInventoriesFetched = listOf(
            Inventory("DEF456", 30, "Region1"),
            Inventory("DEF456", 30, "Region2"),
        )

        every { pISvc.getProducts().execute() } returns Response.success(mockProductsFetched)
        every { pISvc.getInventories().execute() } returns Response.success(mockInventoriesFetched)

        val output = captureConsoleOutput { productService.fetchAndDisplayProducts() }

        assertEquals(
            "SKU: DEF456, Name: Sports Shoes, Price: 144.0, Stock: 60, Image URL: image2.jpg",
            output
        )
    }

    @Test
    fun `fetchAndDisplayProducts - should return 150 percent price if demand of product is high demand and stock is less than 30`() = runTest {
        val mockProductsFetched = listOf(
            Product("DEF456", "Sports Shoes", 120.0, "HIGH_DEMAND", "image2.jpg")
        )

        val mockInventoriesFetched = listOf(
            Inventory("DEF456", 10, "Region1"),
            Inventory("DEF456", 10, "Region2"),
        )

        every { pISvc.getProducts().execute() } returns Response.success(mockProductsFetched)
        every { pISvc.getInventories().execute() } returns Response.success(mockInventoriesFetched)

        val output = captureConsoleOutput { productService.fetchAndDisplayProducts() }

        assertEquals(
            "SKU: DEF456, Name: Sports Shoes, Price: 180.0, Stock: 20, Image URL: image2.jpg",
            output
        )
    }
}