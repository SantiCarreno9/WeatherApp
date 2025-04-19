package com.comp304.dongheun_santiago_comp304sec002_lab03.data

data class WeatherData(
    val cityName: String = "",
    val lat: Float = 0.0f,
    val lon: Float = 0.0f,
    var country: String = "",
    var state: String = "",
    val temperature: Double = 0.0,
    val feelsLike: Double = 0.0,
    val weatherMain: String = "",
    val description: String = "",
    val humidity: Int = 0,
    val windSpeed: Double = 0.0,
    var isFavorite: Boolean = false
)
