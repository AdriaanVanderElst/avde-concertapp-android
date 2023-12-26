package com.example.androidconcertapp.data

import com.example.androidconcertapp.network.ConcertApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val concertRepository: ConcertRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:9000/api/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType()),
        )
        .baseUrl(baseUrl)
        .build()

    val concertApiService: ConcertApiService = retrofit.create(ConcertApiService::class.java)

    override val concertRepository: ConcertRepository by lazy {
        ApiConcertRepository(concertApiService)
    }
}
