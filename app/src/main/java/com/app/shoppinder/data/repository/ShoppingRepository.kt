package com.app.shoppinder.data.repository

import androidx.lifecycle.MutableLiveData
import com.app.shoppinder.data.model.responseModel.category.CategoryResponse
import com.app.shoppinder.data.model.responseModel.product.ProductsResponse
import com.app.shoppinder.data.network.api.ShoppingAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ShoppingRepository {

    fun getProducts(): MutableLiveData<ProductsResponse?> {
        val mutableLiveData: MutableLiveData<ProductsResponse?> = MutableLiveData()
        ShoppingAPI().getProducts().enqueue(object : Callback<ProductsResponse?> {
            override fun onFailure(call: Call<ProductsResponse?>, t: Throwable) {
                mutableLiveData.value = null
            }

            override fun onResponse(
                call: Call<ProductsResponse?>,
                response: Response<ProductsResponse?>
            ) {
                if (response.isSuccessful) {
                    mutableLiveData.value = response.body()
                }
            }
        })

        return mutableLiveData
    }

    fun getCategory(): MutableLiveData<CategoryResponse?> {
        val mutableLiveData: MutableLiveData<CategoryResponse?> = MutableLiveData()

        ShoppingAPI().getCategories().enqueue(object : Callback<CategoryResponse?> {
            override fun onFailure(call: Call<CategoryResponse?>, t: Throwable) {
                mutableLiveData.value = null
            }

            override fun onResponse(
                call: Call<CategoryResponse?>,
                response: Response<CategoryResponse?>
            ) {
                if (response.isSuccessful) {
                    mutableLiveData.value = response.body()
                }
            }
        })
        return mutableLiveData
    }
}