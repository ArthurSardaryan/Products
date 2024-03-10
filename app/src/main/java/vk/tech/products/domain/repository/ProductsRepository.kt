package vk.tech.products.domain.repository

import kotlinx.coroutines.flow.StateFlow
import vk.tech.products.domain.model.Product

interface ProductsRepository {
    val products: StateFlow<List<Product>>
    val categories: StateFlow<List<String>>
    suspend fun getProductById(id: Int): Product
    suspend fun loadNextData()
    suspend fun searchText(searchText: String)
}
