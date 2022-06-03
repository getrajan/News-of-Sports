package com.NewsofSports.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.NewsofSports.api.ApiResource
import com.NewsofSports.data.Repository
import com.NewsofSports.data.model.news.NewsResponse
import com.pegotec.retrofit_coroutine.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: Repository) : ViewModel() {
    private val _getNewsResponse = MutableLiveData<Resource<NewsResponse>>()
    val getNewsResponse: LiveData<Resource<NewsResponse>>
        get() = _getNewsResponse

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch(Dispatchers.IO) {
            _getNewsResponse.postValue(Resource.loading(data = null))
            try {
                val response = repository.getNews()
                response.let {
                    when (it) {
                        is ApiResource.Success -> {
                            _getNewsResponse.postValue(Resource.success(data = it.value))
                        }
                        is ApiResource.Failure -> {
                                _getNewsResponse.postValue(
                                    Resource.error(
                                        data = null,
                                        message = it.errorBody.toString()
                                    )
                                )
                        }

                    }
                }
            } catch (exception: Exception) {
                _getNewsResponse.postValue(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "something gone wrong"
                    )
                )
            }
        }
    }
}