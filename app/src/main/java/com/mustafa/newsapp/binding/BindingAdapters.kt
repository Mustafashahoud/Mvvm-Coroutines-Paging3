package com.mustafa.newsapp.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mustafa.newsapp.model.Article

@BindingAdapter("imageUrl")
fun bindImage (imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView.context)
            .load(it)
            .into(imageView)
    }

}
