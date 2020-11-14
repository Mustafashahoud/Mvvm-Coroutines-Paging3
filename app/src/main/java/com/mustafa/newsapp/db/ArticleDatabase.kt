package com.mustafa.newsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mustafa.newsapp.model.Article
import com.mustafa.newsapp.model.RemoteKeys

@Database(
    entities = [Article::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}