package com.example.bunonbasket.ui.component.home.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bunonbasket.databinding.LoadStateViewBinding

/**
 * Created by inan on 18/5/21
 */
class ProductLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ProductLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        val progress = holder.binding.loadStateProgress
        val btnRetry = holder.binding.loadStateRetry
        val txtErrorMessage = holder.binding.loadStateErrorMessage

       // btnRetry.isVisible = loadState !is LoadState.Loading
       // txtErrorMessage.isVisible = loadState !is LoadState.Loading
        progress.isVisible = loadState is LoadState.Loading

        if (loadState is LoadState.Error) {
            txtErrorMessage.text = loadState.error.localizedMessage
        }

        btnRetry.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LoadStateViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    class LoadStateViewHolder(val binding: LoadStateViewBinding) :
        RecyclerView.ViewHolder(binding.root)
}