package com.NewsofSports.data

import com.NewsofSports.api.ApiHelper
import com.NewsofSports.api.SafeApiCall

class Repository(private val apiHelper: ApiHelper) : SafeApiCall {

    suspend fun getNews() = safeApiCall {
        apiHelper.getNews()
    }

    suspend fun getLiveMatches() = safeApiCall {
        apiHelper.getLiveMatches()
    }

    suspend fun getPreMatches() = safeApiCall {
        apiHelper.getPreMatches()
    }
}