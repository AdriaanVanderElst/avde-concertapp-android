package com.example.androidconcertapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ConcertEntity::class], version = 3)
abstract class ConcertDatabase : RoomDatabase() {
    abstract fun concertDao(): ConcertDao

    companion object {

        @Volatile
        private var Instance: ConcertDatabase? = null

        fun getDatabase(appContext: Context): ConcertDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(appContext, ConcertDatabase::class.java, "concert_database")
                    .fallbackToDestructiveMigration()
                    .build().also { Instance = it }
            }
        }
    }
}
