package com.mustafa.newsapp.api

import com.mustafa.newsapp.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String = "us",
        @Query("page") pageNumber: Int = 1
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun searchForNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int = 1,
        @Query("country") countryCode: String = "us"
    ): Response<NewsResponse>
}