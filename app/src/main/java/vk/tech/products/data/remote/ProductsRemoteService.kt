package vk.tech.products.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vk.tech.products.data.dto.ProductDto
import vk.tech.products.data.dto.ProductResponse

interface ProductsRemoteService {
    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductResponse

    @GET("products/categories")
    suspend fun getCategories(): List<String>


    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDto
    @GET("products/search")
    suspend fun searchText(@Query("q") searchText: String): ProductResponse
}
