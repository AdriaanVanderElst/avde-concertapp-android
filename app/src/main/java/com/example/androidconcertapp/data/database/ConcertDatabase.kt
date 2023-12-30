package com.example.androidconcertapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/** ConcertDatabase is a Room database that contains the concert table. */
@Database(entities = [ConcertEntity::class], version = 5)
abstract class ConcertDatabase : RoomDatabase() {

    /** Returns the DAO for the concert table. */
    abstract fun concertDao(): ConcertDao

    companion object {

        @Volatile
        private var Instance: ConcertDatabase? = null

        /** Returns an instance of the Room database. */
        fun getDatabase(appContext: Context): ConcertDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(appContext, ConcertDatabase::class.java, "concert_database")
                    .fallbackToDestructiveMigration()
                    .build().also { Instance = it }
            }
        }
    }
}
