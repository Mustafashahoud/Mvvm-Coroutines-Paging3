package com.mustafa.newsapp.db

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.mustafa.newsapp.model.Article
import java.util.concurrent.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article): Long

    @Query("SELECT * FROM articles ORDER BY id ASC ")
    fun getAllArticles(): PagingSource<Int, Article>

    @Delete
    suspend fun deleteArticle(article: Article)
}