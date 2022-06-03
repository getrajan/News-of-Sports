package com.NewsofSports.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.NewsofSports.api.ApiHelper
import com.NewsofSports.data.Repository


class MatchesViewModelFactory(
    private val apiHelper: ApiHelper,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MatchesViewModel(
            Repository(apiHelper),
        ) as T
    }
}