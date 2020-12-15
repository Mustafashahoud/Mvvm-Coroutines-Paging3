package com.mustafa.newsapp.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafa.newsapp.model.Article
import com.mustafa.newsapp.repository.ArticleRepository
import kotlinx.coroutines.launch


class ArticleViewModel @ViewModelInject constructor(val repository: ArticleRepository) :
    ViewModel() {

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            repository.insertArticle(article)
        }
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            repository.deleteArticle(article)
        }
    }
}