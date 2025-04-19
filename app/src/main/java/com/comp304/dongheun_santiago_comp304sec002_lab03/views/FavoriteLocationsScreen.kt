package com.comp304.dongheun_santiago_comp304sec002_lab03.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.WeatherData
import com.comp304.dongheun_santiago_comp304sec002_lab03.viewmodel.WeatherViewModel

@Composable
fun FavoriteLocationsScreen(
    weatherViewModel: WeatherViewModel,
    onLocationClicked: (Float, Float) -> Unit
) {
    val favoriteLocations = weatherViewModel.favoriteLocations.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Text(
            text = "My Favorite Locations",
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn {
            if (favoriteLocations.value.size == 0) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "You don't have any favorites",
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                        )
                    }
                }
            } else {
                items(favoriteLocations.value) { item ->
                    FavoriteLocationItem(
                        locationWeather = item,
                        onLocationClicked = onLocationClicked,
                        onFavoriteValueChanged = {
                            weatherViewModel.updateFavoriteStatus(item,it)
                            weatherViewModel.getFavoriteLocations()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteLocationItem(
    locationWeather: WeatherData,
    onLocationClicked: (Float, Float) -> Unit,
    onFavoriteValueChanged: (Boolean) -> Unit
) {
    var isFavoriteState by remember { mutableStateOf(locationWeather.isFavorite) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { onLocationClicked(locationWeather.lat, locationWeather.lon) }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(15.dp)
        ) {
            Column {
                Text(
                    text = locationWeather.cityName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )
                Text(
                    text = "${locationWeather.state},${locationWeather.country}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth(0.95f)
            ) {
                WeatherIcon(weatherMain = locationWeather.weatherMain,40)
                Text(
                    text = "${locationWeather.temperature.toInt()}°C",
                    fontSize = 40.sp,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )
                IconButton(
                    onClick = {
                        isFavoriteState = !isFavoriteState
                        onFavoriteValueChanged(isFavoriteState)
                    }
                ) {
                    Icon(
                        imageVector = if (isFavoriteState) {
                            androidx.compose.material.icons.Icons.Filled.Favorite
                        } else {
                            androidx.compose.material.icons.Icons.Outlined.FavoriteBorder
                        },
                        contentDescription = "Favorite",
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            }
        }
    }
}