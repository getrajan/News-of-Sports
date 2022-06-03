package com.NewsofSports.data.model.match

data class MatchResponse(
    val capacity_requests: String,
    val games_live: List<Match>,
    val last_time_your_key: String,
    val remain_requests: String,
    val time_request: Double
)