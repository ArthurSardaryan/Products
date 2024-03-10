package vk.tech.products.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object ProductsRemote {
    private const val BASE_URL = "https://dummyjson.com/"

    private val client = OkHttpClient.Builder()
        .build()

    private val gsonConverterFactory = GsonConverterFactory.create()
    private val retrofit = Retrofit
        .Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .build()
    val service: ProductsRemoteService = retrofit.create()
}