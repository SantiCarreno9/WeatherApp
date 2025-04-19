package com.comp304.dongheun_santiago_comp304sec002_lab03.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.remote.LocationResponse
import com.comp304.dongheun_santiago_comp304sec002_lab03.viewmodel.WeatherViewModel

import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material.icons.outlined.Thunderstorm
import androidx.compose.material.icons.outlined.Grain
import androidx.compose.material.icons.outlined.CloudQueue

@Composable
fun WeatherScreen(
    lat:Float,
    lon:Float,
    weatherViewModel: WeatherViewModel,
    onSearchClicked: () -> Unit
) {
    val weatherState = weatherViewModel.weatherState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        weatherViewModel.fetchWeatherByCoord(lat,lon)
        weatherViewModel.fetchLocationDetails(lat,lon)
    }
    if(weatherState.value.weather.cityName.equals("")){
        weatherViewModel.fetchWeatherByCoord(lat,lon)
        weatherViewModel.fetchLocationDetails(lat,lon)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TopBar(
            weatherData = weatherState.value.weather,
            onSearchButtonClicked = onSearchClicked,
            onRefreshButtonClicked = {
                weatherViewModel.fetchWeatherByCoord(lat,lon)
                weatherViewModel.fetchLocationDetails(lat,lon)
            }
        )
        if (weatherState.value.isLoading) {
            CircularProgressIndicator()
        } else {
            MainWeatherInfo(
                weatherData = weatherState.value.weather,
                onFavoriteValueChanged = { isFavorite ->
                    weatherViewModel.updateFavoriteStatus(isFavorite)
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            AdditionalWeatherInfo(weatherData = weatherState.value.weather)
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun TopBar(
    weatherData: WeatherData,
    onSearchButtonClicked: () -> Unit,
    onRefreshButtonClicked: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(80.dp)
    ) {
        IconButton(
            onClick = onRefreshButtonClicked,
        ) {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Outlined.Refresh,
                contentDescription = "Refresh",
                modifier = Modifier
                    .size(40.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.9f)
        ) {
            Text(
                text = weatherData.cityName,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "${weatherData.state},${weatherData.country}",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
        }
        IconButton(
            onClick = onSearchButtonClicked,
        ) {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Outlined.Search,
                contentDescription = "Search",
                modifier = Modifier
                    .size(40.dp)
            )
        }
    }
}

@Composable
fun WeatherIcon(weatherMain: String,size:Int=64) {
    val icon = when (weatherMain.lowercase()) {
        "clear" -> Icons.Outlined.WbSunny
        "clouds" -> Icons.Outlined.Cloud
        "rain" -> Icons.Outlined.WaterDrop
        "snow" -> Icons.Outlined.AcUnit
        "thunderstorm" -> Icons.Outlined.Thunderstorm
        "drizzle" -> Icons.Outlined.Grain
        "mist", "fog", "haze" -> Icons.Outlined.CloudQueue
        else -> Icons.Outlined.Cloud
    }

    Icon(
        imageVector = icon,
        contentDescription = "Weather icon for $weatherMain",
        modifier = Modifier.size(size.dp)
    )
}

@Composable
fun MainWeatherInfo(
    weatherData: WeatherData,
    onFavoriteValueChanged: (Boolean) -> Unit
) {
    var isFavoriteState by remember { mutableStateOf(weatherData.isFavorite) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {
                isFavoriteState = !isFavoriteState
                onFavoriteValueChanged(isFavoriteState)
            }) {
                Icon(
                    imageVector = if (isFavoriteState) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentDescription = "Favorite",
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        WeatherIcon(weatherData.weatherMain)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = weatherData.temperature.toInt().toString(),
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "°c",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = weatherData.weatherMain,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Feels like ${weatherData.feelsLike.toInt()}°",
            fontSize = 20.sp,
        )
    }
}

@Composable
fun AdditionalWeatherInfo(
    weatherData: WeatherData
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        AdditionalInfoCard(
            title = "Wind",
            value = weatherData.windSpeed.toString(),
            extra = "m/s"
        )
        Spacer(modifier = Modifier.width(10.dp))
        AdditionalInfoCard(
            title = "Humidity",
            value = weatherData.humidity.toString(),
            extra = "%"
        )
    }
}

@Composable
fun AdditionalInfoCard(
    title: String,
    value: String,
    extra: String
) {
    Card(
        modifier = Modifier
            .fillMaxHeight()
            .width(120.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = value,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                )
                Text(
                    text = extra,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                )
            }
        }
    }
}

