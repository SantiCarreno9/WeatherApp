package com.comp304.dongheun_santiago_comp304sec002_lab03.navigation

sealed class Screens(val route: String) {
    object WeatherScreen : Screens("weather")
    object SearchScreen : Screens("search")
    object FavoriteLocations : Screens("favoriteLocations")
}