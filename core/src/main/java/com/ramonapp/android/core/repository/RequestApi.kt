package com.ramonapp.android.core.repository

import com.ramonapp.android.core.data.RetrofitResult
import com.ramonapp.android.core.repository.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface RequestApi {
    @GET("current")
    suspend fun getCurrent(
        @Query("query") query: String,
        @Query("units") unit: String
    ):RetrofitResult<ApiResponse>
}