package com.comp304.dongheun_santiago_comp304sec002_lab03.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WeatherEntity::class],
    version = 2
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao;

    companion object {
        const val DATABASE_NAME = "Weather_Db"
        const val WEATHER_TABLE_NAME = "Weather"
    }
}