package com.example.bunonbasket.ui.component.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.databinding.SubCategoryProductItemLayoutBinding

/**
 * Created by inan on 9/5/21
 */
class SubCategoryProductAdapter :
    ListAdapter<Product, SubCategoryProductAdapter.SubCategoryProductViewHolder>(DiffCallback()) {

    inner class SubCategoryProductViewHolder(private val binding: SubCategoryProductItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product?) {
            binding.data = product
            binding.executePendingBindings()
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubCategoryProductViewHolder {
        val binding =
            SubCategoryProductItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return SubCategoryProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubCategoryProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}