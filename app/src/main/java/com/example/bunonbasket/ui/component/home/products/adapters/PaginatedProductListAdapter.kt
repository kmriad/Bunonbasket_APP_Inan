package com.example.bunonbasket.ui.component.home.products.adapters

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.databinding.HomeProductItemLayoutBinding

/**
 * Created by inan on 18/5/21
 */
class PaginatedProductListAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Product, PaginatedProductListAdapter.ProductViewHolder>(DiffCallback()) {

    inner class ProductViewHolder(private val binding: HomeProductItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product?) {
            binding.data = product
            if (product?.purchase_price!! > 0) {
                binding.productPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                binding.productPrice.setTextColor(Color.parseColor("#C4C4C4"))
                binding.purchasePrice.visibility = View.VISIBLE
            } else {
                binding.purchasePrice.visibility = View.GONE
            }
            if (product.discount > 0) {
                binding.discountText.visibility = View.VISIBLE
                binding.discountText.text = "${product.discount} Off"
            } else {
                binding.discountText.visibility = View.INVISIBLE
            }
            binding.root.setOnClickListener {
                listener.onItemClick(product)
            }
        }

    }


    interface OnItemClickListener {
        fun onItemClick(product: Product)
    }

    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            HomeProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}