package com.example.androidconcertapp.data

import com.example.androidconcertapp.model.Concert

object ConcertSampler {

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
                "Weerstraat 1",
                "Gent",
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
                "Losstraat 15",
                "Antwerpen",
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
