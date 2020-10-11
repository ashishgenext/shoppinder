package com.app.shoppinder.data.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shoppinder.R
import com.app.shoppinder.data.model.responseModel.category.DataItem
import com.app.shoppinder.databinding.CardViewCategoryBinding

class CategoryAdapter(val listener: OnItemClickListener) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var productList: List<DataItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.card_view_category,
            parent,
            false
        )
    )

    override fun getItemCount() = productList?.size ?: 0

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        productList?.let {
            holder.binding.category = it[position]
            holder.binding.executePendingBindings()
            if (it[position].selected) {
                changeBackground(holder)
            } else {
                defaultBackground(holder)
            }
            holder.itemView.setOnClickListener { view ->
                it.forEach {
                    it.selected = false
                }
                it[position].selected = !it[position].selected
                listener.onItemClick(it[position], position)
                notifyDataSetChanged()
            }
        }
    }

    private fun changeBackground(holder: CategoryViewHolder) {
        holder.binding.view.setBackgroundColor(Color.parseColor("#3700B3"))
        holder.binding.title.setTextColor(Color.parseColor("#ffffff"))
        holder.binding.desc.setTextColor(Color.parseColor("#ffffff"))
    }

    private fun defaultBackground(holder: CategoryViewHolder) {
        holder.binding.view.setBackgroundColor(Color.parseColor("#FFFFFF"))
        holder.binding.title.setTextColor(Color.parseColor("#000000"))
        holder.binding.desc.setTextColor(Color.parseColor("#000000"))
    }

    fun setProduct(dataItem: List<DataItem>) {
        this.productList = dataItem
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(val binding: CardViewCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)


    interface OnItemClickListener {
        fun onItemClick(dataItem: DataItem, position: Int)
    }
}