package com.example.androidconcertapp.fake

import com.example.androidconcertapp.data.database.ConcertDao
import com.example.androidconcertapp.data.database.ConcertEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeConcertDao : ConcertDao {
    override suspend fun insertItem(concert: ConcertEntity) {
        TODO("Not yet implemented")
    }

    override fun getItems(): Flow<List<ConcertEntity>> {
        TODO("Not yet implemented")
    }

    override fun getItemById(id: Int): Flow<ConcertEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun updateItem(concert: ConcertEntity) {
        TODO("Not yet implemented")
    }

}
