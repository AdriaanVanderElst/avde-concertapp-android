package com.example.androidconcertapp.network

import retrofit2.http.GET

interface ConcertApiService {
    @GET("concerts")
    suspend fun getConcerts(): ApiConcertResponse

    @GET("concerts/{id}")
    suspend fun getConcert(id: Int): ApiConcert
}
