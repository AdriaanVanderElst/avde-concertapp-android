package com.example.androidconcertapp.data

import com.example.androidconcertapp.model.Concert

object ConcertSampler {
    val getAll: () -> MutableList<Concert> = {
        val list = mutableListOf<Concert>()
        list.add(Concert(1,"Test Concert 1", 1000))
        list.add(Concert(2,"Test Concert 2", 1200))
        list.add(Concert(3,"Test Concert 3", 1800))
        list
    }
}
