package com.example.androidconcertapp.fake

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidconcertapp.data.ConcertRepository
import com.example.androidconcertapp.model.Concert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeConcertRepository() : ConcertRepository {

    private val fakeConcerts = listOf(
        Concert(
            1,
            "Concert 1",
            "2024-01-01",
            "19:00",
            1000,
            true,
            1,
            "Teststraat 10",
            "Teststad",
            1,
            "TestOrganisator 1",
            "0471649785",
            "test@test.org",
            "Testuser",
            "This is a comment",
        ),
        Concert(
            2,
            "Concert 2",
            "2024-01-10",
            "19:30",
            1400,
            false,
            2,
            "Teststraat 50",
            "Teststad 2",
            2,
            "TestOrganisator 2",
            "0471641945",
            "testtwee@test.org",
            "Testuser 2",
            "This is another comment",
        )
    )

    override suspend fun updateConcertsToApi() {
        // do nothing
    }

    override suspend fun insertItem(concert: Concert) {
        // do nothing
    }

    override fun getItems(): Flow<List<Concert>> {
        return flow { emit(fakeConcerts) }
    }

    override fun getItemById(id: Int): Flow<Concert> {
        return flow { fakeConcerts.find { it.id == id }?.let { emit(it) } }
    }

    override suspend fun addComment(concert: Concert) {
        // do nothing
    }

    override suspend fun refresh() {
        // do nothing
    }

}