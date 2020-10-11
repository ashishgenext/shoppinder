package com.app.shoppinder.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shoppinder.data.model.responseModel.category.CategoryResponse
import com.app.shoppinder.data.model.responseModel.product.DataItem
import com.app.shoppinder.data.model.responseModel.product.ProductsResponse
import com.app.shoppinder.data.repository.ShoppingRepository

object MainViewModel : ViewModel() {
    private val shoppingRepository = ShoppingRepository
    val cartList: MutableList<DataItem> = mutableListOf()
    fun getProducts(): LiveData<ProductsResponse?> {
        return shoppingRepository.getProducts()
    }

    fun getCategory(): LiveData<CategoryResponse?> {
        return shoppingRepository.getCategory()
    }

    fun getCart(): LiveData<List<DataItem>> {
        val liveData: MutableLiveData<List<DataItem>> = MutableLiveData()
        liveData.value = cartList
        return liveData
    }

    fun addToCart(item: DataItem) {
        var deleteItem: DataItem? = null
        cartList.forEach {
            if (it.id == item.id) deleteItem = it
        }

        if (deleteItem != null) {
            cartList.remove(deleteItem)
        }
        cartList.add(item)
    }

    fun removeFromCart(item: DataItem) {
        cartList.remove(item)
    }

    fun removeFromCart(position: Int) {
        cartList.removeAt(position)
    }

}