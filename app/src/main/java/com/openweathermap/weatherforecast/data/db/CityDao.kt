package com.openweathermap.weatherforecast.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openweathermap.weatherforecast.data.model.City

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCity(cityWeatherDao: City)

    @Query("SELECT * FROM city")
    suspend fun getCities(): List<City>

    @Query("SELECT * FROM city WHERE cityId = :cityId")
    suspend fun getCity(cityId: Int): City

    @Query("DELETE FROM city")
    suspend fun deletePersistedCities()

}