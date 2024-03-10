package vk.tech.products.domain.usecases

import vk.tech.products.domain.repository.ProductsRepository

class GetProductByIdUseCase(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(id: Int) = repository.getProductById(id)
}