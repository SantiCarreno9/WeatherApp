package com.comp304.dongheun_santiago_comp304sec002_lab03.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM Weather WHERE lat = :lat AND lon = :lon")
    fun getWeatherDataByCoords(lat: Float,lon:Float): Flow<WeatherEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherEntity: WeatherEntity)

    @Query("UPDATE Weather SET isFavorite =:isFavorite WHERE lat = :lat AND lon = :lon")
    suspend fun updateFavoriteStatus(lat: Float,lon: Float,isFavorite:Boolean)

    @Query("DELETE FROM Weather WHERE lat = :lat AND lon = :lon")
    suspend fun deleteWeatherData(lat: Float,lon:Float)

    @Query("SELECT * FROM Weather WHERE isFavorite = 1")
    fun getFavoriteLocations(): Flow<List<WeatherEntity>>

}