package com.example.androidconcertapp.model

data class Concert(
    val id: Int,
    val name: String,
    val date: String,
    val time: String,
    val price: Int,
    val isConfirmed: Boolean,
    val placeId: Int,
    val address: String,
    val city: String,
    val organizerId: Int,
    val organizer: String,
    val phoneNr: String,
    val email: String,
    val user: String,
    val comment: String,
)
