package vk.tech.products.view

import vk.tech.products.domain.model.Product

sealed class ProductsScreenState {
    data object Initial : ProductsScreenState()

    data object Loading : ProductsScreenState()

    data class Error(val message: String) : ProductsScreenState()

    data class Success(
        val products: List<Product>,
        val nextDataIsLoading: Boolean = false,
        val postsIsOver: Boolean = false
    ) : ProductsScreenState()

}