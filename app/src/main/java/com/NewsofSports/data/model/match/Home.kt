package com.NewsofSports.data.model.match

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Home(
    val cc: String,
    val id: String,
    val image_id: String,
    val name: String
) : Parcelable