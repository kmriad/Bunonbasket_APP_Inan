package com.example.bunonbasket.utils

import android.text.TextWatcher
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.ui.component.details.adapters.ChoiceOptionsItemAdapter
import com.example.bunonbasket.ui.component.details.adapters.ChoiceOptionsItemAdapter.OnChoiceOptionSelectListener
import com.example.bunonbasket.ui.component.home.adapters.SubCategoryProductAdapter
import com.google.android.material.textfield.TextInputEditText

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
    val subCategoryProductAdapter = SubCategoryProductAdapter()
    subCategoryProductAdapter.submitList(products)
    adapter = subCategoryProductAdapter
}

@BindingAdapter("app:setOptions")
fun RecyclerView.setOptions(options: List<String>) {

    val optionsItemAdapter = ChoiceOptionsItemAdapter(object : OnChoiceOptionSelectListener {
        override fun onChoiceOptionSelect(option: String) {

        }
    })
    optionsItemAdapter.submitList(options)
    adapter = optionsItemAdapter
}

@BindingAdapter("app:setProductImageUrl")
fun setProductImageUrl(imageView: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        val fullUrl = "${Constants.BASE_URL}$url"
        Glide.with((imageView.context.applicationContext))
            .load(fullUrl)
            .placeholder(R.drawable.placeholder2)
            .error(R.drawable.placeholder2)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView)
    }
}

@BindingAdapter("textChangedListener")
fun bindTextWatcher(editText: TextInputEditText, textWatcher: TextWatcher) {
    editText.addTextChangedListener(textWatcher)
}
