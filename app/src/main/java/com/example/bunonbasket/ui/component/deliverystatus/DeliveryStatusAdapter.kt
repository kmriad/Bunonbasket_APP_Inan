package com.example.bunonbasket.ui.component.deliverystatus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.data.models.deliverystatus.Detial
import com.example.bunonbasket.databinding.DeliveryStatusItemLayoutBinding

class DeliveryStatusAdapter() :
    ListAdapter<Detial, DeliveryStatusAdapter.DeliveryStatusViewHolder>(DiffCallback()) {

    inner class DeliveryStatusViewHolder(private val binding: DeliveryStatusItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val product = getItem(position)
                    }

                }
            }
        }

        fun bind(wishListModel: Detial?) {
            binding.data = wishListModel
        }

    }


    class DiffCallback : DiffUtil.ItemCallback<Detial>() {
        override fun areItemsTheSame(oldItem: Detial, newItem: Detial) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Detial, newItem: Detial) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryStatusViewHolder {
        val binding =
            DeliveryStatusItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return DeliveryStatusViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryStatusViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}