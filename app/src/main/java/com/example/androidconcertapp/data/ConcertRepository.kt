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

// write documentation

/**
 * A repository for [Concert]s. It can get [Concert]s from the backend and store them in a local database.
 * It can also get [Concert]s from the local database and update them to the backend.
 */
interface ConcertRepository {

    /**
     * Gets [Concert]s from the backend and compares them in the local database.
     * If there are any differences, the differing [Concert]s in the local database will be updated to the backend.
     */
    suspend fun updateConcertsToApi()

    /**
     * Inserts a [Concert] in the local database.
     * @param concert the [Concert] to be inserted.
     */
    suspend fun insertItem(concert: Concert)

    /**
     * Gets all [Concert]s from the local database.
     * @return a [Flow] of a [List] of [Concert]s.
     */
    fun getItems(): Flow<List<Concert>>

    /**
     * Gets a [Concert] from the local database by its id.
     * @param id the id of the [Concert] to be retrieved.
     * @return a [Flow] of a [Concert].
     */
    fun getItemById(id: Int): Flow<Concert>

    /**
     * Adds a comment to a [Concert] in the local database.
     * @param concert the [Concert] which contains the new comment.
     */
    suspend fun addComment(concert: Concert)

    /**
     * Refreshes the [Concert]s in the local database.
     */
    suspend fun refresh()

}

/**
 * A concrete implementation of [ConcertRepository].
 * @property concertDao the [ConcertDao] which is used to access the local database.
 * @property concertApiService the [ConcertApiService] which is used to access the backend.
 */
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
            Log.d("ConcertRepository", "Refresh will be called")
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
