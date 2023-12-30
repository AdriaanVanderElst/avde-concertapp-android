package com.example.androidconcertapp

import com.example.androidconcertapp.data.CachingConcertRepository
import com.example.androidconcertapp.data.ConcertRepository
import com.example.androidconcertapp.data.database.ConcertDao
import com.example.androidconcertapp.data.database.asDomainConcerts
import com.example.androidconcertapp.fake.FakeConcertApiService
import com.example.androidconcertapp.fake.FakeConcertDao
import com.example.androidconcertapp.fake.FakeDataSource
import com.example.androidconcertapp.network.ConcertApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test



class ConcertRepositoryTest {

    private lateinit var repository: ConcertRepository
    private lateinit var mockConcertDao: ConcertDao
    private lateinit var mockApiService: ConcertApiService
}