import com.thoughtworks.kotlin_basic.util.PrintUtil

fun main(args: Array<String>) {
    println("Hello World!")
    println("Program arguments: ${args.joinToString()}")

    val printUtil = PrintUtil()
    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.

    val headers = listOf("ID", "Name", "Occupation")
    val rows = listOf(
        listOf("1", "Alice", "Software Engineer"),
        listOf("2", "Bob", "Data Scientist"),
        listOf("3", "Charlie", "Product Manager")
    )

    printUtil.printTable(headers, rows)
}