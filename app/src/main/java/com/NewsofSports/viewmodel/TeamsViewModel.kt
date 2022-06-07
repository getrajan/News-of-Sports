package com.NewsofSports.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.NewsofSports.api.ApiResource
import com.NewsofSports.data.Repository
import com.NewsofSports.data.model.match.Home
import com.NewsofSports.data.model.match.Match
import com.pegotec.retrofit_coroutine.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TeamsViewModel(private val repository: Repository) : ViewModel() {
    private val _getTeamsResponse = MutableLiveData<Resource<List<Home>>>()
    val getTeamsResponse: LiveData<Resource<List<Home>>>
        get() = _getTeamsResponse

    private val teamList: ArrayList<Home> = arrayListOf()
    private val matchList: ArrayList<Match> = arrayListOf()


    init {
        getMatches()
    }

    private fun getMatches() {
        viewModelScope.launch(Dispatchers.IO) {
            _getTeamsResponse.postValue(Resource.loading(data = null))
            try {
                val liveMatchesResponse = repository.getLiveMatches()
                Log.d("TAG", "getMatches: $liveMatchesResponse")
                liveMatchesResponse.let {
                    when (it) {
                        is ApiResource.Success -> {
                            it.value.games_live.forEach { match ->
                                teamList.add(match.home)
                            }
                            getPreMatches()
                        }
                        is ApiResource.Failure -> {
                            getPreMatches()
                        }
                    }
                }
            } catch (e: Exception) {
                _getTeamsResponse.postValue(
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
            preMatchesResponse.let { it ->
                when (it) {
                    is ApiResource.Success -> {
                        it.value.games_pre.forEach { match ->
                            teamList.add(match.home)
                        }
                        _getTeamsResponse.postValue(Resource.success(data = teamList.distinctBy { team -> team.id }))
                    }
                    is ApiResource.Failure -> {
                        if (matchList.isNotEmpty()) {
                            _getTeamsResponse.postValue(Resource.success(data = teamList.distinctBy { team -> team.id }))
                        } else {
                            _getTeamsResponse.postValue(
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

    fun searchTeam(name: CharSequence) {
        viewModelScope.launch(Dispatchers.IO) {
            if (name.isNotBlank()) {
                val searchList: List<Home> =
                    teamList.distinctBy { team -> team.id }.filter { team ->
                        team.name.lowercase(Locale.getDefault())
                            .contains(name.toString().lowercase(Locale.getDefault()))
                    }
                _getTeamsResponse.postValue(Resource.success(data = searchList))

            } else {
                _getTeamsResponse.postValue(Resource.success(data = teamList.distinctBy { team -> team.id }))
            }
        }
    }
}