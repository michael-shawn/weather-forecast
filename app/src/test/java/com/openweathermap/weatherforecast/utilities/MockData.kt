package com.openweathermap.weatherforecast.utilities

import com.openweathermap.weatherforecast.data.model.Main
import com.openweathermap.weatherforecast.data.model.Weather
import com.openweathermap.weatherforecast.data.model.WeatherForecast

object MockData {
    const val manilaCityId = 1701668
    val citiesIds = listOf(1701668, 3067696, 1835848).joinToString(separator = ",")

    val manilaWeatherForecast = WeatherForecast(
        id = 1701668,
        cityName = "Manila",
        weather = listOf(Weather(id = 801, main = "Clouds")),
        main = Main(
            currentTemperature = 28.35,
            highTemperature = 27.78,
            lowTemperature = 29.0
        )
    )
    val pragueWeatherForecast = WeatherForecast(
        id = 1701668,
        cityName = "Prague",
        weather = listOf(Weather(id = 500, main = "Rain")),
        main = Main(
            currentTemperature = 0.29,
            highTemperature = -1.11,
            lowTemperature = 1.67
        )
    )
    val seoulWeatherForecast = WeatherForecast(
        id = 1701668,
        cityName = "Seoul",
        weather = listOf(Weather(id = 801, main = "Clouds")),
        main = Main(
            currentTemperature = -8.0,
            highTemperature = -8.0,
            lowTemperature = -8.0
        )
    )
}