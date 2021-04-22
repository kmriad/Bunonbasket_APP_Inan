package com.example.bunonbasket.ui.component.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.banner.Banner
import com.example.bunonbasket.databinding.BannerItemLayoutBinding

/**
 * Created by inan on 22/4/21
 */

class BannerAdapter : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(private val binding: BannerItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(banner: Banner?) {
            binding.root.apply {
                Glide.with(this)
                    .load("https://bunonbasket.com/${banner?.photo}")
                    .centerInside()
                    .placeholder(R.drawable.bb_logo)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(binding.bannerImage)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding =
            BannerItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val banner = differ.currentList[position]
        holder.bind(banner)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size;
    }
}

