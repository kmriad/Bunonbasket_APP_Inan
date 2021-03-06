package com.example.bunonbasket.ui.component.home.adapters

import android.util.Log
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
class CartAdapter(private val listener: OnCartUpdateListener) :
    ListAdapter<CartListModel, CartAdapter.CartViewHolder>(DiffCallback()) {


    inner class CartViewHolder(private val binding: CartItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        init {

            binding.apply {
                0
                binding.addBtn.setOnClickListener {
                    val position = bindingAdapterPosition
                    Log.d("CartAdapter", position.toString())
                    if (position != RecyclerView.NO_POSITION) {
                        val quantity = getItem(position).quantity
                        listener.onAddButtonClick(
                            quantity = quantity + 1,
                            getItem(position).id
                        )
                    }
                }

                binding.removeBtn.setOnClickListener {
                    val position = bindingAdapterPosition
                    Log.d("CartAdapter", position.toString())
                    val quantity = getItem(position).quantity
                    if (quantity > 1) {
                        listener.onRemoveButtonClick(
                            quantity = quantity - 1, getItem(position).id
                        )
                    }
                }
            }
        }

        fun bind(cart: CartListModel) {
            binding.cart = cart
            binding.data = cart.product
            binding.productNameTextView.tag = ("" + bindingAdapterPosition)
            binding.selectBtn.isChecked = getItem(bindingAdapterPosition).isSelected

            binding.selectBtn.setOnClickListener {
                for (i in currentList) {
                    i.isSelected = false
                }
                val tag: String = binding.productNameTextView.tag.toString()
                val pos: Int = tag.toInt()
                currentList.get(pos).isSelected = !currentList.get(pos).isSelected
                if (currentList.get(pos).isSelected) {
                    listener.onItemSelected(cart)
                }
                notifyDataSetChanged()
            }
        }
    }

    interface OnCartUpdateListener {
        fun onAddButtonClick(quantity: Int, id: Int)
        fun onRemoveButtonClick(quantity: Int, id: Int)
        fun onItemSelected(cart: CartListModel)
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