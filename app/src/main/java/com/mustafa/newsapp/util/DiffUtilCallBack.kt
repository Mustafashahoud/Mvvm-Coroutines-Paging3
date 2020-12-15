package com.mustafa.newsapp.util

import androidx.recyclerview.widget.DiffUtil
import com.mustafa.newsapp.model.Article

class DiffUtilCallBack : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
                && oldItem.id == newItem.id
                && oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem.title == newItem.title &&
                oldItem.author == newItem.author &&
                oldItem.content == newItem.content &&
                oldItem.description == newItem.description &&
                oldItem.publishedAt == newItem.publishedAt &&
                oldItem.url == newItem.url
}