package com.mustafa.newsapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.mustafa.newsapp.db.ArticleDao
import com.mustafa.newsapp.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavedNewsRepository @Inject constructor(private val articleDao: ArticleDao) {
    fun getSavedNews(): Flow<PagingData<Article>> {
        val pagingSource: PagingSource<Int, Article> = articleDao.getAllArticles()
        return Pager(
            PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            )
        ) { pagingSource }
            .flow
    }

    companion object {
        // Depending on the backend .. in my case it is 20
        private const val NETWORK_PAGE_SIZE = 20
    }

    suspend fun deleteArticle(article: Article) {
        articleDao.deleteArticle(article)
    }

}