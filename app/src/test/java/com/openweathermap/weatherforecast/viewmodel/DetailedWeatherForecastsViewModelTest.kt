package com.openweathermap.weatherforecast.viewmodel

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.timeout
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.openweathermap.weatherforecast.BaseTest
import com.openweathermap.weatherforecast.data.model.WeatherForecast
import com.openweathermap.weatherforecast.data.network.Resource
import com.openweathermap.weatherforecast.utilities.MockData.manilaCityId
import com.openweathermap.weatherforecast.utilities.MockData.manilaWeatherForecast
import com.openweathermap.weatherforecast.viewmodels.DetailedWeatherForecastsViewModel
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
class DetailedWeatherForecastsViewModelTest : BaseTest() {

    private lateinit var viewModel: DetailedWeatherForecastsViewModel

    @Mock
    private lateinit var weatherForecastObserver: Observer<Resource<WeatherForecast>>

    private val weatherForecastResource = Resource.Success(manilaWeatherForecast)

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = DetailedWeatherForecastsViewModel(
            forecastRepository,
            cityRepository,
            resourceHelper
        )

        runBlocking {
            whenever(forecastRepository.getDetailedWeatherForecast(manilaCityId))
                .thenReturn(weatherForecastResource)
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
    fun getSuccessfulDetailedWeatherForecast() = runBlocking {
        viewModel.weatherForecast.observeForever(weatherForecastObserver)
        viewModel.getDetailedWeatherForecast(manilaCityId)
        delay(20)
        verify(weatherForecastObserver, timeout(50)).onChanged(weatherForecastResource)
    }
}