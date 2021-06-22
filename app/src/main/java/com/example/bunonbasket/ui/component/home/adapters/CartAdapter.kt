package com.example.bunonbasket.ui.component.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.data.models.cart.CartListModel
import com.example.bunonbasket.databinding.CartItemLayoutBinding

/**
 * Created by inan on 22/6/21
 */
class CartAdapter :
    ListAdapter<CartListModel, CartAdapter.CartViewHolder>(DiffCallback()) {

    inner class CartViewHolder(private val binding: CartItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cart: CartListModel) {
            binding.cart = cart
            binding.data = cart.product
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CartListModel>() {
        override fun areItemsTheSame(oldItem: CartListModel, newItem: CartListModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CartListModel, newItem: CartListModel) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding =
            CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = getItem(position)
        holder.bind(cart)
    }
}