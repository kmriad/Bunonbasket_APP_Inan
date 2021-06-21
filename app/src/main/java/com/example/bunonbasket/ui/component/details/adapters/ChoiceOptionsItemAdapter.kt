package com.example.bunonbasket.ui.component.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.databinding.ChoiceOptionItemLayoutBinding

/**
 * Created by inan on 25/5/21
 */
class ChoiceOptionsItemAdapter(private val listener: OnChoiceOptionSelectListener) :
    ListAdapter<String, ChoiceOptionsItemAdapter.ChoiceOptionItemViewHolder>(DiffCallback()) {

    inner class ChoiceOptionItemViewHolder(private val binding: ChoiceOptionItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onChoiceOptionSelect(currentList[position])
                    }
                }
            }
        }

        fun bind(choiceOption: String) {
            binding.data = choiceOption
        }
    }

    interface OnChoiceOptionSelectListener {
        fun onChoiceOptionSelect(option: String)
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceOptionItemViewHolder {
        val binding =
            ChoiceOptionItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ChoiceOptionItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChoiceOptionItemViewHolder, position: Int) {
        val choiceOption = getItem(position)
        holder.bind(choiceOption)
    }
}