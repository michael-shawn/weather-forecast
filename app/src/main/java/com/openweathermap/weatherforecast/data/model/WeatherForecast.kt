package com.openweathermap.weatherforecast.data.model

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val cityName: String,

    @SerializedName("weather")
    val weather: List<Weather>,

    @SerializedName("main")
    val main: Main
) {
    val weatherStatus: String
        get() = weather[0].main
}
