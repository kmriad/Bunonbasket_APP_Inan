package com.example.bunonbasket.ui.component.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.data.models.wishlist.WishListModel
import com.example.bunonbasket.databinding.WishlistItemLayoutBinding

class WishListAdapter(
    private val listener: OnItemClickListener,
) : ListAdapter<WishListModel, WishListAdapter.WishListViewHolder>(DiffCallback()) {

    inner class WishListViewHolder(private val binding: WishlistItemLayoutBinding) :
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

        fun bind(wishListModel: WishListModel?) {
            binding.data = wishListModel!!.wishListProduct
            /*if (wishListModel!!.product.discount > 0) {
                binding.discountText.visibility = View.VISIBLE
            } else {
                binding.discountText.visibility = View.INVISIBLE
            }*/
        }

    }

    interface OnItemClickListener {
        fun onItemClick(product: WishListModel?)
    }

    class DiffCallback : DiffUtil.ItemCallback<WishListModel>() {
        override fun areItemsTheSame(oldItem: WishListModel, newItem: WishListModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: WishListModel, newItem: WishListModel) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
        val binding =
            WishlistItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WishListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        val product = getItem(position)
//        Log.d("WishListAdapter", product.product.name)
        holder.bind(product)
    }
}