package com.example.androidconcertapp.model

/**
 * Concert represents a concert in the app.
 * @property id the unique id of the concert.
 * @property name the name of the concert.
 * @property date the date of the concert.
 * @property time the time of the concert.
 * @property price the price of the concert.
 *  @property isConfirmed the confirmation status of the concert.
 *  @property placeId the id of the place of the concert.
 *  @property address the address of the place of the concert.
 *  @property city the city of the place of the concert.
 *  @property organizerId the id of the organizer of the concert.
 *  @property organizer the name of the organizer of the concert.
 *  @property phoneNr the phone number of the organizer of the concert.
 *  @property email the email of the organizer of the concert.
 *  @property user the user who added the concert.
 *  @property comment the comment of the concert.
 */
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
