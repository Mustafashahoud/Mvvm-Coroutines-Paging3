package com.mustafa.newsapp.repository

import com.mustafa.newsapp.db.ArticleDao
import com.mustafa.newsapp.model.Article
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val dao: ArticleDao) {

    suspend fun insertArticle(article: Article) {
        dao.insertArticle(article)
    }

    suspend fun deleteArticle(article: Article) {
        dao.deleteArticle(article)
    }
}