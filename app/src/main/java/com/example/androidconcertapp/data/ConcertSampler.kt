package com.example.androidconcertapp.data

import com.example.androidconcertapp.model.Concert

/**
 * ConcertSampler is a singleton object that provides a list of Concerts for testing purposes.
 */
object ConcertSampler {

    /**
     * getAll returns a list of Concerts.
     */
    val getAll: () -> MutableList<Concert> = {
        val list = mutableListOf<Concert>()
        list.add(
            Concert(
                1,
                "Test Concert 1",
                "2024-05-01",
                "10:00:00",
                1500,
                true,
                1,
                "Weerstraat 1",
                "Gent",
                1,
                "Test Organizer 1",
                "0123456789",
                "test@test.com",
                "Test User 1",
                "Test Comment 1"
            ),
        )
        list.add(
            Concert(
                2,
                "Test Concert 2",
                "2024-05-02",
                "10:00:00",
                2500,
                false,
                2,
                "Losstraat 15",
                "Antwerpen",
                2,
                "Test Organizer 2",
                "9876543210",
                "test2@test.com",
                "Test User 2",
                "Test Comment 2"
            ),
        )
        list
    }
}
