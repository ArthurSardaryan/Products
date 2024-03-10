package vk.tech.products.data.dto

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products") val products: List<ProductDto>,
)
