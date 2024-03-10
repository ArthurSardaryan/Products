package vk.tech.products.view

sealed class Screen(
    val route: String
) {
    data object Products : Screen("products")
    data object ProductDetails : Screen("product_details") {
        const val argName = "productId"
        val routeWithArg = "$route/{$argName}"
    }
    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}