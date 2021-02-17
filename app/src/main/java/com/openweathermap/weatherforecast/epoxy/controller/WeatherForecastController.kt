package com.openweathermap.weatherforecast.epoxy.controller

import com.airbnb.epoxy.AsyncEpoxyController
import com.openweathermap.weatherforecast.data.model.City
import com.openweathermap.weatherforecast.data.model.WeatherForecast
import com.openweathermap.weatherforecast.epoxy.model.weatherForecast

class WeatherForecastController : AsyncEpoxyController() {
    var weatherForecasts: List<WeatherForecast> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    var cities: List<City> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        weatherForecasts.map { weatherForecast ->
            weatherForecast {
                id(weatherForecast.id)
                weatherForecast(weatherForecast)
                cities
                    .filter { it.cityId == weatherForecast.id }
                    .map { favorite(it.isFavorite) }
            }
        }
    }
}