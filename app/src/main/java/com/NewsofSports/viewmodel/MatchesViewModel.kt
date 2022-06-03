package com.NewsofSports.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.NewsofSports.api.ApiResource
import com.NewsofSports.data.Repository
import com.NewsofSports.data.model.match.Match
import com.NewsofSports.data.model.match.MatchEnum
import com.pegotec.retrofit_coroutine.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MatchesViewModel(private val repository: Repository) : ViewModel() {
    private val _getMatchesResponse = MutableLiveData<Resource<List<Match>>>()
    val getMatchesResponse: LiveData<Resource<List<Match>>>
        get() = _getMatchesResponse

    private val matchList: ArrayList<Match> = arrayListOf()

    init {
        getMatches()
    }

    private fun getMatches() {
        viewModelScope.launch(Dispatchers.IO) {
            _getMatchesResponse.postValue(Resource.loading(data = null))
            try {
                val liveMatchesResponse = repository.getLiveMatches()
                liveMatchesResponse.let {
                    when (it) {
                        is ApiResource.Success -> {
                            it.value.games_live.forEach { match ->
                                match.matchType = MatchEnum.LIVE
                                matchList.add(match)
                            }
                            getPreMatches()
                        }
                        is ApiResource.Failure -> {
                            getPreMatches()
                        }
                    }
                }
            } catch (e: Exception) {
                _getMatchesResponse.postValue(
                    Resource.error(
                        data = null,
                        message = e.message ?: "Unknown Error"
                    )
                )
            }
        }
    }

    private suspend fun getPreMatches() {
        withContext(Dispatchers.IO) {
            val preMatchesResponse = repository.getPreMatches()
            preMatchesResponse.let {
                when (it) {
                    is ApiResource.Success -> {
                        it.value.games_pre.forEach { match ->
                            match.matchType = MatchEnum.PRE
                            matchList.add(match)
                        }
                        _getMatchesResponse.postValue(Resource.success(data = matchList))
                    }
                    is ApiResource.Failure -> {
                        if (matchList.isNotEmpty()) {
                            _getMatchesResponse.postValue(Resource.success(data = matchList))
                        } else {
                            _getMatchesResponse.postValue(
                                Resource.error(
                                    data = null,
                                    message = it.errorBody.toString()
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}