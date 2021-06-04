package com.ramonapp.android.weatherstack.setting

import androidx.lifecycle.viewModelScope
import com.ramonapp.android.core.SharedPreferenceManager
import com.ramonapp.android.core.UnitType
import com.ramonapp.android.core.base.APIResult
import com.ramonapp.android.core.base.BaseViewModel
import com.ramonapp.android.core.repository.Repository
import com.shatelland.namava.common.core.mvvm.SingleLiveEvent
import kotlinx.coroutines.launch

class SettingViewModel() : BaseViewModel() {


    fun setSelectedUnit(unit: String) {
        when (unit) {
            UnitType.Metric.name -> SharedPreferenceManager.saveUnit(UnitType.Metric.sign)
            UnitType.Imperial.name -> SharedPreferenceManager.saveUnit(UnitType.Imperial.sign)
        }
    }

    fun getSelectedUnitIndex(): Int {
        return when (SharedPreferenceManager.getUnit()) {
            UnitType.Metric.sign -> UnitType.Metric.ordinal
            UnitType.Imperial.sign -> UnitType.Imperial.ordinal
            else -> 0
        }
    }

    fun setLocation(input: String?) = input?.also {
        SharedPreferenceManager.saveCurrentCity(it)
    }

    fun isLocationOn() = SharedPreferenceManager.isLocationOn()

    fun setLocationState(state: Boolean) = SharedPreferenceManager.changeLocationState(state)

}