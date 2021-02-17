package com.openweathermap.weatherforecast.data.model

import com.google.gson.annotations.SerializedName


data class WeatherForecastResponse(
    @SerializedName("cnt")
    val count: Int,

    @SerializedName("list")
    val weatherForecasts: List<WeatherForecast>
)
