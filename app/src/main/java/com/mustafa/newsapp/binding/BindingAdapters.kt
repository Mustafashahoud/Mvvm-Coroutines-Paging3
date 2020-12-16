package com.mustafa.newsapp.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import coil.load

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String?) {
    url?.let {
        imageView.load(it)
    }
}

@Suppress("unused")
@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}

@Suppress("unused")
@BindingAdapter("bindToast")
fun bindToast(view: View, loadState: CombinedLoadStates) {
    val errorState =
        loadState.refresh as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error
    errorState?.let {
        Toast.makeText(
            view.context,
            "\uD83D\uDE28 Wooops ${it.error}",
            Toast.LENGTH_LONG
        ).show()
    }
}

@Suppress("unused")
@BindingAdapter("setDate")
fun bindDate(view: TextView, date: String) {
    view.text = date.replace("[TZ]".toRegex(), "  ").take(20)
}


