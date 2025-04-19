package com.comp304.dongheun_santiago_comp304sec002_lab03.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.NetworkResult
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.WeatherData
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.WeatherRepository
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.asResult
import com.comp304.dongheun_santiago_comp304sec002_lab03.views.LocationsUIState
import com.comp304.dongheun_santiago_comp304sec002_lab03.views.WeatherUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val weatherState = MutableStateFlow(WeatherUIState())
    val locationsState = MutableStateFlow(LocationsUIState())

    private val _favoriteLocations = MutableStateFlow<List<WeatherData>>(emptyList())
    val favoriteLocations: StateFlow<List<WeatherData>> get() = _favoriteLocations

    fun resetFoundLocations() {
        locationsState.value = LocationsUIState()
    }

    init {
        getFavoriteLocations()
    }

    private fun updateWeatherData(weatherData: WeatherData) {
        viewModelScope.launch {
            weatherRepository.updateWeatherData(weatherData)
            val updatedData =
                weatherRepository.getWeatherByCoordFromDb(weatherData.lat, weatherData.lon)
            if (updatedData.firstOrNull() != null) {
                weatherState.update {
                    it.copy(isLoading = false, weather = updatedData.firstOrNull()!!)
                }
            }
        }
    }

    fun fetchWeatherByCoord(lat: Float, lon: Float) {
        weatherState.value = WeatherUIState(isLoading = true)
        viewModelScope.launch {
            when (val result = weatherRepository.getWeatherByCoord(lat, lon)) {
                is NetworkResult.Success -> {
                    val originalData = weatherRepository.getWeatherByCoordFromDb(lat, lon)
                    val updated = if (originalData.firstOrNull() != null)
                        originalData.firstOrNull()!!.copy(
                            cityName = result.data.cityName,
//                            lat = result.data.lat,
//                            lon = result.data.lon,
                            temperature = result.data.temperature,
                            feelsLike = result.data.feelsLike,
                            weatherMain = result.data.weatherMain,
                            description = result.data.description,
                            windSpeed = result.data.windSpeed,
                        )
                    else result.data
                    updateWeatherData(updated)
                }

                is NetworkResult.Error -> {
                    weatherState.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                }
            }
        }
    }

    fun fetchLocationDetails(lat: Float, lon: Float) {
        weatherState.value = WeatherUIState(isLoading = true)
        viewModelScope.launch {
            when (val result = weatherRepository.searchLocationByCoord(lat = lat, lon = lon)) {
                is NetworkResult.Success -> {
                    if (result.data.size > 0) {
                        val originalData = weatherRepository.getWeatherByCoordFromDb(lat, lon)
                        val updated = if (originalData.firstOrNull() != null)
                            originalData.firstOrNull()!!.copy(
                                country = result.data[0].country,
                                state = result.data[0].state
                            )
                        else WeatherData(
                            lat = lat,
                            lon = lon,
                            country = result.data[0].country,
                            state = result.data[0].state
                        )
                        updateWeatherData(updated)
                    }
                }

                is NetworkResult.Error -> {
                    weatherState.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                }
            }
        }
    }

    fun getFavoriteLocations() {
        viewModelScope.launch {
            weatherRepository.getFavoriteLocations().collect {
                _favoriteLocations.value = it
            }
        }
    }

    fun updateFavoriteStatus(isFavorite: Boolean) {
        viewModelScope.launch {
            weatherRepository.updateFavoriteStatus(weatherState.value.weather, isFavorite)
            val updatedData =
                weatherRepository.getWeatherByCoordFromDb(
                    weatherState.value.weather.lat,
                    weatherState.value.weather.lon
                )
            if (updatedData.firstOrNull() != null) {
                weatherState.value =
                    WeatherUIState(isLoading = false, weather = updatedData.firstOrNull()!!)
            }
        }
    }

    fun updateFavoriteStatus(weatherData: WeatherData, isFavorite: Boolean) {
        viewModelScope.launch {
            weatherRepository.updateFavoriteStatus(weatherData, isFavorite)
        }
    }

    fun searchLocations(cityName: String) {
        locationsState.value = LocationsUIState(isLoading = true)
        viewModelScope.launch {
            when (val result = weatherRepository.searchLocationsByCity(cityName)) {
                is NetworkResult.Success -> {
                    locationsState.update {
                        it.copy(isLoading = false, locations = result.data)
                    }
                }

                is NetworkResult.Error -> {
                    locationsState.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                }
            }
        }
    }
}