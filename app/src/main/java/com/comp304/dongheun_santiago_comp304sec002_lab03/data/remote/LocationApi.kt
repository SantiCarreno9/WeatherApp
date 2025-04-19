package com.comp304.dongheun_santiago_comp304sec002_lab03.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {
    @GET("direct")
    suspend fun searchLocations(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("limit") limit: Int = 5
    ): Response<List<LocationResponse>>

    @GET("reverse")
    suspend fun searchLocations(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("appid") apiKey: String,
        @Query("limit") limit: Int = 5
    ): Response<List<LocationResponse>>

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/geo/1.0/"
        const val API_KEY = "77f98ea80d2eb92551e341a4e79c26d6"  // Replace with your actual API key
    }
}