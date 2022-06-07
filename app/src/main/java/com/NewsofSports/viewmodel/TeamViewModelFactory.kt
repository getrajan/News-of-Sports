package com.NewsofSports.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.NewsofSports.api.ApiHelper
import com.NewsofSports.data.Repository


class TeamViewModelFactory(
    private val apiHelper: ApiHelper,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TeamViewModel(
            Repository(apiHelper),
        ) as T
    }
}