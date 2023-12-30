package com.example.androidconcertapp.ui.detailScreen

import com.example.androidconcertapp.model.Concert
import org.w3c.dom.Comment

// THIS CLASS IS UNUSED
data class ConcertDetailState(
    val comment: String = "",
)

data class ConcertState(val concert: Concert = Concert(
    id = 0,
    name = "",
    date = "",
    time = "",
    price = 0,
    isConfirmed = false,
    placeId = 0,
    address = "",
    city = "",
    organizerId = 0,
    organizer = "",
    phoneNr = "",
    email = "",
    user = "",
    comment = "",
    )
)