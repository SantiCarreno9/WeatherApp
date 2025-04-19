package com.comp304.dongheun_santiago_comp304sec002_lab03.views

import com.comp304.dongheun_santiago_comp304sec002_lab03.data.WeatherData

data class WeatherUIState(
    val isLoading: Boolean = false,
    val weather: WeatherData = WeatherData(),
    val error: String? = null
)