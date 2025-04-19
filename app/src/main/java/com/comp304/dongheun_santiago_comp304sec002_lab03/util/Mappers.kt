package com.comp304.dongheun_santiago_comp304sec002_lab03.util

import com.comp304.dongheun_santiago_comp304sec002_lab03.data.WeatherData
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.database.WeatherEntity
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.remote.WeatherResponse

fun WeatherData.toWeatherEntity() = WeatherEntity(
    cityName = cityName,
    lat = lat,
    lon = lon,
    country = country,
    state = state,
    temperature = temperature,
    feelsLike = feelsLike,
    weatherMain = weatherMain,
    description = description,
    humidity = humidity,
    windSpeed = windSpeed,
    isFavorite = isFavorite
)

fun WeatherEntity.toWeatherData() = WeatherData(
    cityName = cityName,
    lat = lat,
    lon = lon,
    country = country,
    state = state,
    temperature = temperature,
    feelsLike = feelsLike,
    weatherMain = weatherMain,
    description = description,
    humidity = humidity,
    windSpeed = windSpeed,
    isFavorite = isFavorite
)

fun WeatherResponse.toWeatherData() = WeatherData(
    cityName = name,
    lat = coord.lat,
    lon = coord.lon,
    temperature = main.temp,
    feelsLike = main.feelsLike,
    weatherMain = weather.firstOrNull()?.main ?: "",
    description = weather.firstOrNull()?.description ?: "",
    humidity = main.humidity,
    windSpeed = wind.speed
)