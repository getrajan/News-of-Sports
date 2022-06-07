package com.NewsofSports.api

import com.NewsofSports.utility.Constants


class ApiHelper(private val apiService: ApiService) {
    suspend fun getNews() = apiService.getNews()
    suspend fun getLiveMatches() = apiService.getLiveMatches()
    suspend fun getPreMatches() = apiService.getPreMatches()
    suspend fun getTeamMatches(team: String) = apiService.getTeamDetails(
        login = Constants.login,
        token = Constants.token,
        task = "enddatapage",
        sport = "soccer",
        team = team
    )
}