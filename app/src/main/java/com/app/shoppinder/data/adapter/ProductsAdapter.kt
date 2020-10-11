package com.app.shoppinder.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shoppinder.R
import com.app.shoppinder.data.model.responseModel.product.DataItem
import com.app.shoppinder.databinding.CardViewProductBinding

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private var productList: List<DataItem>? = null
     var mPosition : Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.card_view_product,
            parent,
            false
        )
    )

    override fun getItemCount() = productList?.size ?: 0

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        mPosition = position
        productList?.let {
            holder.binding.product = it[position]
            holder.binding.executePendingBindings()
        }
    }

    fun getItem():DataItem{
        return productList!![mPosition]
    }

    fun setProduct(dataItem: List<DataItem>) {
        this.productList = dataItem
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(val binding: CardViewProductBinding) :
        RecyclerView.ViewHolder(binding.root)

}