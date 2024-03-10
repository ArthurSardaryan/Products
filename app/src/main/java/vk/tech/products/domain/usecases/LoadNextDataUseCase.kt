package vk.tech.products.domain.usecases

import vk.tech.products.domain.repository.ProductsRepository

class LoadNextDataUseCase(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke() = repository.loadNextData()
}