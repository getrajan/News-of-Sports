package com.NewsofSports.data.model.news

data class Value(
    val elements: List<Element>,
    val items: List<Item>,
    val size: String,
    val style: String,
    val type: String
)