package com.example.bunonbasket.ui.component.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.databinding.SubCategoryProductItemLayoutBinding
import com.example.bunonbasket.databinding.ViewAllButtonBinding
import com.example.bunonbasket.utils.Constants.TYPE_FOOTER
import com.example.bunonbasket.utils.Constants.TYPE_ITEM

/**
 * Created by inan on 9/5/21
 */
class SubCategoryProductAdapter(
    private val listener: OnItemClickListener,
) :
    ListAdapter<Product, RecyclerView.ViewHolder>(DiffCallback()) {

    inner class SubCategoryProductViewHolder(private val binding: SubCategoryProductItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val product = getItem(position)
                        listener.onItemClick(product = product)
                    }
                }
            }
        }

        fun bind(product: Product?) {
            binding.data = product
            binding.executePendingBindings()
        }

    }

    inner class ViewAllProductsViewHolder(private val binding: ViewAllButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            if (currentList.isNotEmpty()) {
                binding.viewAllButton.visibility = View.VISIBLE
            }
        }

        fun bind() {

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (viewType == TYPE_ITEM) {
            val binding =
                SubCategoryProductItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return SubCategoryProductViewHolder(binding)
        } else if (viewType == TYPE_FOOTER) {
            val binding =
                ViewAllButtonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return ViewAllProductsViewHolder(binding)
        }
        throw RuntimeException("There is no type that matches the type $viewType. Make sure you are using view types correctly!")
    }

    override fun getItemCount(): Int {
        if (currentList.size > 6) {
            return 6
        }
        return currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_ITEM -> (holder as SubCategoryProductViewHolder).bind(getItem(position))
            TYPE_FOOTER -> (holder as ViewAllProductsViewHolder).bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == currentList.size) TYPE_FOOTER else TYPE_ITEM
    }
}