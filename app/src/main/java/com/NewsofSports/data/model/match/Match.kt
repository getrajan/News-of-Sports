package com.NewsofSports.data.model.match

enum class MatchEnum { LIVE, PRE }

data class Match(
    val away: Away,
    val bet365_id: String,
    val game_id: String,
    val home: Home,
    val league: League,
    val score: String,
    val time: String,
    val time_status: String,
    val timer: String,
    var matchType: MatchEnum = MatchEnum.PRE
)