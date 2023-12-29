package com.example.androidconcertapp.network

import kotlinx.coroutines.flow.flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ConcertApiService {
    @GET("concerts")
    suspend fun getConcerts(): ApiConcertResponse

    @PUT("concerts/{id}")
    suspend fun updateConcert(@Path("id") id: Int, @Body concert: ApiPutConcert)
}

fun ConcertApiService.getConcertsAsFlow() = flow { emit(getConcerts()) }
