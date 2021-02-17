package com.openweathermap.weatherforecast.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openweathermap.weatherforecast.data.model.City
import com.openweathermap.weatherforecast.data.model.WeatherForecastResponse
import com.openweathermap.weatherforecast.data.network.Resource
import com.openweathermap.weatherforecast.data.repository.CityRepository
import com.openweathermap.weatherforecast.data.repository.ForecastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherForecastsViewModel(
    private val forecastRepository: ForecastRepository,
    private val cityRepository: CityRepository
) : ViewModel() {

    private val _weatherForecasts = MutableLiveData<Resource<WeatherForecastResponse>>()
    val weatherForecasts: LiveData<Resource<WeatherForecastResponse>>
        get() = _weatherForecasts

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>>
        get() = _cities

    private val _persistedCity = MutableLiveData<City>()
    private val persistedCity: LiveData<City>
        get() = _persistedCity

    private val cityIds = listOf(1701668, 3067696, 1835848)

    init {
        getListWeatherForecasts()
    }

    fun getListWeatherForecasts() {
        viewModelScope.launch {
            _weatherForecasts.value = Resource.Loading()
            val weatherForecastsEntries = withContext(Dispatchers.IO) {
                forecastRepository.getListWeatherForecast(cityIds.joinToString(separator = ","))
            }
            _weatherForecasts.value = weatherForecastsEntries
        }
    }

    fun checkForPersistedCity(city: City) {
        viewModelScope.launch {
            val cityEntry = withContext(Dispatchers.IO) {
                cityRepository.getCity(city.cityId)
            }
            _persistedCity.value = cityEntry

            when (persistedCity.value) {
                null -> persistCity(city.cityId, city.isFavorite)
                else -> persistCity(city.cityId, persistedCity.value!!.isFavorite)
            }
        }
    }

    private fun persistCity(cityId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            val city = City(cityId, isFavorite)
            cityRepository.persistCity(city)
        }
    }

    fun getPersistedCities() {
        viewModelScope.launch {
            val cityEntries = withContext(Dispatchers.IO) {
                cityRepository.getCities()
            }
            _cities.value = cityEntries
        }
    }
}