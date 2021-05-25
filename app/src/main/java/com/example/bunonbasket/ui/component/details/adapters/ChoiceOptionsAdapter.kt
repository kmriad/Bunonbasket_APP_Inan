package com.example.bunonbasket.ui.component.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.data.models.product.ChoiceOption
import com.example.bunonbasket.databinding.ChoiceOptiosLayoutBinding

/**
 * Created by inan on 25/5/21
 */
class ChoiceOptionsAdapter :
    ListAdapter<ChoiceOption, ChoiceOptionsAdapter.ChoiceOptionViewHolder>(DiffCallback()) {
    private val viewPool = RecyclerView.RecycledViewPool()

    inner class ChoiceOptionViewHolder(private val binding: ChoiceOptiosLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.optionItemRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }

        fun bind(choiceOption: ChoiceOption) {
            binding.data = choiceOption
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ChoiceOption>() {
        override fun areItemsTheSame(oldItem: ChoiceOption, newItem: ChoiceOption) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: ChoiceOption, newItem: ChoiceOption) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceOptionViewHolder {
        val binding =
            ChoiceOptiosLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChoiceOptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChoiceOptionViewHolder, position: Int) {
        val choiceOption = getItem(position)
        holder.bind(choiceOption)
    }
}