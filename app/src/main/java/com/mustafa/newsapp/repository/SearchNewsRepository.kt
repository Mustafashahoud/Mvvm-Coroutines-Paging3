package com.mustafa.newsapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mustafa.newsapp.api.NewsService
import com.mustafa.newsapp.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNewsRepository @Inject constructor(private val service: NewsService) {

    fun getSearchNewsStream(query: String): Flow<PagingData<Article>> {
        return Pager(
            PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            )
        ) {
            SearchNewsPagingSource(service, query)
        }.flow
    }

    companion object {
        // Depending on the backend .. in my case it is 20
        private const val NETWORK_PAGE_SIZE = 20
    }
}