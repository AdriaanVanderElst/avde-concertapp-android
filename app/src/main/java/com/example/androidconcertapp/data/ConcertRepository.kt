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
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import okio.IOException
import retrofit2.HttpException

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
        val apiConcerts: List<Concert> = concertApiService.getConcerts().concerts.asDomainObjects()

        Log.d("ConcertRepository", "Comparing Concerts")

        for (concert in apiConcerts) {
            val corresponding: Concert = concertDao.getItemById(concert.id).map { it.asDomainConcert() }.first()
            if (corresponding != null && concert.comment != corresponding.comment) {
                Log.d("ConcertRepository", corresponding.comment + " != " + concert.comment)
                concertApiService.updateConcert(corresponding.id, corresponding.asApiPutConcert())
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
            Log.e("ConcertRepository_HTTP", "refresh: ${e.message()}")
        } catch (e: IOException) {
            Log.e("ConcertRepository_IO", "refresh: ${e.message}")
        } catch (e: Exception) {
            Log.e("ConcertRepository_EX", "refresh: ${e.message}")
        }
    }
}
