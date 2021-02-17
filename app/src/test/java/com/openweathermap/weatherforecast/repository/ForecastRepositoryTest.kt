package com.openweathermap.weatherforecast.repository

import com.nhaarman.mockitokotlin2.whenever
import com.openweathermap.weatherforecast.BaseTest
import com.openweathermap.weatherforecast.utilities.MockData.citiesIds
import com.openweathermap.weatherforecast.utilities.MockData.manilaCityId
import com.openweathermap.weatherforecast.utilities.MockData.manilaWeatherForecast
import com.openweathermap.weatherforecast.utilities.MockData.pragueWeatherForecast
import com.openweathermap.weatherforecast.utilities.MockData.seoulWeatherForecast
import com.openweathermap.weatherforecast.data.model.WeatherForecastResponse
import com.openweathermap.weatherforecast.data.network.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ForecastRepositoryTest : BaseTest() {

    private val weatherForecastResponse = WeatherForecastResponse(
        count = 3,
        weatherForecasts = listOf(
            manilaWeatherForecast,
            pragueWeatherForecast,
            seoulWeatherForecast
        )
    )
    private val weatherForecastsResource = Resource.Success(weatherForecastResponse)
    private val weatherForecastResource = Resource.Success(manilaWeatherForecast)

    @Before
    fun setup() {
        runBlocking {
            whenever(forecastRepository.getListWeatherForecast(citiesIds))
                .thenReturn(weatherForecastsResource)
            whenever(forecastRepository.getDetailedWeatherForecast(manilaCityId))
                .thenReturn(weatherForecastResource)
        }
    }

    @Test
    fun getSuccessfulListWeatherForecasts() = runBlocking {
        assertEquals(
            weatherForecastsResource,
            forecastRepository.getListWeatherForecast(citiesIds)
        )
    }

    @Test
    fun getSuccessfulDetailedWeatherForecast() = runBlocking {
        assertEquals(
            weatherForecastResource,
            forecastRepository.getDetailedWeatherForecast(manilaCityId)
        )
    }
}