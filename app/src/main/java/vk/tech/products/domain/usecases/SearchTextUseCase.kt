package vk.tech.products.domain.usecases

import vk.tech.products.domain.repository.ProductsRepository

class SearchTextUseCase(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(searchText: String) = repository.searchText(searchText)
}