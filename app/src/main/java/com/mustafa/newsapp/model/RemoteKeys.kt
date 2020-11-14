package com.mustafa.newsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val articleId: Long = 1,
    val prevKey: Int?,
    val nextKey: Int?
)