package com.openweathermap.weatherforecast

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.openweathermap.weatherforecast.data.repository.CityRepository
import com.openweathermap.weatherforecast.data.repository.ForecastRepository
import com.openweathermap.weatherforecast.utilities.ResourceHelper
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import org.junit.Rule
import org.mockito.Mock

abstract class BaseTest {

    @JvmField
    @Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    protected val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Mock
    protected var forecastRepository = mock<ForecastRepository>()

    @Mock
    protected var cityRepository = mock<CityRepository>()

    @Mock
    protected var resourceHelper = mock<ResourceHelper>()

}