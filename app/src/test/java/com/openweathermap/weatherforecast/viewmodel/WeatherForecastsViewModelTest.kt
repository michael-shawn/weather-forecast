package com.openweathermap.weatherforecast.viewmodel

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.timeout
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.openweathermap.weatherforecast.BaseTest
import com.openweathermap.weatherforecast.data.model.WeatherForecastResponse
import com.openweathermap.weatherforecast.data.network.Resource
import com.openweathermap.weatherforecast.utilities.MockData.citiesIds
import com.openweathermap.weatherforecast.utilities.MockData.manilaWeatherForecast
import com.openweathermap.weatherforecast.utilities.MockData.pragueWeatherForecast
import com.openweathermap.weatherforecast.utilities.MockData.seoulWeatherForecast
import com.openweathermap.weatherforecast.viewmodels.WeatherForecastsViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherForecastsViewModelTest : BaseTest() {

    private lateinit var viewModel: WeatherForecastsViewModel

    @Mock
    private lateinit var weatherForecastsObserver: Observer<Resource<WeatherForecastResponse>>

    private val weatherForecastResponse = WeatherForecastResponse(
        count = 3,
        weatherForecasts = listOf(
            manilaWeatherForecast,
            pragueWeatherForecast,
            seoulWeatherForecast
        )
    )
    private val weatherForecastsResource = Resource.Success(weatherForecastResponse)

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = WeatherForecastsViewModel(forecastRepository, cityRepository)

        runBlocking {
            whenever(forecastRepository.getListWeatherForecast(citiesIds))
                .thenReturn(weatherForecastsResource)
        }
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun reset() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun getSuccessfulListWeatherForecasts() = runBlocking {
        viewModel.weatherForecasts.observeForever(weatherForecastsObserver)
        viewModel.getListWeatherForecasts()
        delay(20)
        verify(weatherForecastsObserver, timeout(50)).onChanged(weatherForecastsResource)
    }
}