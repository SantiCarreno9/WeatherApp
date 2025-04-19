package com.comp304.dongheun_santiago_comp304sec002_lab03.di

import androidx.room.Room
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.WeatherRepository
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.WeatherRepositoryImpl
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.database.WeatherDatabase
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.remote.LocationApi
import com.comp304.dongheun_santiago_comp304sec002_lab03.data.remote.WeatherApi
import com.comp304.dongheun_santiago_comp304sec002_lab03.viewmodel.WeatherViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

val appModules = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get(), get(), get()) }
    single { Dispatchers.IO }
    single { WeatherViewModel(get()) }
    val weatherRetrofit = named("WeatherRetrofit")
    val locationRetrofit = named("LocationRetrofit")
    single(weatherRetrofit) {
        Retrofit.Builder()
            .addConverterFactory(
                json.asConverterFactory(contentType = "application/json".toMediaType())
            )
            .baseUrl(WeatherApi.BASE_URL)
            .build()
    }
    single { get<Retrofit>(weatherRetrofit).create(WeatherApi::class.java) }
    single(locationRetrofit) {
        Retrofit.Builder()
            .addConverterFactory(
                json.asConverterFactory(contentType = "application/json".toMediaType())
            )
            .baseUrl(LocationApi.BASE_URL)
            .build()
    }
    single { get<Retrofit>(locationRetrofit).create(LocationApi::class.java) }

    single {
        Room.databaseBuilder(
            androidContext(),
            WeatherDatabase::class.java,
            WeatherDatabase.DATABASE_NAME
        ).build()
    }
    single { get<WeatherDatabase>().weatherDao() }
}