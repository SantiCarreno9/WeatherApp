package com.comp304.dongheun_santiago_comp304sec002_lab03.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.comp304.dongheun_santiago_comp304sec002_lab03.navigation.Screens

@Composable
fun BottomNavigationBar(
    currentScreen: String,
    onFavoriteClicked: () -> Unit,
    onHomeClicked: () -> Unit
){
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        NavigationBarItem(
            selected = currentScreen == Screens.WeatherScreen.route,
            onClick = {
                onHomeClicked()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home Icon"
                )
            }
        )

        NavigationBarItem(
            selected = currentScreen == Screens.FavoriteLocations.route,
            onClick = {
                onFavoriteClicked()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite Icon"
                )
            }
        )
    }
}