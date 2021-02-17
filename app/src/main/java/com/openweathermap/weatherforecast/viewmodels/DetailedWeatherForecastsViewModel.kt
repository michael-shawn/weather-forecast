package com.openweathermap.weatherforecast.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openweathermap.weatherforecast.data.model.City
import com.openweathermap.weatherforecast.data.model.WeatherForecast
import com.openweathermap.weatherforecast.data.network.Resource
import com.openweathermap.weatherforecast.data.repository.CityRepository
import com.openweathermap.weatherforecast.data.repository.ForecastRepository
import com.openweathermap.weatherforecast.utilities.ResourceHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailedWeatherForecastsViewModel(
    private val forecastRepository: ForecastRepository,
    private val cityRepository: CityRepository,
    private val resourceHelper: ResourceHelper
) : ViewModel() {

    private val _cityName = MutableLiveData<String>()
    val cityName: LiveData<String>
        get() = _cityName

    private val _currentTemperature = MutableLiveData<String>()
    val currentTemperature: LiveData<String>
        get() = _currentTemperature

    private val _weatherStatus = MutableLiveData<String>()
    val weatherStatus: LiveData<String>
        get() = _weatherStatus

    private val _highLowTemperature = MutableLiveData<String>()
    val highLowTemperature: LiveData<String>
        get() = _highLowTemperature

    private val _weatherForecast = MutableLiveData<Resource<WeatherForecast>>()
    val weatherForecast: LiveData<Resource<WeatherForecast>>
        get() = _weatherForecast

    private val _city = MutableLiveData<City>()
    val city: LiveData<City>
        get() = _city

    init {
        _cityName.value = ""
        _currentTemperature.value = ""
        _weatherStatus.value = ""
        _highLowTemperature.value = ""
    }

    fun getDetailedWeatherForecast(cityId: Int) {
        viewModelScope.launch {
            _weatherForecast.value = Resource.Loading()
            val weatherForecastEntry = withContext(Dispatchers.IO) {
                forecastRepository.getDetailedWeatherForecast(cityId)
            }
            _weatherForecast.value = weatherForecastEntry
        }

        getPersistedCity(cityId)
    }

    private fun getPersistedCity(cityId: Int) {
        viewModelScope.launch {
            val cityEntry = withContext(Dispatchers.IO) {
                cityRepository.getCity(cityId)
            }
            _city.value = cityEntry
        }
    }

    fun displayDetailedWeatherForecast(weatherForecast: WeatherForecast) {
        with(weatherForecast) {
            _cityName.value = cityName
            _currentTemperature.value = String.format(
                resourceHelper.currentTemperature,
                main.roundedCurrentTemperature
            )
            _weatherStatus.value = weatherStatus
            _highLowTemperature.value = String.format(
                resourceHelper.highAndLowTemperature,
                main.roundedHighTemperature,
                main.roundedLowTemperature
            )
        }
    }

    fun updateAndMarkCityAsFavorite(city: City) {
        viewModelScope.launch {
            cityRepository.persistCity(city)
        }
    }
}