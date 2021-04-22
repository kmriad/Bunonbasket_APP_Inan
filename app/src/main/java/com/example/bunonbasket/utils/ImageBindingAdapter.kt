package com.example.bunonbasket.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bunonbasket.R

/**
 * Created by inan on 22/4/21
 */
@BindingAdapter("android:patchImageFullUrl")
fun setPatchImageFromUrl(imageView: ImageView, url: String?) {

    if (!url.isNullOrEmpty()) {
        Glide.with((imageView.context.applicationContext))
            .load(url)
            .error(R.drawable.bb_logo)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(imageView)
    }
}