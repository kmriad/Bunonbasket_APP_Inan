package com.example.bunonbasket.ui.component.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.data.models.category.Category
import com.example.bunonbasket.databinding.CategoryItemLayoutBinding

/**
 * Created by inan on 26/4/21
 */
class CategoryAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(DiffCallback()) {

    inner class CategoryViewHolder(private val binding: CategoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val category = getItem(position)
                        currentList.forEach { category ->
                            category.isSelected = false;
                        }
                        category.isSelected = !category.isSelected
                        notifyDataSetChanged()
                        listener.onItemClick(category)
                    }
                }

            }
        }

        fun bind(category: Category?) {
            binding.data = category
        }

    }

    interface OnItemClickListener {
        fun onItemClick(index: Category)
    }

    class DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            CategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }
}