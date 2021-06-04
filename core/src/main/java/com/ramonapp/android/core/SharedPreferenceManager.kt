package com.ramonapp.android.core

import com.ramonapp.android.core.PreferenceHelper.get
import com.ramonapp.android.core.PreferenceHelper.set

object SharedPreferenceManager {

    private const val PREF_UNIT = "pref_unit"
    private const val PREF_CITY = "pref_city"
    private const val PREF_IS_LOCATION_ON = "pref_is_location_on"

    private val prefs = PreferenceHelper.customPrefs()

    fun getUnit(): String {
        val unit: String? = prefs[PREF_UNIT]
        return unit ?: UnitType.Metric.sign
    }

    fun saveUnit(unit: String) {
        prefs[PREF_UNIT] = unit
    }

    fun getCurrentCity(): String {
        val city: String? = prefs[PREF_CITY]
        return city ?: "tehran"
    }

    fun saveCurrentCity(city: String) {
        prefs[PREF_CITY] = city
    }

    fun isLocationOn(): Boolean {
        val locationTurnOn: Boolean? = prefs[PREF_IS_LOCATION_ON]
        return locationTurnOn ?: false
    }

    fun changeLocationState(locationTurnOn: Boolean) {
        prefs[PREF_IS_LOCATION_ON] = locationTurnOn
    }
}