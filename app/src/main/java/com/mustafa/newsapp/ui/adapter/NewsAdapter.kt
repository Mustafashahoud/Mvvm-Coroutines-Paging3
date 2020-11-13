package com.mustafa.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mustafa.newsapp.R
import com.mustafa.newsapp.databinding.ItemArticlePreviewBinding
import com.mustafa.newsapp.model.Article
import com.mustafa.newsapp.util.DiffUtilCallBack

class NewsAdapter : PagingDataAdapter<Article, NewsAdapter.ViewHolder>(DiffUtilCallBack()) {

    class ViewHolder(private val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.article = article
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemArticlePreviewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_article_preview, parent, false
        )
        return ViewHolder(binding)
    }
}