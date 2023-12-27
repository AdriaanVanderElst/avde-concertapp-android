package com.example.androidconcertapp.network

import kotlinx.coroutines.flow.flow
import retrofit2.http.GET

interface ConcertApiService {
    @GET("concerts")
    suspend fun getConcerts(): ApiConcertResponse
}

fun ConcertApiService.getConcertsAsFlow() = flow { emit(getConcerts()) }
