package vk.tech.products.view

import vk.tech.products.domain.model.Product

sealed class ProductDetailsScreenState {
    data object Initial : ProductDetailsScreenState()

    data object Loading : ProductDetailsScreenState()

    data class Error(val message: String) : ProductDetailsScreenState()
    data class Success(val product: Product) : ProductDetailsScreenState()
}