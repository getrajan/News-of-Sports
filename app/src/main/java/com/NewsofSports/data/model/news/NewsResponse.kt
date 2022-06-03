package com.NewsofSports.data.model.news

data class NewsResponse(
    val news: List<News>,
    val total_count: Int
)