package com.example.androidconcertapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/** ConcertDao is a Data Access Object for the concert table. */
@Dao
interface ConcertDao {

    /** Inserts a [ConcertEntity] in the table. If the concert already exists, replace it.
     * @param concert the [ConcertEntity] to insert.
     */
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertItem(concert: ConcertEntity)

    /** Gets all the concerts from the table, sorted by date.
     * @return all the concerts from the table as a flow, sorted by date.
     */
    @Query("SELECT * FROM concerts ORDER BY date ASC")
    fun getItems(): Flow<List<ConcertEntity>>

    /** Gets the concert with the given id from the table.
     * @param id the id of the concert.
     * @return the concert with the given id from the table as a flow.
     */
    @Query("SELECT * FROM concerts WHERE id = :id")
    fun getItemById(id: Int): Flow<ConcertEntity>

    /** Updates a [ConcertEntity] in the table.
     * @param concert the [ConcertEntity] to update.
     */
    @Update(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun updateItem(concert: ConcertEntity)
}
