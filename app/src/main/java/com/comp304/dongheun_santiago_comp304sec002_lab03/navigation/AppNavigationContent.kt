package com.comp304.dongheun_santiago_comp304sec002_lab03.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.comp304.dongheun_santiago_comp304sec002_lab03.viewmodel.WeatherViewModel
import com.comp304.dongheun_santiago_comp304sec002_lab03.views.BottomNavigationBar
import com.comp304.dongheun_santiago_comp304sec002_lab03.views.WeatherNavigationRail

@Composable
fun AppNavigationContent(
    currentScreen: String,
    onScreenChanged: (String) -> Unit,
    navigationType: NavigationType
) {
    val screen = remember {
        mutableStateOf(currentScreen)
    }
    val navController = rememberNavController()
    Row(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        AnimatedVisibility(
            visible = navigationType == NavigationType.NavigationRail
        ) {
            WeatherNavigationRail(
                currentScreen = screen.value,
                onFavoriteClicked = {
                    screen.value=Screens.FavoriteLocations.route
                    onScreenChanged(Screens.FavoriteLocations.route)
                    navController.navigate(Screens.FavoriteLocations.route)
                },
                onHomeClicked = {
                    screen.value=Screens.WeatherScreen.route
                    onScreenChanged(Screens.WeatherScreen.route)
                    navController.navigate(Screens.WeatherScreen.route)
                }
            )
        }
        Scaffold(
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    AppNavigation(
//                        weatherViewModel=weatherViewModel,
                        navHostController = navController,
                        onScreenChanged = {
                            screen.value=it
                            onScreenChanged(it)
                        }
                    )
                }
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = navigationType == NavigationType.BottomNavigation
                ) {
                    BottomNavigationBar(
                        currentScreen = screen.value,
                        onFavoriteClicked = {
                            screen.value=Screens.FavoriteLocations.route
                            onScreenChanged(Screens.FavoriteLocations.route)
                            navController.navigate(Screens.FavoriteLocations.route)
                        },
                        onHomeClicked = {
                            screen.value=Screens.WeatherScreen.route
                            onScreenChanged(Screens.WeatherScreen.route)
                            navController.navigate(Screens.WeatherScreen.route)
                        }
                    )
                }
            }
        )
    }
}