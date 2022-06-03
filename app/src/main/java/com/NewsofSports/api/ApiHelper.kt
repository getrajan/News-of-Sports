package com.NewsofSports.api


class ApiHelper(private val apiService: ApiService) {
    suspend fun getNews() = apiService.getNews()
    suspend fun getLiveMatches() = apiService.getLiveMatches()
    suspend fun getPreMatches() = apiService.getPreMatches()
}