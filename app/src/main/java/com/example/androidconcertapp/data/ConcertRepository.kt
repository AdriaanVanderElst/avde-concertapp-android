package com.example.androidconcertapp.data

import com.example.androidconcertapp.model.Concert
import com.example.androidconcertapp.network.ConcertApiService
import com.example.androidconcertapp.network.asDomainObject
import com.example.androidconcertapp.network.asDomainObjects

interface ConcertRepository {
    suspend fun getConcerts(): List<Concert>
    suspend fun getConcertById(id: Int): Concert
}

class ApiConcertRepository(
    private val concertApiService: ConcertApiService,
) : ConcertRepository {
    override suspend fun getConcerts(): List<Concert> {
        val apiConcertResponse = concertApiService.getConcerts()
        return apiConcertResponse.concerts.asDomainObjects()
    }

    override suspend fun getConcertById(id: Int): Concert {
        val apiConcert = concertApiService.getConcert(id)
        return apiConcert.asDomainObject()
    }
}
