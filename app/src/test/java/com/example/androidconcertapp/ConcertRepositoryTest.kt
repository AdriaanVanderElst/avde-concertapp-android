package com.example.androidconcertapp

import android.util.Log
import com.example.androidconcertapp.data.CachingConcertRepository
import com.example.androidconcertapp.data.ConcertRepository
import com.example.androidconcertapp.data.database.ConcertDao
import com.example.androidconcertapp.data.database.asDbConcert
import com.example.androidconcertapp.fake.FakeConcertApiService
import com.example.androidconcertapp.model.Concert
import com.example.androidconcertapp.network.ApiPutConcert
import com.example.androidconcertapp.network.asDomainObjects
import com.example.androidconcertapp.network.getConcertsAsFlow
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


/**
 * A test class for the [ConcertRepository].
 * It uses a [FakeConcertApiService] and a mocked [ConcertDao] to test the [ConcertRepository].
 */
class ConcertRepositoryTest {

    @Mock
    lateinit var mockConcertDao: ConcertDao

    private lateinit var repository: ConcertRepository
    private lateinit var fakeApiService: FakeConcertApiService
    private var mockLogger: MockedStatic<Log> ?= null

    /**
     * A setup function which is run before every test.
     */
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        mockLogger = Mockito.mockStatic(Log::class.java)

        fakeApiService = FakeConcertApiService()

        repository = CachingConcertRepository(
            concertApiService = fakeApiService,
            concertDao = mockConcertDao,
        )
    }

    /**
     * A teardown function which is run after every test.
     */
    @After
    fun tearDown() {
        mockLogger?.close()
    }

    /**
     * Verifies that the [ConcertRepository.insertItem] function inserts a [Concert] into the
     * [ConcertDao] by calling the correct function
     */
    @Test
    fun `insertItem inserts concert into dao`() = runTest {
        val concert = createSampleConcert()
        val dbConcert = concert.asDbConcert()

        repository.insertItem(concert)
        verify(mockConcertDao).insertItem(dbConcert)
    }

    /**
     * Verifies that the [ConcertRepository.getItems] function gets all [Concert]s from the
     * [ConcertDao] by calling the correct function
     */
    @Test
    fun `getItems gets all concerts from dao`() = runTest {
        val expected = listOf(createSampleConcert())

        val dbConcertResponse = flowOf(listOf(
            createSampleConcert().asDbConcert())
        )
        `when`(mockConcertDao.getItems()).thenReturn(dbConcertResponse)

        val actual = repository.getItems().first()

        assertEquals(expected, actual)
    }

    /**
     * Verifies that the [ConcertRepository.getItemById] function gets a [Concert] from the
     * [ConcertDao] by calling the correct function
     */
    @Test
    fun `getItemById gets concert from dao`() = runTest {
        val expected = createSampleConcert()

        val dbConcertResponse = flowOf(createSampleConcert().asDbConcert())

        `when`(mockConcertDao.getItemById(1)).thenReturn(dbConcertResponse)

        val actual = repository.getItemById(1).first()

        assertEquals(expected, actual)
    }

    /**
     * Verifies that the [ConcertRepository.addComment] updates a [Concert] in
     * the [ConcertDao] by calling the correct function
     */
    @Test
    fun `addComment adds comment to concert in dao`() = runTest {
        val concert = createSampleConcert()
        val dbConcert = concert.asDbConcert()

        repository.addComment(concert)
        verify(mockConcertDao).updateItem(dbConcert)
    }

    /**
     * Verifies that the [ConcertRepository.refresh] function updates the [Concert]s in the
     * [ConcertDao] by calling the correct api function and inserting the [Concert]s in the [ConcertDao]
     */
    @Test
    fun `refresh fetches concerts from fake api and inserts in dao`() = runTest {
        val fakeapiresponse = fakeApiService.getConcerts()
        val fakeData = fakeapiresponse.concerts.asDomainObjects()

        repository.refresh()

        fakeData.forEach {
            verify(mockConcertDao).insertItem(it.asDbConcert())
        }

        mockLogger?.verify { Log.d("ConcertRepository", "Refresh will be called") }
    }

    private fun createSampleConcert(): Concert {
        return Concert(
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
            "test@test.com",
            "Testuser",
            "This is a comment",
        )
    }
}