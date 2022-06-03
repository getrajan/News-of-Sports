package com.NewsofSports.data.model.match

data class PreMatchResponse(
    val capacity_requests: String,
    val games_pre: List<Match>,
    val last_time_your_key: String,
    val remain_requests: String,
    val time_request: Double
)