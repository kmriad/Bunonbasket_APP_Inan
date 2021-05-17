package com.example.bunonbasket.ui.component.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.data.models.category.SubCategory
import com.example.bunonbasket.databinding.SubcategoryItemLayoutBinding

/**
 * Created by inan on 3/5/21
 */
class SubCategoryAdapter(
    private val listener: OnItemClickListener,
) :
    ListAdapter<SubCategory, SubCategoryAdapter.SubCategoryViewHolder>(DiffCallback()) {
    private val viewPool = RecyclerView.RecycledViewPool()
    private val spanCount: Int = 2

    inner class SubCategoryViewHolder(private val binding: SubcategoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
              /*  root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val subCategory = getItem(position)
                        currentList.forEach { category ->
                            subCategory.isSelected = false;
                            binding.productGridView.visibility = View.GONE
                        }
                        subCategory.isSelected = !subCategory.isSelected
                        if (subCategory.isSelected) {
                            binding.productGridView.visibility = View.VISIBLE
                        }
                        notifyDataSetChanged()
                        listener.onItemClick(subCategory)
                    }
                }*/
            }
        }

        fun bind(subCategory: SubCategory?) {
            binding.data = subCategory
            if (subCategory!!.products.isNotEmpty()) {
                binding.viewAllButton.visibility = View.VISIBLE
            }
            binding.productGridView.apply {
                layoutManager = if (subCategory.products.size > 3) {
                    GridLayoutManager(
                        binding.root.context,
                        spanCount,
                        GridLayoutManager.HORIZONTAL,
                        false
                    )
                } else {
                    GridLayoutManager(
                        binding.root.context,
                        1,
                        GridLayoutManager.HORIZONTAL,
                        false
                    )
                }
            }
            binding.viewAllButton.setOnClickListener {
                listener.onViewAllClicked(subCategory)
            }
            binding.productGridView.setRecycledViewPool(viewPool)
            binding.executePendingBindings()
        }

    }

    interface OnItemClickListener {
        fun onItemClick(subCategory: SubCategory?)
        fun onViewAllClicked(subCategory: SubCategory?)
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