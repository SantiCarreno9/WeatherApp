package com.comp304.dongheun_santiago_comp304sec002_lab03.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = WeatherDatabase.WEATHER_TABLE_NAME,
    primaryKeys = ["lat", "lon"])
data class WeatherEntity(
    val lat: Float,
    val lon: Float,
    val cityName: String,
    val country: String,
    val state: String,
    val temperature: Double,
    val feelsLike: Double,
    val weatherMain: String,
    val description: String,
    val humidity: Int,
    val windSpeed: Double,
    val isFavorite: Boolean = false
)
