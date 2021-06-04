package com.ramonapp.android.weatherstack.today

import androidx.lifecycle.viewModelScope
import com.ramonapp.android.core.SharedPreferenceManager
import com.ramonapp.android.core.UnitType
import com.ramonapp.android.core.base.APIResult
import com.ramonapp.android.core.base.BaseViewModel
import com.ramonapp.android.core.repository.Repository
import com.ramonapp.android.core.repository.model.Current
import com.ramonapp.android.core.repository.model.Location
import com.shatelland.namava.common.core.mvvm.SingleLiveEvent
import kotlinx.coroutines.launch

class TodayViewModel(private val repository: Repository) : BaseViewModel() {

    val weatherTypeLv = SingleLiveEvent<String>()
    val degreeLv = SingleLiveEvent<String>()
    val feelLikeLv = SingleLiveEvent<String>()
    val weatherImageLv = SingleLiveEvent<String>()
    val windLv = SingleLiveEvent<String>()
    val precipitationLv = SingleLiveEvent<String>()
    val visibilityLv = SingleLiveEvent<String>()
    val locationLv = SingleLiveEvent<String>()

    fun getCurrentWeatherData(loc: String? = null) {
        viewModelScope.launch {

            val result = repository.getCurrent(loc ?: SharedPreferenceManager.getCurrentCity())
            if (result is APIResult.Success) {
                result.data?.apply {
                    setViewParameter(current,location)
                }

            } else if (result is APIResult.Error) {
                mError.value = result.e.message
            }
        }
    }

    fun isLocationOn() = SharedPreferenceManager.isLocationOn()


    fun setViewParameter(current: Current?, location: Location? = null) {

        val unit = when (SharedPreferenceManager.getUnit()) {
            UnitType.Metric.sign -> UnitType.Metric
            UnitType.Imperial.sign -> UnitType.Imperial
            else -> UnitType.Metric
        }

        weatherTypeLv.value = current?.weatherDescriptions?.joinToString()

        degreeLv.value =
            "${current?.temperature} ° ${if (unit == UnitType.Imperial) "F" else "C"}"

        feelLikeLv.value = "Feels like ${current?.feelslike}"

        weatherImageLv.value = current?.weatherIcons?.getOrNull(0)

        windLv.value =
            "Wind: ${current?.windDir} , ${current?.windSpeed} ${if (unit == UnitType.Imperial) "mph" else "kph"}"

        precipitationLv.value =
            "Precipitation: ${current?.precip} ${if (unit == UnitType.Imperial) "in" else "mm"}"

        visibilityLv.value =
            "Visibility: ${current?.visibility} ${if (unit == UnitType.Imperial) "mi" else "km"}"

        location?.run { locationLv.value = "$name, $country" }
    }

}