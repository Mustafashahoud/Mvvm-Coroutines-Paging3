package com.mustafa.newsapp.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mustafa.newsapp.model.Article
import com.mustafa.newsapp.repository.SavedNewsRepository
import kotlinx.coroutines.launch

class SavedNewsViewModel @ViewModelInject constructor(
    val repository: SavedNewsRepository
) : ViewModel() {

    val savedNewsStream = repository.getSavedNews().cachedIn(viewModelScope)

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            repository.deleteArticle(article)
        }
    }


}