package com.app.shoppinder.utils.viewmodelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.shoppinder.ui.main.MainViewModel

class MyViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel
                else -> throw IllegalArgumentException("Unknown viewModel class $modelClass")
            }
        } as T


    companion object {
        private var instance: MyViewModelFactory? = null
        fun getInstance() =
            instance ?: synchronized(MyViewModelFactory::class.java) {
                instance ?: MyViewModelFactory().also { instance = it }
            }
    }
}