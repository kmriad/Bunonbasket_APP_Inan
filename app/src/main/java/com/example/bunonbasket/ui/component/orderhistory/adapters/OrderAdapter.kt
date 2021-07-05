package com.example.bunonbasket.ui.component.orderhistory.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.orders.OrderHistoryModel
import com.example.bunonbasket.databinding.OrderItemLayoutBinding

class OrderAdapter(
    private val listener: OnItemClickListener,
) : ListAdapter<OrderHistoryModel, OrderAdapter.OrderViewHolder>(DiffCallback()) {

    inner class OrderViewHolder(private val binding: OrderItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val product = getItem(position)
                        listener.onItemClick(order = product)
                    }
                }
            }
        }

        fun bind(order: OrderHistoryModel?) {
            binding.data = order
            binding.deliveryStatus.text = order?.delivery_status!!.capitalize()
            when (order.status) {
                0 -> binding.deliverySection.setBackgroundResource(R.drawable.pending_background)
                1 -> binding.deliverySection.setBackgroundResource(R.drawable.processing_backgound)
                2 -> binding.deliverySection.setBackgroundResource(R.drawable.delivery_status_background)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(order: OrderHistoryModel?)
    }

    class DiffCallback : DiffUtil.ItemCallback<OrderHistoryModel>() {
        override fun areItemsTheSame(oldItem: OrderHistoryModel, newItem: OrderHistoryModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: OrderHistoryModel, newItem: OrderHistoryModel) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding =
            OrderItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order)
    }
}