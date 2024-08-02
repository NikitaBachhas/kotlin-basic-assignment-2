import com.thoughtworks.kotlin_basic.util.viewmodel.ProductService

suspend fun main() {
    val productService = ProductService()

    productService.fetchAndDisplayProducts()
}