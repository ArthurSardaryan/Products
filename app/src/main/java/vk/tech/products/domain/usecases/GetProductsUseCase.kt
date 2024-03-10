package vk.tech.products.domain.usecases

import vk.tech.products.domain.repository.ProductsRepository

class GetProductsUseCase(private val repository: ProductsRepository) {
    operator fun invoke() = repository.products
}