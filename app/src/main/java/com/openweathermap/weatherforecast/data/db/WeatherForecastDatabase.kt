package com.openweathermap.weatherforecast.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.openweathermap.weatherforecast.data.model.City
import com.openweathermap.weatherforecast.utilities.Constants.DB_NAME

@Database(
    entities = [City::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherForecastDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    companion object {
        @Volatile
        private var instance: WeatherForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context): WeatherForecastDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                WeatherForecastDatabase::class.java,
                DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}