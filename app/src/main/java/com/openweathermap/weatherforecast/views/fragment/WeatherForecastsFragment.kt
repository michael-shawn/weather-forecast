package com.openweathermap.weatherforecast.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.openweathermap.weatherforecast.data.model.City
import com.openweathermap.weatherforecast.data.model.WeatherForecast
import com.openweathermap.weatherforecast.data.network.Resource
import com.openweathermap.weatherforecast.databinding.FragmentWeatherForecastsBinding
import com.openweathermap.weatherforecast.epoxy.controller.WeatherForecastController
import com.openweathermap.weatherforecast.viewmodels.WeatherForecastsViewModel

class WeatherForecastsFragment :
    BaseFragment<WeatherForecastsViewModel, FragmentWeatherForecastsBinding>() {

    private val weatherForecastController by lazy { WeatherForecastController() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initAdapter()
        onRequestForListOfWeatherForecasts()
        onSwipeRefreshWeatherForecast()
    }

    override fun getViewModel() = WeatherForecastsViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentWeatherForecastsBinding.inflate(inflater, container, false)

    private fun initAdapter() {
        binding.recyclerView.setController(weatherForecastController)
    }

    private fun onRequestForListOfWeatherForecasts() {
        viewModel.weatherForecasts.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> binding.swipeRefresh.isRefreshing = true
                is Resource.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    onRetrieveListOfWeatherForecasts(result.data!!.weatherForecasts)
                }
                is Resource.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun onRetrieveListOfWeatherForecasts(weatherForecasts: List<WeatherForecast>) {
        weatherForecasts.map {
            val cityId = it.id
            val isFavorite = false
            val city = City(cityId, isFavorite)
            viewModel.checkForPersistedCity(city)
        }
        viewModel.getPersistedCities()
        onSubmitWeatherForecastsToController(weatherForecasts)
    }

    private fun onSubmitWeatherForecastsToController(weatherForecasts: List<WeatherForecast>) {
        viewModel.cities.observe(viewLifecycleOwner) { cities ->
            weatherForecastController.weatherForecasts = weatherForecasts
            weatherForecastController.cities = cities
        }
    }

    private fun onSwipeRefreshWeatherForecast() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            viewModel.getListWeatherForecasts()
        }
    }
}