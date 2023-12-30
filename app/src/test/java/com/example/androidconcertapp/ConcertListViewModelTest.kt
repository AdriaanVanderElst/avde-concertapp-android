package com.example.androidconcertapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidconcertapp.data.ConcertRepository
import com.example.androidconcertapp.fake.FakeConcertRepository
import com.example.androidconcertapp.ui.listScreen.ConcertListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ConcertListViewModelTest {
    private val comment = "a new comment"

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var concertRepository: ConcertRepository

    private lateinit var viewModel: ConcertListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun testFetchRepositories() = runTest {

    }

}

class TestDispatcherRule @OptIn(ExperimentalCoroutinesApi::class) constructor(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}