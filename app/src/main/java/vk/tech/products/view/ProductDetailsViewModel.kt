package vk.tech.products.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import vk.tech.products.data.repository.ProductsRepositoryImpl
import vk.tech.products.domain.usecases.GetProductByIdUseCase

class ProductDetailsViewModel(id: Int) : ViewModel() {
    private val repository = ProductsRepositoryImpl()
    private val getProductByIdUseCase = GetProductByIdUseCase(repository)
    private val _productState =
        MutableStateFlow<ProductDetailsScreenState>(ProductDetailsScreenState.Loading)
    val productState = _productState
        .asStateFlow()
        .onStart { emit(ProductDetailsScreenState.Loading)}

    init {
        viewModelScope.launch {
            _productState.value = ProductDetailsScreenState.Success(getProductByIdUseCase(id))
        }
    }
}
