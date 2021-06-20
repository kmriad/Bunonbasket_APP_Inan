package com.example.bunonbasket.ui.component.details.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.databinding.ColorItemLayoutBinding

/**
 * Created by inan on 20/6/21
 */
class ColorsAdapter : ListAdapter<String, ColorsAdapter.ColorsViewHolder>(DiffCallback()) {

    inner class ColorsViewHolder(private val binding: ColorItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(colorString: String) {
            binding.data = colorString
            binding.colorBtn.setBackgroundColor(Color.parseColor(colorString))
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsViewHolder {
        val binding =
            ColorItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorsViewHolder, position: Int) {
        val choiceOption = getItem(position)
        holder.bind(choiceOption)
    }
}