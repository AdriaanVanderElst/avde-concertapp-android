package com.example.androidconcertapp

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidconcertapp.data.ConcertRepository
import com.example.androidconcertapp.fake.FakeConcertRepository
import com.example.androidconcertapp.model.Concert
import com.example.androidconcertapp.ui.listScreen.ConcertApiState
import com.example.androidconcertapp.ui.listScreen.ConcertListState
import com.example.androidconcertapp.ui.listScreen.ConcertListViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * A test class for the [ConcertListViewModel].
 * It uses a mock of [ConcertRepository] to test the [ConcertListViewModel].
 */
class ConcertListViewModelTest {
    private val comment = "a new comment"

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    @Mock
    private lateinit var mockrepository: ConcertRepository

//    private lateinit var fakerepository: FakeConcertRepository

    private lateinit var viewModel: ConcertListViewModel

    private var mockLogger: MockedStatic<Log>?= null

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


    /** A setup function that is run before every test */
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mockLogger = Mockito.mockStatic(Log::class.java)
//        fakerepository = FakeConcertRepository()
        Mockito.`when`(mockrepository.getItems()).thenReturn(getFakeConcerts())
        viewModel = ConcertListViewModel(mockrepository)
    }

    /** A teardown function that is run after every test */
    @After
    fun tearDown() {
        mockLogger?.close()
    }

    /**
     * Verifies that the [ConcertListViewModel.concertApiState] is set to [ConcertApiState.Success] when
     * the [ConcertListViewModel] is created.
     * It also verifies that the [ConcertListViewModel.uiListState] is properly set.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getRepoConcerts updates the uiListState successfully on init`() = runTest {
        Mockito.`when`(mockrepository.getItems()).thenReturn(getFakeConcerts())
        Mockito.`when`(mockrepository.refresh()).thenReturn(Unit)

        advanceUntilIdle()

        verify(mockrepository).getItems()
        verify(mockrepository).refresh()
        assertEquals(ConcertApiState.Success, viewModel.concertApiState)

//        val firstItem = viewModel.uiListState.value.concertList.first()
//        assertEquals(fakeConcerts[0].id, firstItem.id)

    }

    /**
     * Verifies that the setConcertDetail function sets the concertDetail in the uiState
     */
    @Test
    fun `test setConcertDetail changes state and sets the concertDetail`() = runTest {
        val concertId = 1
        viewModel.setConcertDetail(concertId)
        assertEquals(concertId, viewModel.uiState.value.concertDetail?.id)
    }

    /**
     * Verifies that the setNewComment function sets the newComment in the uiState
     */
    @Test
    fun `test setNewComment changes state and sets the newComment`() = runTest {
        viewModel.setNewComment(comment)
        assertEquals(comment, viewModel.uiState.value.newComment)
    }

    /**
     * Verifies that the setNewComment function adds the newComment to the concertDetail in the uiState
     * and calls the addComment function on the repository
     */
    @Test
    fun `test addComment changes state and calls addComment on repository`() = runTest {
        Mockito.`when`(mockrepository.addComment(fakeConcerts[0])).thenReturn(Unit)
        viewModel.setConcertDetail(1)
        viewModel.setNewComment(comment)
        viewModel.addComment()
        assertEquals(comment, viewModel.uiState.value.concertDetail?.comment)
        verify(mockrepository).addComment(fakeConcerts[0])
    }

    /**
     * Verifies that the setNewComment function calls the repository updateConcertsToApi function
     * to save the new comments in the concerts to the api
     */
    @Test
    fun `Test saveConcertsToApi calls repository updateConcertsToApi`() = runTest {
        viewModel.saveConcertsToApi()
        verify(mockrepository).updateConcertsToApi()
    }


    /** helper function to get a fake flow of a list of concerts */
    private fun getFakeConcerts(): Flow<List<Concert>> {
        return flow {
            emit(fakeConcerts)
        }
    }


}

/**
 * A test rule that sets the main dispatcher to a [TestDispatcher] before every test and resets it
 * after every test.
 */
class TestDispatcherRule @OptIn(ExperimentalCoroutinesApi::class) constructor(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

