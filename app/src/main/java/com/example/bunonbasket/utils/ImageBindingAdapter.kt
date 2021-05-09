package com.example.bunonbasket.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.ui.component.home.adapters.SubCategoryProductAdapter

/**
 * Created by inan on 22/4/21
 */
@BindingAdapter("app:patchImageFullUrl")
fun setPatchImageFromUrl(imageView: ImageView, url: String?) {

    if (!url.isNullOrEmpty()) {
        Glide.with((imageView.context.applicationContext))
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView)
    }
}

@BindingAdapter("app:setProducts")
fun RecyclerView.setProducts(products: List<Product>) {
    var fProducts = products
    val subCategoryProductAdapter = SubCategoryProductAdapter()
    if (fProducts.size > 5) {
        fProducts = products.subList(0, 5)
    }
    subCategoryProductAdapter.submitList(fProducts)
    adapter = subCategoryProductAdapter
}