package com.example.androidconcertapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.androidconcertapp.data.database.ConcertDao
import com.example.androidconcertapp.data.database.ConcertDatabase
import com.example.androidconcertapp.data.database.asDbConcert
import com.example.androidconcertapp.data.database.asDomainConcert
import com.example.androidconcertapp.model.Concert
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ConcertDaoTest {
    private lateinit var concertDao: ConcertDao
    private lateinit var database: ConcertDatabase

    private var concert1 = Concert(
        1,
        "concert1",
        "2024-05-04",
        "21:30",
        2000,
        true,
        1,
        "Dam 9",
        "Amsterdam",
        1,
        "organizer1",
        "0612345678",
        "org@org.com",
        "user1",
        "this is a comment"
    )
    private var concert2 = Concert(
        2,
        "concert2",
        "2024-05-06",
        "21:00",
        1500,
        false,
        2,
        "Losstraat 1",
        "Gent",
        2,
        "organizer2",
        "0612345978",
        "org2@org.com",
        "user2",
        "this is another comment in a different concert"
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, ConcertDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        concertDao = database.concertDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertConcertInRoom() = runBlocking {
        addOneToDb()
        val allItems = concertDao.getItems().first()
        assertEquals(allItems[0].asDomainConcert(), concert1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllConcerts_returnsConcertsFromRoom() = runBlocking {
        addTwoToDb()
        val allItems = concertDao.getItems().first()
        assertEquals(allItems[0].asDomainConcert(), concert1)
        assertEquals(allItems[1].asDomainConcert(), concert2)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetConcertById_returnsConcertFromRoom() = runBlocking {
        addTwoToDb()
        val concert = concertDao.getItemById(2).first()
        assertEquals(concert.asDomainConcert(), concert2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateConcert_updatesConcertInRoom() = runBlocking {
        addOneToDb()
        val concert = concertDao.getItemById(1).first()
        assertEquals(concert.asDomainConcert(), concert1)
        concertDao.updateItem(concert.copy(comment = "a new comment"))
        val updatedConcert = concertDao.getItemById(1).first()
        assertEquals(updatedConcert.asDomainConcert().comment, "a new comment")
    }

    private suspend fun addOneToDb() {
        concertDao.insertItem(concert1.asDbConcert())
    }

    private suspend fun addTwoToDb() {
        concertDao.insertItem(concert1.asDbConcert())
        concertDao.insertItem(concert2.asDbConcert())
    }
}