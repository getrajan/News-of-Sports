package com.NewsofSports.data.model

import com.NewsofSports.data.model.match.Match

data class TeamMatchResponse(
    val capacity_requests: String,
    val games_end: List<Match>,
    val last_time_your_key: String,
    val remain_requests: String,
    val time_request: Double
)