package com.example.androidconcertapp.data

import android.content.Context
import com.example.androidconcertapp.data.database.ConcertDatabase
import com.example.androidconcertapp.network.ConcertApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer {
    val concertRepository: ConcertRepository
}

class DefaultAppContainer(private val appContext: Context) : AppContainer {
    private val baseUrl = "http://10.0.2.2:9000/api/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType()),
        )
        .baseUrl(baseUrl)
        .build()

    private val concertApiService: ConcertApiService = retrofit.create(ConcertApiService::class.java)

    override val concertRepository: ConcertRepository by lazy {
//        ApiConcertRepository(concertApiService)
        CachingConcertRepository(ConcertDatabase.getDatabase(appContext = appContext).concertDao(), concertApiService)
    }

    // add bearer token of user to each request
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val sharedPrefs = appContext.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE)
        val bearerToken = sharedPrefs.getString("bearerToken", "")
        val newRequest = originalRequest.newBuilder().header("Authorization", "Bearer $bearerToken").build()
        chain.proceed(newRequest)
    }
    private fun getSafeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }
}
