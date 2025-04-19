package com.comp304.dongheun_santiago_comp304sec002_lab03.views

import com.comp304.dongheun_santiago_comp304sec002_lab03.data.WeatherData
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.remote.LocationResponse

data class LocationsUIState(
    val isLoading: Boolean = false,
    val locations: List<LocationResponse> = emptyList(),
    val error: String? = null
)