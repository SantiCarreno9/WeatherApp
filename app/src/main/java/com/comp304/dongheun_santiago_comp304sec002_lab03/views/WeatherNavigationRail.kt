package com.comp304.dongheun_santiago_comp304sec002_lab03.views

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.comp304.dongheun_santiago_comp304sec002_lab03.navigation.Screens

@Composable
fun WeatherNavigationRail(
    currentScreen:String,
    onFavoriteClicked: () -> Unit,
    onHomeClicked: () -> Unit,
) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
        modifier = Modifier
            .fillMaxHeight()
    ) {
        NavigationRailItem(
            selected = currentScreen == Screens.WeatherScreen.route,
            onClick = onHomeClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home Icon"
                )
            }
        )

        NavigationRailItem(
            selected = currentScreen == Screens.FavoriteLocations.route,
            onClick = onFavoriteClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite Icon"
                )
            }
        )
    }
}