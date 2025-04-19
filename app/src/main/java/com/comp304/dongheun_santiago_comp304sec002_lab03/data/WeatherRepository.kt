package com.comp304.dongheun_santiago_comp304sec002_lab03.data

import com.comp304.dongheun_santiago_comp304sec002_lab03.data.remote.Coord
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.remote.LocationResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherByCoord(lat: Float, lon: Float): NetworkResult<WeatherData>
    suspend fun getWeatherByCoordFromDb(lat: Float, lon: Float): Flow<WeatherData?>
    suspend fun updateWeatherData(weatherData: WeatherData)
    suspend fun updateFavoriteStatus(weatherData: WeatherData,isFavorite:Boolean)
    suspend fun getFavoriteLocations(): Flow<List<WeatherData>>
    suspend fun searchLocationsByCity(cityName: String): NetworkResult<List<LocationResponse>>
    suspend fun searchLocationByCoord(lat: Float, lon: Float): NetworkResult<List<LocationResponse>>
}