package com.openweathermap.weatherforecast.epoxy.model

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.openweathermap.weatherforecast.R
import com.openweathermap.weatherforecast.data.model.WeatherForecast
import com.openweathermap.weatherforecast.databinding.ItemWeatherForecastBinding
import com.openweathermap.weatherforecast.utilities.Weather
import com.openweathermap.weatherforecast.views.fragment.WeatherForecastsFragmentDirections
import kotlin.math.roundToInt

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass(layout = R.layout.item_weather_forecast)
abstract class WeatherForecastModel :
    EpoxyModelWithHolder<WeatherForecastModel.WeatherForecastHolder>() {

    @EpoxyAttribute
    lateinit var weatherForecast: WeatherForecast

    @EpoxyAttribute
    var favorite: Boolean = false

    override fun bind(holder: WeatherForecastHolder) {
        holder.binding.apply {
            with(weatherForecast) {
                itemWeatherForecastLayout.updateLayoutBackground(main.currentTemperature)
                textViewCityName.updateCityName(cityName)
                textViewCurrentTemperature.updateCurrentTemperature(main.roundedCurrentTemperature)
                textViewWeatherStatus.updateWeatherStatus(weatherStatus)
                imageViewFavoriteIndicator.updateFavoriteCityIndicator(favorite)
                itemWeatherForecastLayout.navigateToDetailedFragment(id)
            }
        }
    }

    private fun CardView.updateLayoutBackground(currentTemperature: Double) {
        val temperature = currentTemperature.roundToInt()
        val freezingCondition = Int.MIN_VALUE until 0
        val coldCondition = 0 until 15
        val warmCondition = 15 until 30
        val hotCondition = 30 until Int.MAX_VALUE
        this.setBackgroundColor(
            when (temperature) {
                in freezingCondition -> changeBackgroundColorTo(Weather.FREEZING.color)
                in coldCondition -> changeBackgroundColorTo(Weather.COLD.color)
                in warmCondition -> changeBackgroundColorTo(Weather.WARM.color)
                in hotCondition -> changeBackgroundColorTo(Weather.HOT.color)
                else -> Color.TRANSPARENT
            }
        )
    }

    private fun changeBackgroundColorTo(color: String): Int = Color.parseColor(color)

    private fun AppCompatTextView.updateCityName(name: String) {
        this.text = name
    }

    private fun AppCompatTextView.updateCurrentTemperature(currentTemperature: String) {
        val initialFormat = this.resources.getString(R.string.temperature)
        val completeFormat = String.format(initialFormat, currentTemperature)
        this.text = completeFormat
    }

    private fun AppCompatTextView.updateWeatherStatus(status: String) {
        this.text = status
    }

    private fun AppCompatImageView.updateFavoriteCityIndicator(isFavorite: Boolean) {
        this.isVisible = isFavorite
        this.setImageResource(
            when (isFavorite) {
                false -> R.drawable.ic_favorite_border
                true -> R.drawable.ic_favorite
            }
        )
    }

    private fun CardView.navigateToDetailedFragment(cityId: Int) {
        val action = WeatherForecastsFragmentDirections.navigateToDetailedFragment(cityId = cityId)
        this.setOnClickListener {
            Navigation.findNavController(it).navigate(action)
        }
    }

    class WeatherForecastHolder : EpoxyHolder() {
        lateinit var binding: ItemWeatherForecastBinding
            private set

        override fun bindView(itemView: View) {
            binding = ItemWeatherForecastBinding.bind(itemView)
        }
    }
}