package com.ramonapp.android.core.repository.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ApiResponse(

    @SerializedName("location") var location: Location?,
    @SerializedName("current") var current: Current?,
    @SerializedName("success") var success: Boolean?,
    @SerializedName("error") var error: ApiResponseError?
)

@Parcelize
data class Current(

    @SerializedName("observation_time") var observationTime: String? = null,
    @SerializedName("temperature") var temperature: Int? = null,
    @SerializedName("weather_code") var weatherCode: Int? = null,
    @SerializedName("weather_icons") var weatherIcons: List<String>? = null,
    @SerializedName("weather_descriptions") var weatherDescriptions: List<String>? = null,
    @SerializedName("wind_speed") var windSpeed: Int? = null,
    @SerializedName("wind_degree") var windDegree: Int? = null,
    @SerializedName("wind_dir") var windDir: String? = null,
    @SerializedName("pressure") var pressure: Int? = null,
    @SerializedName("precip") var precip: Float? = null,
    @SerializedName("humidity") var humidity: Int? = null,
    @SerializedName("cloudcover") var cloudcover: Int? = null,
    @SerializedName("feelslike") var feelslike: Int? = null,
    @SerializedName("uv_index") var uvIndex: Int? = null,
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("is_day") var isDay: String? = null

): Parcelable


data class Location(

    @SerializedName("name") var name: String?,
    @SerializedName("country") var country: String?,
    @SerializedName("region") var region: String?,
    @SerializedName("lat") var lat: String?,
    @SerializedName("lon") var lon: String?,
    @SerializedName("timezone_id") var timezoneId: String?,
    @SerializedName("localtime") var localtime: String?,
    @SerializedName("localtime_epoch") var localtimeEpoch: Int?,
    @SerializedName("utc_offset") var utcOffset: String?
)


data class ApiResponseError(
    val code: Int?,
    val type: String?,
    val info: String?
)
