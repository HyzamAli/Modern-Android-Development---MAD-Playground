package com.tut.mvvm_playground.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .applyDefaultRequestOptions(RequestOptions.bitmapTransform(RoundedCorners(16)))
        .load(url?:"")
        .into(imageView)
}