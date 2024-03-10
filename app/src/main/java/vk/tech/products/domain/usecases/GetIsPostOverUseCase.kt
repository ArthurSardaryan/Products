package vk.tech.products.domain.usecases

import vk.tech.products.domain.repository.ProductsRepository

class GetIsPostOverUseCase(
    private val repository: ProductsRepository
) {
    operator fun invoke() = repository.postIsOver
}