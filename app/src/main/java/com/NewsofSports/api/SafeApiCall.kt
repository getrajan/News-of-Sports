package com.NewsofSports.api

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 *Created by Rajan Karki on 2021-08-31
 */
interface SafeApiCall {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T,
    ): ApiResource<T> {
        return withContext(Dispatchers.IO) {
            try {
                ApiResource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Log.d("ERROR", throwable.message.toString())

                        ApiResource.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody()
                        )
                    }
                    else -> {
                        Log.d("ERROR", throwable.message.toString())
                        ApiResource.Failure(true, throwable.hashCode(), null);
                    }
                }
            }
        }
    }
}