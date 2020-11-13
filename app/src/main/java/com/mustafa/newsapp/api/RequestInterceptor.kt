package com.mustafa.newsapp.api

import com.mustafa.newsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 *  Copied from https://github.com/skydoves/TheMovies
 */
internal class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val url = originalUrl.newBuilder()
            .addQueryParameter("apiKey", BuildConfig.NEWS_API_KEY)
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}