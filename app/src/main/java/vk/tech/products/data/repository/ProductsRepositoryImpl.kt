package vk.tech.products.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import vk.tech.products.data.dto.ProductDto
import vk.tech.products.data.dto.ProductResponse
import vk.tech.products.data.remote.ProductsRemote
import vk.tech.products.domain.model.Product
import vk.tech.products.domain.repository.ProductsRepository
import vk.tech.products.mergeWith

class ProductsRepositoryImpl : ProductsRepository {
    private val remoteService = ProductsRemote.service

    private val scope = CoroutineScope(Dispatchers.Default)

    private var page = 0

    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)

    private val _productsList = mutableListOf<Product>()
    private val productsList get() = _productsList.toList()

    private val _postIsOver = MutableStateFlow(false)
    override val postIsOver = _postIsOver.asStateFlow()

    private var isSearching = false

    private val _isError = MutableStateFlow("")
    override val isError = _isError.asStateFlow()

    private val _searchProducts = MutableStateFlow<List<Product>>(emptyList())

    private val loadedProducts = flow {
        loadNextData()
        nextDataNeededEvents.collect {
            if (isSearching) {
                isSearching = false
                return@collect
            }
            val response = remoteService.getProducts(20, page * 20)
            if (response.products.isEmpty()) _postIsOver.emit(true)
            page++
            _productsList.addAll(response.mapResponseToProducts())
            emit(productsList)
            _isError.emit("")
        }
    }
        .mergeWith(_searchProducts)
        .retry {
            _isError.emit(it.message.toString())
            delay(3000L)
            true
        }

    override suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    override suspend fun searchText(searchText: String) {
        try {
            val products = remoteService.searchText(searchText).mapResponseToProducts()
            _searchProducts.emit(products)
            if (searchText.isEmpty()) return
            isSearching = true
        }
        catch (e: Exception) {
            _isError.emit(e.message.toString())
        }
    }

    override val products = loadedProducts
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = productsList
        )

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    override val categories: StateFlow<List<String>> = _categories.asStateFlow()

    override suspend fun getProductById(id: Int): Product {
        return remoteService.getProductById(id).mapProductDtoToProducts()
    }

    init {
        scope.launch {
            try {
                _categories.emit(remoteService.getCategories())
            }
            catch (e: Exception) {
                _isError.emit(e.message.toString())
            }
        }
    }
}

private fun ProductResponse.mapResponseToProducts(): List<Product> {
    return products.map { it.mapProductDtoToProducts() }
}

private fun ProductDto.mapProductDtoToProducts(): Product {
    return Product(
        id,
        title,
        price,
        description,
        category,
        thumbnail,
        images,
        discountPercentage,
        rating,
        stock,
        brand
    )
}
