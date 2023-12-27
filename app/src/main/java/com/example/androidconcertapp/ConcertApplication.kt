package com.example.androidconcertapp

import android.app.Application
import com.example.androidconcertapp.data.AppContainer
import com.example.androidconcertapp.data.DefaultAppContainer

class ConcertApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(applicationContext)
    }
}
