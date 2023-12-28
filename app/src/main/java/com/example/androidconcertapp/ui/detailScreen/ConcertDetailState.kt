package com.example.androidconcertapp.ui.detailScreen

import com.example.androidconcertapp.model.Concert
import org.w3c.dom.Comment

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
    address = "",
    city = "",
    organizer = "",
    phoneNr = "",
    email = "",
    user = "",
    comment = "",
    )
)