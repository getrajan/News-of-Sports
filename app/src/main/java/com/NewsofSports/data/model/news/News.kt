package com.NewsofSports.data.model.news

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val can_comment: Boolean,
    val category_id: Int,
    val `class`: String,
    val comment_count: Int,
    val content: String,
//    val content_option: Any,
    val content_type: String,
    val doc_type_id: Int,
    val hot: Boolean,
    val id: Int,
    val link: String,
    val main: Boolean,
    val nick: String,
    val poll_id: Int,
    val posted_time: Int,
    val social_image: String,
    val source_link_href: String,
    val source_link_title: String,
//    val structured_body: List<StructuredBody>,
    val title: String
) : Parcelable