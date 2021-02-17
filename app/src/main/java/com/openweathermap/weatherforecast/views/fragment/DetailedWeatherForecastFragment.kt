package com.openweathermap.weatherforecast.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.openweathermap.weatherforecast.data.model.City
import com.openweathermap.weatherforecast.data.network.Resource
import com.openweathermap.weatherforecast.databinding.FragmentDetailedWeatherForecastBinding
import com.openweathermap.weatherforecast.viewmodels.DetailedWeatherForecastsViewModel

class DetailedWeatherForecastFragment :
    BaseFragment<DetailedWeatherForecastsViewModel, FragmentDetailedWeatherForecastBinding>() {

    private val args by navArgs<DetailedWeatherForecastFragmentArgs>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.viewModel = viewModel

        onRequestForDetailedWeatherForecast()
        onSwipeRefreshDetailedWeatherForecast()
        onFavoriteToggleButton()
    }

    override fun getViewModel() = DetailedWeatherForecastsViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailedWeatherForecastBinding.inflate(inflater, container, false)


    private fun onRequestForDetailedWeatherForecast() {
        viewModel.getDetailedWeatherForecast(args.cityId)
        viewModel.weatherForecast.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> binding.swipeRefresh.isRefreshing = true
                is Resource.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    binding.toggleButtonFavorite.visibility = View.VISIBLE
                    viewModel.displayDetailedWeatherForecast(result.data!!)
                }
                is Resource.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun onSwipeRefreshDetailedWeatherForecast() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            viewModel.getDetailedWeatherForecast(args.cityId)
        }
    }

    private fun onFavoriteToggleButton() {
        viewModel.city.observe(viewLifecycleOwner) { city ->
            binding.toggleButtonFavorite.isChecked = city.isFavorite
            binding.toggleButtonFavorite.setOnCheckedChangeListener { _, isChecked ->
                val favoriteCity = City(city.cityId, isChecked)
                viewModel.updateAndMarkCityAsFavorite(favoriteCity)
            }
        }
    }
}