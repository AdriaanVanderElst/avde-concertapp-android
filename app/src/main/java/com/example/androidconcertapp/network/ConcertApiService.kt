package com.example.androidconcertapp.network

import kotlinx.coroutines.flow.flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

/** A service for concerts. */
interface ConcertApiService {

    /** Gets all the concerts from the server.
     * @return all the concerts from the server as an [ApiConcertResponse].
     */
    @GET("concerts")
    suspend fun getConcerts(): ApiConcertResponse

    /** Updates a concert on the server.
     * @param id the id of the concert to update.
     * @param concert the [ApiPutConcert] to update.
     */
    @PUT("concerts/{id}")
    suspend fun updateConcert(@Path("id") id: Int, @Body concert: ApiPutConcert)
}

/** Extension function to get all the concerts from the server as a flow. */
fun ConcertApiService.getConcertsAsFlow() = flow { emit(getConcerts()) }
