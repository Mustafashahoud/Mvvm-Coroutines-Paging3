package com.mustafa.newsapp.repository

import androidx.paging.PagingSource
import com.bumptech.glide.load.HttpException
import com.mustafa.newsapp.api.NewsService
import com.mustafa.newsapp.model.Article
import java.io.IOException
import javax.inject.Inject


/**
 * BreakingNewsDataSource acts here as a repository and the load function gets the data from the API.
 * Since the load function is a suspend function, we can call other suspend functions inside it without any issues
 * which we created in APIService.
 */
class BreakingNewsDataSource @Inject constructor(private val backend: NewsService) :
    PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val currentLoadingPageKey = params.key ?: NEWS_API_STARTING_PAGE_INDEX
            val response = backend.getBreakingNews(pageNumber = currentLoadingPageKey)
            val responseData = mutableListOf<Article>()
            val articles = response.body()?.articles ?: emptyList()
            responseData.addAll(articles)

//            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentLoadingPageKey == NEWS_API_STARTING_PAGE_INDEX) null else currentLoadingPageKey - 1,
                nextKey = if (articles.isEmpty()) null else currentLoadingPageKey.plus(1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        // Depending on the backend .. in my case it is 20, when we get less than 20 means it the last page
        const val perPage : Int = 20

        private const val NEWS_API_STARTING_PAGE_INDEX = 1
    }
}