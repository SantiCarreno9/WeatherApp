package com.comp304.dongheun_santiago_comp304sec002_lab03.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    val name: String,
    val lat: Float,
    val lon: Float,
    val country: String,
    val state: String
)