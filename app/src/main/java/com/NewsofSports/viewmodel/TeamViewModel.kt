package com.NewsofSports.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.NewsofSports.api.ApiResource
import com.NewsofSports.data.Repository
import com.NewsofSports.data.model.TeamMatchResponse
import com.pegotec.retrofit_coroutine.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamViewModel(private val repository: Repository) : ViewModel() {
    private val _getTeamDetailResponse = MutableLiveData<Resource<TeamMatchResponse>>()
    val getTeamDetailResponse: LiveData<Resource<TeamMatchResponse>>
        get() = _getTeamDetailResponse

    suspend fun getTeamMatches(team: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _getTeamDetailResponse.postValue(Resource.loading(data = null))

            try {
                val response = repository.getTeamMatches(team)
                response.let {
                    when (it) {
                        is ApiResource.Success -> {
                            _getTeamDetailResponse.postValue(Resource.success(data = it.value))
                        }
                        is ApiResource.Failure -> {
                            _getTeamDetailResponse.postValue(
                                Resource.error(
                                    data = null,
                                    message = it.errorBody.toString()
                                )
                            )
                        }

                    }
                }
            } catch (e: Exception) {
                _getTeamDetailResponse.postValue(
                    Resource.error(
                        data = null,
                        message = e.message ?: "something gone wrong"
                    )
                )
            }
        }
    }
}