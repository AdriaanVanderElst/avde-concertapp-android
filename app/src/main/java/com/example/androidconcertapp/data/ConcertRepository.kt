package com.example.androidconcertapp.data

import android.util.Log
import com.example.androidconcertapp.data.database.ConcertDao
import com.example.androidconcertapp.data.database.asDbConcert
import com.example.androidconcertapp.data.database.asDomainConcert
import com.example.androidconcertapp.data.database.asDomainConcerts
import com.example.androidconcertapp.model.Concert
import com.example.androidconcertapp.network.ConcertApiService
import com.example.androidconcertapp.network.asApiPutConcert
import com.example.androidconcertapp.network.asDomainObjects
import com.example.androidconcertapp.network.getConcertsAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okio.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException

interface ConcertRepository {

    // Retrofit
//    suspend fun getConcertsFromApi(): List<Concert>

    suspend fun updateConcertsToApi()

    // RoomDB
    suspend fun insertItem(concert: Concert)

    fun getItems(): Flow<List<Concert>>

    fun getItemById(id: Int): Flow<Concert>

    suspend fun addComment(concert: Concert)

    suspend fun refresh()

}

class CachingConcertRepository(
    private val concertDao: ConcertDao,
    private val concertApiService: ConcertApiService,
) : ConcertRepository {
//    override suspend fun getConcertsFromApi(): List<Concert> {
//        val apiConcertResponse = concertApiService.getConcerts()
//        return apiConcertResponse.concerts.asDomainObjects()
//    }

    override suspend fun updateConcertsToApi() {
        var apiConcerts: List<Concert> = emptyList()
        try {
            apiConcerts = concertApiService.getConcerts().concerts.asDomainObjects()
        } catch (e: SocketTimeoutException) {
            Log.e("ConcertRepository", "Backend connection failed: ${e.message}")
        } catch (e: Exception) {
            Log.e("ConcertRepository", "updateConcertsToApi: ${e.message}")
        }

        Log.d("ConcertRepository", "Comparing Concerts")

        for (concert in apiConcerts) {
            val corresponding: Concert =
                concertDao.getItemById(concert.id).map { it.asDomainConcert() }.first()
            if (apiConcerts.isNotEmpty() && concert.comment != corresponding.comment) {
                try {
                    concertApiService.updateConcert(
                        corresponding.id, corresponding.asApiPutConcert()
                    )
                } catch (e: SocketTimeoutException) {
                    Log.e("ConcertRepository", "Backend connection failed: ${e.message}")
                } catch (e: Exception) {
                    Log.e("ConcertRepository", "updateConcertsToApi: ${e.message}")
                }
            }
        }
    }

    override suspend fun insertItem(concert: Concert) {
        concertDao.insertItem(concert.asDbConcert())
    }

    override fun getItems(): Flow<List<Concert>> {
        return concertDao.getItems().map {
            it.asDomainConcerts()
        }
    }

    override fun getItemById(id: Int): Flow<Concert> {
        return concertDao.getItemById(id).map {
            it.asDomainConcert()
        }
    }

    override suspend fun addComment(concert: Concert) {
        concertDao.updateItem(concert.asDbConcert())
    }

    override suspend fun refresh() {
        try {
            Log.e("ConcertRepository", "Refresh will be called")
            concertApiService.getConcertsAsFlow().collect { response ->
                response.let {
                    for (concert in it.concerts.asDomainObjects()) {
                        Log.i("ConcertRepository", "refresh: $concert")
                        insertItem(concert)
                    }
                }
            }
        } catch (e: HttpException) {
            Log.e("ConcertRepository", "refresh: ${e.message()}")
        } catch (e: IOException) {
            Log.e("ConcertRepository", "refresh: ${e.message}")
        } catch (e: SocketTimeoutException) {
            Log.e("ConcertRepository", "Backend connection failed: ${e.message}")
        } catch (e: Exception) {
            Log.e("ConcertRepository_EX", "refresh: ${e.message}")
        }
    }
}
