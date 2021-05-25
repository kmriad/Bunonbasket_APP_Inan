package com.example.bunonbasket.ui.component.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.databinding.ProductImageLayoutBinding

/**
 * Created by inan on 25/5/21
 */
class ProductImageViewPagerAdapter :
    ListAdapter<String, ProductImageViewPagerAdapter.ProductImageViewHolder>(DiffCallback()) {

    inner class ProductImageViewHolder(private val binding: ProductImageLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: String) {
            binding.data = image
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImageViewHolder {
        val binding =
            ProductImageLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductImageViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)
    }
}