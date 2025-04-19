package com.comp304.dongheun_santiago_comp304sec002_lab03.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val name: String,
    val coord: Coord,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)

@Serializable
data class Main(
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    val humidity: Int
)

@Serializable
data class Weather(
    val main: String,
    val description: String
)

@Serializable
data class Wind(
    val speed: Double
)

@Serializable
data class Coord(
    val lat: Float,
    val lon: Float,
)