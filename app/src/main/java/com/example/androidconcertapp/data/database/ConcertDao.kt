package com.example.androidconcertapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ConcertDao {
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertItem(concert: ConcertEntity)

    @Query("SELECT * FROM concerts ORDER BY date ASC")
    fun getItems(): Flow<List<ConcertEntity>>

    @Query("SELECT * FROM concerts WHERE id = :id")
    fun getItemById(id: Int): Flow<ConcertEntity>

    @Update(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun updateItem(concert: ConcertEntity)
}
