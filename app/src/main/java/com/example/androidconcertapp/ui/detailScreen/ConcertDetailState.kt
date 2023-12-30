package com.example.androidconcertapp.ui.detailScreen

import com.example.androidconcertapp.model.Concert

// THIS CLASS IS UNUSED
/**
 * This is a deprecated class that was used to store the state of the app's detail screen.
 * The detail screen uses the ConcertViewState to store its state.
 *
 * @Deprecated Use ConcertViewState instead.
 */

@Deprecated("Use ConcertViewState instead")
data class ConcertDetailState(
    val comment: String = "",
)

/** This is a deprecated class that was used to store the state of the app's detail screen.
 * The detail screen uses the ConcertListState to store its state.
 *
 * @Deprecated Use ConcertListState instead.
 */

@Deprecated("Use ConcertListState instead")
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