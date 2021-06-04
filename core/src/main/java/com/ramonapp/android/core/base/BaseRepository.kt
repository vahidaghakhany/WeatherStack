package com.ramonapp.android.core.base

import com.ramonapp.android.core.data.RetrofitResult
import com.ramonapp.android.core.repository.model.ApiResponse


open class BaseRepository {
    fun  execute(response: RetrofitResult<ApiResponse>): APIResult<ApiResponse> {
        return if (response is RetrofitResult.RetrofitSuccess) {
            if (response.data?.success == false) {
                APIResult.Error(
                    response.data.error?.code ?: 0,
                    Exception(response.data.error?.info))
            } else {
                APIResult.Success(response.data)
            }
        } else if (response is RetrofitResult.RetrofitFailure) {
            APIResult.Error(response.statusCode, response.e)
        } else {
            APIResult.Error(0, Exception("خطای عمومی"))
        }
    }
}


sealed class APIResult<out T> {
    data class Success<T>(val data: T?) : APIResult<T>()
    data class Error(val statusCode: Int?, val e: Exception) : APIResult<Nothing>()
}