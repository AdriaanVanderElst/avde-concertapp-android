package com.example.androidconcertapp

import android.app.Application
import com.example.androidconcertapp.data.AppContainer
import com.example.androidconcertapp.data.DefaultAppContainer

/**
 * The main application class of the app.
 *
 * @property container The [AppContainer] used for dependency injection.
 */
class ConcertApplication : Application() {
    lateinit var container: AppContainer

    /**
     * Creates the [AppContainer].
     */
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(applicationContext)
    }
}
