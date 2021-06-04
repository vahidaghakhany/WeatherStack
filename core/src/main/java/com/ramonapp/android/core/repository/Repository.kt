package com.ramonapp.android.core.repository

import com.ramonapp.android.core.SharedPreferenceManager
import com.ramonapp.android.core.base.BaseRepository
import com.ramonapp.android.core.repository.model.Current


class Repository(private val api: RequestApi) : BaseRepository() {
    suspend fun getCurrent(query: String) =
        execute(api.getCurrent(query, SharedPreferenceManager.getUnit()))

    fun getMockSevenDays(): MutableList<Current> {
        val weather = Current(
            observationTime = "Oct 20, 2021",
            weatherIcons = listOf("https://assets.weatherstack.com/images/wsymbols01_png_64/wsymbol_0001_sunny.png"),
            weatherDescriptions = listOf("Sunny"),
            temperature = 38,
            windSpeed = 15,
            precip = 15F,
            visibility = 10,
            feelslike = 36
        )
        val weatherList = mutableListOf<Current>()
        repeat(7) {
            weatherList.add(weather)
        }
        return weatherList
    }
}