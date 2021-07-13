package com.example.bunonbasket.ui.component.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.data.models.brands.Brand
import com.example.bunonbasket.data.models.partners.PartnerModel
import com.example.bunonbasket.databinding.BrandItemLayoutBinding

/**
 * Created by inan on 27/4/21
 */
class BrandAdapter() :
    ListAdapter<PartnerModel, BrandAdapter.BrandViewHolder>(DiffCallback()) {

    inner class BrandViewHolder(private val binding: BrandItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(brand: PartnerModel?) {
            binding.data = brand
        }

    }


    class DiffCallback : DiffUtil.ItemCallback<PartnerModel>() {
        override fun areItemsTheSame(oldItem: PartnerModel, newItem: PartnerModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PartnerModel, newItem: PartnerModel) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val binding =
            BrandItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BrandViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        val brand = getItem(position)
        holder.bind(brand)
    }
}