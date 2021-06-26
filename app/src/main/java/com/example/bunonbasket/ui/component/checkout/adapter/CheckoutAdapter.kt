package com.example.bunonbasket.ui.component.checkout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.data.models.cart.CartListModel
import com.example.bunonbasket.databinding.CheckoutItemLayoutBinding

/**
 * Created by inan on 26/6/21
 */
class CheckoutAdapter :
    ListAdapter<CartListModel, CheckoutAdapter.CheckoutViewHolder>(DiffCallback()) {

    inner class CheckoutViewHolder(private val binding: CheckoutItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cartListModel: CartListModel?) {
            binding.data = cartListModel
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CartListModel>() {
        override fun areItemsTheSame(oldItem: CartListModel, newItem: CartListModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CartListModel, newItem: CartListModel) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        val binding =
            CheckoutItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CheckoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}