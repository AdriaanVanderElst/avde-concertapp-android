package com.example.androidconcertapp.fake

import com.example.androidconcertapp.network.ApiConcert
import com.example.androidconcertapp.network.ApiConcertResponse
import com.example.androidconcertapp.network.ConcertUser
import com.example.androidconcertapp.network.Organizer
import com.example.androidconcertapp.network.Place

object FakeDataSource {
    val fakeDataResponse = ApiConcertResponse(
        listOf(
            ApiConcert(
                1,
                "Concert 1",
                "2025-01-01T00:00:00.000Z",
                1500,
                null,
                false,
                Place(
                    1,
                    9,
                    "Damstraat",
                    "Gent",
                ),
                Organizer(
                    1,
                    "Organizer 1",
                    "0499 99 99 99",
                    "organizer@org.com",),
                ConcertUser(
                    1,
                    "User 1",
                ),
            ),
            ApiConcert(
                2,
                "Concert 2",
                "2025-02-15T19:30:00.000Z",
                1200,
                "This is a comment",
                true,
                Place(
                    2,
                    5,
                    "Main Street",
                    "Los Angeles",
                ),
                Organizer(
                    2,
                    "Music Events LLC",
                    "555-1234-5678",
                    "info@musicevents.com",
                ),
                ConcertUser(
                    2,
                    "User 2",
                ),
            ),

            ApiConcert(
                3,
                "Jazz Night",
                "2025-03-20T21:00:00.000Z",
                800,
                null,
                false,
                Place(
                    3,
                    12,
                    "Jazz Square",
                    "New York",
                ),
                Organizer(
                    3,
                    "Cool Jazz Productions",
                    "555-9876-5432",
                    "events@cooljazz.com",
                ),
                ConcertUser(
                    3,
                    "User 3",
                ),
            ),
        ),
        3,
    )
}