package com.example.androidconcertapp.data

import android.util.Log
import com.example.androidconcertapp.data.database.ConcertDao
import com.example.androidconcertapp.data.database.asDbConcert
import com.example.androidconcertapp.data.database.asDomainConcert
import com.example.androidconcertapp.data.database.asDomainConcerts
import com.example.androidconcertapp.model.Concert
import com.example.androidconcertapp.network.ConcertApiService
import com.example.androidconcertapp.network.asDomainObjects
import com.example.androidconcertapp.network.getConcertsAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface ConcertRepository {

    // Retrofit
    suspend fun getConcertsFromApi(): List<Concert>

    // RoomDB
    suspend fun insertItem(concert: Concert)

    fun getItems(): Flow<List<Concert>>

    fun getItemById(id: Int): Flow<Concert>

    suspend fun refresh()
}

class CachingConcertRepository(
    private val concertDao: ConcertDao,
    private val concertApiService: ConcertApiService,
) : ConcertRepository {
    override suspend fun getConcertsFromApi(): List<Concert> {
        val apiConcertResponse = concertApiService.getConcerts()
        return apiConcertResponse.concerts.asDomainObjects()
    }

    override suspend fun insertItem(concert: Concert) {
        concertDao.insertItem(concert.asDbConcert())
    }

    override fun getItems(): Flow<List<Concert>> {
        return concertDao.getItems().map {
            it.asDomainConcerts()
        }.onEach {
            if (it.isEmpty()) {
//                refresh()
            }
        }
    }

    override fun getItemById(id: Int): Flow<Concert> {
        return concertDao.getItemById(id).map {
            it.asDomainConcert()
        }
    }

    override suspend fun refresh() {
        concertApiService.getConcertsAsFlow().collect {
            for (concert in it.concerts.asDomainObjects()) {
                Log.i("ConcertRepository", "refresh: $concert")
                insertItem(concert)
            }
        }
    }
}
