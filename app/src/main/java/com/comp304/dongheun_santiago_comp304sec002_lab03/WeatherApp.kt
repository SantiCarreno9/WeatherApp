package com.comp304.dongheun_santiago_comp304sec002_lab03

import android.app.Application
import com.comp304.dongheun_santiago_comp304sec002_lab03.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModules)
        }
    }
}