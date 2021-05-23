package com.example.bunonbasket.ui.component.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.databinding.HomeProductItemLayoutBinding

/**
 * Created by inan on 1/5/21
 */
class ProductAdapter(
    private val listener: OnItemClickListener,
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(DiffCallback()) {

    inner class ProductViewHolder(private val binding: HomeProductItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if(position!=RecyclerView.NO_POSITION){
                        val product = getItem(position)
                        listener.onItemClick(product = product)
                    }

                }
            }
        }
        fun bind(product: Product?) {
            binding.data = product
            if (product!!.discount > 0) {
                binding.discountText.visibility = View.VISIBLE
            } else {
                binding.discountText.visibility = View.INVISIBLE
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(product: Product?)
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