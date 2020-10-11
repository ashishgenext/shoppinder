package com.app.shoppinder.data.network.api

import com.app.shoppinder.data.model.responseModel.category.CategoryResponse
import com.app.shoppinder.data.model.responseModel.product.ProductsResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ShoppingAPI {
    @GET("products")
    fun getProducts(): Call<ProductsResponse>

    @GET("categories")
    fun getCategories(): Call<CategoryResponse>

    companion object {
        operator fun invoke(): ShoppingAPI {
            return Retrofit.Builder()
                .baseUrl("https://gorest.co.in/public-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ShoppingAPI::class.java)
        }
    }

}