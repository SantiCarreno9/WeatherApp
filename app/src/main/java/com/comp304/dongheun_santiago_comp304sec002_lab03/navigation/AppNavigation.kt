package com.comp304.dongheun_santiago_comp304sec002_lab03.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.remote.Coord
import com.comp304.dongheun_santiago_comp304sec002_lab03.viewmodel.WeatherViewModel
import com.comp304.dongheun_santiago_comp304sec002_lab03.views.FavoriteLocationsScreen
import com.comp304.dongheun_santiago_comp304sec002_lab03.views.SearchLocationScreen
import com.comp304.dongheun_santiago_comp304sec002_lab03.views.WeatherScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    onScreenChanged: (String) -> Unit
) {
    val weatherViewModel: WeatherViewModel = koinViewModel()
    val lat = remember {
        mutableFloatStateOf(43.65439987182617f)
    }
    val lon = remember {
        mutableFloatStateOf(-79.38069915771484f)
    }
    NavHost(
        navController = navHostController,
        startDestination = "${Screens.WeatherScreen.route}?lat=${lat.floatValue}&lon=${lon.floatValue}"
    ) {
        composable("${Screens.WeatherScreen.route}?lat={lat}&lon={lon}",
            arguments = listOf(
                navArgument("lat") {
                    defaultValue = lat.floatValue
                    type = NavType.FloatType
                },
                navArgument("lon") {
                    defaultValue = lon.floatValue
                    type = NavType.FloatType
                }
            )) {
            lat.floatValue = it.arguments?.getFloat("lat") ?: lat.floatValue
            lon.floatValue = it.arguments?.getFloat("lon") ?: lon.floatValue

            WeatherScreen(
                lat = lat.floatValue,
                lon = lon.floatValue,
                weatherViewModel = weatherViewModel,
                onSearchClicked = {
                    navHostController.navigate(
                        Screens.SearchScreen.route
                    )
                }
            )
        }
        composable(Screens.FavoriteLocations.route) {
            LaunchedEffect(Unit) {
                weatherViewModel.getFavoriteLocations()
            }
            FavoriteLocationsScreen(
                weatherViewModel = weatherViewModel,
                onLocationClicked = { lat, lon ->
                    navHostController.navigate(
                        "${Screens.WeatherScreen.route}?lat=${lat}&lon=${lon}"
                    )
                    onScreenChanged(Screens.WeatherScreen.route)
                }
            )
        }
        composable(Screens.SearchScreen.route) {
            SearchLocationScreen(
                weatherViewModel = weatherViewModel,
                onBackButtonClicked = {
                    navHostController.navigate(Screens.WeatherScreen.route)
                },
                onLocationClicked = { lat, lon ->
                    navHostController.navigate(
                        "${Screens.WeatherScreen.route}?lat=${lat}&lon=${lon}"
                    )
                    onScreenChanged(Screens.WeatherScreen.route)
                }
            )
        }
    }
}