package com.example.androidconcertapp.fake

import com.example.androidconcertapp.network.ApiConcertResponse
import com.example.androidconcertapp.network.ApiPutConcert
import com.example.androidconcertapp.network.ConcertApiService

class FakeConcertApiService : ConcertApiService {
    override suspend fun getConcerts(): ApiConcertResponse {
        return FakeDataSource.fakeDataResponse
    }

    override suspend fun updateConcert(id: Int, concert: ApiPutConcert) {
        TODO("Not yet implemented")
    }
}