package vk.tech.products.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import vk.tech.products.data.repository.ProductsRepositoryImpl
import vk.tech.products.domain.usecases.GetCategoriesUseCase
import vk.tech.products.domain.usecases.GetIsErrorUseCase
import vk.tech.products.domain.usecases.GetIsPostOverUseCase
import vk.tech.products.domain.usecases.GetProductsUseCase
import vk.tech.products.domain.usecases.LoadNextDataUseCase
import vk.tech.products.domain.usecases.SearchTextUseCase
import vk.tech.products.mergeWith

class ProductViewModel : ViewModel() {
    private val repository = ProductsRepositoryImpl()

    private val getCategoriesUseCase = GetCategoriesUseCase(repository)
    private val getProductsUseCase = GetProductsUseCase(repository)
    private val loadNextDataUseCase = LoadNextDataUseCase(repository)
    private val searchTextUseCase = SearchTextUseCase(repository)
    private val getIsErrorUseCase = GetIsErrorUseCase(repository)
    private val getIsPostOverUseCase = GetIsPostOverUseCase(repository)

    private val loadNextDataFlow = MutableSharedFlow<ProductsScreenState>()

    val categories = getCategoriesUseCase()

    private val repoProducts = getProductsUseCase()

    private val isError = getIsErrorUseCase()
        .filter { it.isNotEmpty() }
        .map {
            ProductsScreenState.Error(it)
        }
    private val isProductsOver = getIsPostOverUseCase()
        .filter { it }
        .map {
            ProductsScreenState.Success(
                products = repoProducts.value,
                nextDataIsLoading = false,
                postsIsOver = true
            )
        }


    val products = repoProducts
        .filter { it.isNotEmpty() }
        .map { ProductsScreenState.Success(it) as ProductsScreenState }
        .onStart { emit(ProductsScreenState.Loading) }
        .onEach {
            Log.d("TAG", "products: $it")
        }
        .mergeWith(loadNextDataFlow, isProductsOver, isError)


    fun nextDataNeeded() {
        viewModelScope.launch {
            loadNextDataFlow.emit(
                ProductsScreenState.Success(
                    products = repoProducts.value,
                    nextDataIsLoading = true,
                    postsIsOver = repository.postIsOver.value
                )
            )
            loadNextDataUseCase()
        }
    }

    fun searchText(searchText: String) {
        viewModelScope.launch {
            searchTextUseCase(searchText)
        }
    }
}
