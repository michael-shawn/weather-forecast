package com.openweathermap.weatherforecast.views.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.openweathermap.weatherforecast.R
import com.openweathermap.weatherforecast.data.repository.CityRepository
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.di
import org.kodein.di.instance

class MainActivity : AppCompatActivity(), DIAware {

    override val di: DI by di()

    private val cityRepository: CityRepository by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        if (savedInstanceState == null) {
            lifecycleScope.launch {
                cityRepository.deletePersistedCities()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}