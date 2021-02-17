package com.openweathermap.weatherforecast.data.repository

import com.openweathermap.weatherforecast.data.model.WeatherForecast
import com.openweathermap.weatherforecast.data.model.WeatherForecastResponse
import com.openweathermap.weatherforecast.data.network.Resource

interface ForecastRepository {

    suspend fun getListWeatherForecast(cityIds: String): Resource<WeatherForecastResponse>

    suspend fun getDetailedWeatherForecast(cityId: Int): Resource<WeatherForecast>

}