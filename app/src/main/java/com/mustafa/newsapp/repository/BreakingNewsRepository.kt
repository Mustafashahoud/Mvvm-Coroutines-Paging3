package com.mustafa.newsapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import com.mustafa.newsapp.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BreakingNewsRepository @Inject constructor(private val pagingSource: BreakingNewsPagingSource) {

    fun getBreakingNewsStream(): Flow<PagingData<Article>> {
        return Pager(
            PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            )
        ) {
            pagingSource
        }.flow.map { pagingData ->
            pagingData.filter { article ->
                article.url != null && article.url.contains(
                    "https",
                    true
                )
            }
        }
    }

    companion object {
        // Depending on the backend .. in my case it is 20
        private const val NETWORK_PAGE_SIZE = 20
    }
}