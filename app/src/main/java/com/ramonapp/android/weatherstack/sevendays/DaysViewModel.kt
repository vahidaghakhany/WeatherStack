package com.ramonapp.android.weatherstack.sevendays

import com.ramonapp.android.core.SharedPreferenceManager
import com.ramonapp.android.core.UnitType
import com.ramonapp.android.core.base.BaseViewModel
import com.ramonapp.android.core.repository.Repository
import com.ramonapp.android.core.repository.model.Current
import com.shatelland.namava.common.core.mvvm.SingleLiveEvent

class DaysViewModel(val repository: Repository) : BaseViewModel() {

    val weatherListLv = SingleLiveEvent<List<Current>>()

    fun getSevenDays() {
        weatherListLv.value = repository.getMockSevenDays()
    }

    fun getUnitType() = when (SharedPreferenceManager.getUnit()) {
        UnitType.Metric.sign -> UnitType.Metric
        UnitType.Imperial.sign -> UnitType.Imperial
        else -> UnitType.Metric
    }
}