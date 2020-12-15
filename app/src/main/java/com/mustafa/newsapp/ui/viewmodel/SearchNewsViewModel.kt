package com.mustafa.newsapp.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mustafa.newsapp.model.Article
import com.mustafa.newsapp.repository.SearchNewsRepository
import kotlinx.coroutines.flow.Flow

class SearchNewsViewModel @ViewModelInject constructor(
    val repository: SearchNewsRepository
) : ViewModel() {

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Article>>? = null

    fun searchNews(queryString: String): Flow<PagingData<Article>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString

        val newResult = repository
            .getSearchNewsStream(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }




}