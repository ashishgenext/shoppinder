package com.app.shoppinder.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shoppinder.R
import com.app.shoppinder.data.model.responseModel.product.DataItem
import com.app.shoppinder.databinding.CartListItemBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var productList: List<DataItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CartViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.cart_list_item,
            parent,
            false
        )
    )

    override fun getItemCount() = productList?.size ?: 0

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        productList?.let {
            holder.binding.cart = it[position]
            holder.binding.executePendingBindings()
        }
    }

    fun setProduct(dataItem: List<DataItem>) {
        this.productList = dataItem
        notifyDataSetChanged()
    }

    inner class CartViewHolder(val binding: CartListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
