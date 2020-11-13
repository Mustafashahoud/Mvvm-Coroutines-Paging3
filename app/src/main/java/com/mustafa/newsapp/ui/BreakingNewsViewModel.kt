package com.mustafa.newsapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.mustafa.newsapp.api.NewsService
import com.mustafa.newsapp.repository.BreakingNewsDataSource

class BreakingNewsViewModel @ViewModelInject constructor(
    private val breakingNewsDataSource: BreakingNewsDataSource
) : ViewModel() {

    val breakingNews = Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        )
    ) {
        breakingNewsDataSource
    }.flow.cachedIn(viewModelScope)

}