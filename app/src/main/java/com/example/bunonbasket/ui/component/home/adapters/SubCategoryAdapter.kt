package com.example.bunonbasket.ui.component.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.data.models.category.SubCategory
import com.example.bunonbasket.databinding.SubcategoryItemLayoutBinding

/**
 * Created by inan on 3/5/21
 */
class SubCategoryAdapter :
    ListAdapter<SubCategory, SubCategoryAdapter.SubCategoryViewHolder>(DiffCallback()) {

    inner class SubCategoryViewHolder(private val binding: SubcategoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subCategory: SubCategory?) {
            binding.data = subCategory
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<SubCategory>() {
        override fun areItemsTheSame(oldItem: SubCategory, newItem: SubCategory) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SubCategory, newItem: SubCategory) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        val binding =
            SubcategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
        val subCategory = getItem(position)
        holder.bind(subCategory)
    }
}