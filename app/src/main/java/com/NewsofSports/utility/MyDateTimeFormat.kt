package com.NewsofSports.utility

import java.text.SimpleDateFormat
import java.util.*

/**
 *Created by Rajan Karki on 2021-09-03
 */
class MyDateTimeFormat {
    companion object {
        private val simpleTimeFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
        private val simpleDateFormat = SimpleDateFormat("dd.MM", Locale.getDefault())
        private val simpleTimeFormatEnglish = SimpleDateFormat("hh:mm a", Locale.getDefault())
        private val simpleDateFormatEnglish = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)

        fun timeFormat(milliSeconds: Long): String {
            return simpleTimeFormat.format(milliSeconds)
        }


        fun dateFormat(milliSeconds: Long): String {
            return simpleDateFormat.format(milliSeconds)
        }

        fun timeFormatEnglish(milliSeconds: Long): String {
            return simpleTimeFormatEnglish.format(milliSeconds)
        }

        fun dateFormatEnglish(milliSeconds: Long): String {
            return simpleDateFormatEnglish.format(milliSeconds)
        }

        fun millisecondFromDateTime(dateTime: String): Long {
            val simpleDateTimeFormal = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.ENGLISH)
            return simpleDateTimeFormal.parse(dateTime).time
        }

        fun getBetweenTime(firstMilliSeconds: Long, secondMilliSeconds: Long): String {
            val difference = (secondMilliSeconds - firstMilliSeconds) / 1000;
            val day = difference / 86400
            val hours = (difference % 86400) / 3600
            val minutes = ((difference % 86400) % 3600) / 60
            val seconds = ((difference % 86400) % 3600) % 60
            return if (day.toInt() == 0 && hours.toInt() == 0 && minutes.toInt() == 0) {
                "${seconds}s"
            } else if (day.toInt() == 0 && hours.toInt() == 0) {
                "${minutes}m ${seconds}s"
            } else if (day.toInt() == 0) {
                "${hours}h ${minutes}m ${seconds}s"
            } else {
                "${day}d:${hours}h:${minutes}m:${seconds}s"
            }

        }
    }
}