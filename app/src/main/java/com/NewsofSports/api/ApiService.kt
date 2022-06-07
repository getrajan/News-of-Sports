package com.NewsofSports.api

import com.NewsofSports.data.model.TeamMatchResponse
import com.NewsofSports.data.model.match.MatchResponse
import com.NewsofSports.data.model.match.PreMatchResponse
import com.NewsofSports.data.model.news.NewsResponse
import com.NewsofSports.utility.Constants.login
import com.NewsofSports.utility.Constants.token
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("/stat/export/iphone/news.json?category_id=208&from=0&count=500&api_version=2")
    suspend fun getNews(): NewsResponse

    @GET("en/get.php?login=$login&token=$token&task=predata&sport=soccer&day=today")
    suspend fun getPreMatches(): PreMatchResponse

    @GET("en/get.php?login=$login&token=$token&task=livedata&sport=soccer")
    suspend fun getLiveMatches(): MatchResponse

    @GET("en/get.php")
    suspend fun getTeamDetails(
        @Query("login") login: String,
        @Query("token") token: String,
        @Query("task") task: String,
        @Query("sport") sport: String,
        @Query("team") team: String,
    ): TeamMatchResponse
}