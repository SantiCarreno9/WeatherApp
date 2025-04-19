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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.remote.LocationResponse
import com.comp304.dongheun_santiago_comp304sec002_lab03.viewmodel.WeatherViewModel

@Composable
fun SearchLocationScreen(
    weatherViewModel: WeatherViewModel,
    onBackButtonClicked: () -> Unit,
    onLocationClicked: (Float, Float) -> Unit
) {
    val locations = weatherViewModel.locationsState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopSearchBar(
            onBackButtonClicked = {
                onBackButtonClicked()
                if (locations.value.locations.size != 0)
                    weatherViewModel.resetFoundLocations()
            },
            onSearchButtonClicked = {
                weatherViewModel.searchLocations(it)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn {
            if (locations.value.locations.size == 0) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "No matches found",
                            fontSize = 20.sp,
                        )
                    }
                }
            } else {
                items(locations.value.locations) { item ->
                    LocationItem(
                        locationData = item,
                        onClicked = {
                            onLocationClicked(item.lat, item.lon)
                            weatherViewModel.resetFoundLocations()
                        }
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun TopSearchBar(
    onBackButtonClicked: () -> Unit,
    onSearchButtonClicked: (String) -> Unit
) {
    var search by remember { mutableStateOf(TextFieldValue("")) };
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackButtonClicked
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack, "Go Back button",
                modifier = Modifier
                    .size(35.dp)
            )
        }
        TextField(
            value = search,
            onValueChange = {
                search = it
            },
            textStyle = TextStyle(
                fontSize = 20.sp
            ),
            singleLine = true,
            placeholder = { Text(text = "Location", fontSize = 20.sp) },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .fillMaxWidth(0.9f)
        )
        IconButton(
            onClick = {
                onSearchButtonClicked(search.text)
            }
        ) {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Outlined.Search,
                contentDescription = "Search",
                modifier = Modifier
                    .size(35.dp)
            )
        }
    }
}

@Composable
fun LocationItem(
    locationData: LocationResponse,
    onClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { onClicked() }
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text = locationData.name,
                fontSize = 30.sp,
                modifier = Modifier
            )
            Text(
                text = "${locationData.state}, ${locationData.country}",
                fontSize = 20.sp,
                modifier = Modifier
            )
        }
    }
}