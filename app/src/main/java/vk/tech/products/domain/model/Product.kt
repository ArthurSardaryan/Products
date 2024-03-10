package vk.tech.products.domain.model

data class Product(
    val id: Int,
    val title: String,
    val price: Int,
    val description: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String
)
