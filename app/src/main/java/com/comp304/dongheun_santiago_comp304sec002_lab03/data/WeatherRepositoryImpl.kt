package com.comp304.dongheun_santiago_comp304sec002_lab03.data

import com.comp304.dongheun_santiago_comp304sec002_lab03.data.database.WeatherDao
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.database.WeatherDatabase
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.remote.Coord
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.remote.LocationApi
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.remote.LocationResponse
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.remote.WeatherApi
import com.comp304.dongheun_santiago_comp304sec002_lab03.util.toWeatherData
import com.comp304.dongheun_santiago_comp304sec002_lab03.util.toWeatherEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val locationApi: LocationApi,
    private val dispatcher: CoroutineDispatcher,
    private val weatherDao: WeatherDao,
) : WeatherRepository {

    override suspend fun getWeatherByCoord(lat: Float, lon: Float): NetworkResult<WeatherData> {
        return withContext(dispatcher) {
            try {
                val response = weatherApi.fetchWeatherData(lat = lat, lon = lon, WeatherApi.API_KEY)
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!.toWeatherData())
                } else {
                    NetworkResult.Error(response.errorBody().toString())
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    override suspend fun getWeatherByCoordFromDb(lat: Float, lon: Float): Flow<WeatherData?> {
        return withContext(dispatcher) {
            weatherDao.getWeatherDataByCoords(lat, lon).map {
                it?.toWeatherData()
            }
        }
    }

    override suspend fun updateWeatherData(weatherData: WeatherData) {
        return withContext(dispatcher) {
            val previous =
                weatherDao.getWeatherDataByCoords(weatherData.lat, weatherData.lon).firstOrNull()
            if (previous != null)
                weatherData.isFavorite = previous.isFavorite
            weatherDao.insertWeatherData(weatherData.toWeatherEntity())
        }
    }

    override suspend fun updateFavoriteStatus(weatherData: WeatherData, isFavorite: Boolean) {
        withContext(dispatcher) {
            weatherDao.updateFavoriteStatus(weatherData.lat, weatherData.lon, isFavorite)
        }
    }

    override suspend fun getFavoriteLocations(): Flow<List<WeatherData>> {
        return withContext(dispatcher) {
            weatherDao.getFavoriteLocations().map { locations ->
                locations.map { weatherEntity ->
                    weatherEntity.toWeatherData()
                }
            }
        }
    }

    override suspend fun searchLocationsByCity(cityName: String): NetworkResult<List<LocationResponse>> {
        return withContext(dispatcher) {
            try {
                val response = locationApi.searchLocations(cityName, LocationApi.API_KEY)
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(response.errorBody().toString())
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    override suspend fun searchLocationByCoord(
        lat: Float,
        lon: Float
    ): NetworkResult<List<LocationResponse>> {
        return withContext(dispatcher) {
            try {
                val response =
                    locationApi.searchLocations(lat = lat, lon = lon, LocationApi.API_KEY)
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(response.errorBody().toString())
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }

}