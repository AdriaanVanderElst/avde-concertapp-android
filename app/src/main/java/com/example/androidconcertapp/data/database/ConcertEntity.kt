package com.example.androidconcertapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidconcertapp.model.Concert

/** ConcertEntity is a data class that represents a Concert in the database. */
@Entity(tableName = "concerts")
data class ConcertEntity(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val date: String = "",
    val time: String = "",
    val price: Int = 0,
    val isConfirmed: Boolean = false,
    val placeId: Int,
    val address: String = "",
    val city: String = "",
    val organizerId: Int,
    val organizer: String = "",
    val phoneNr: String = "",
    val email: String = "",
    val user: String = "",
    val comment: String = "",
)

/** Extension function for a Concert to convert it to a ConcertEntity */
fun Concert.asDbConcert() = ConcertEntity(
    id = id,
    name = name,
    date = date,
    time = time,
    price = price,
    isConfirmed = isConfirmed,
    placeId = placeId,
    address = address,
    city = city,
    organizerId = organizerId,
    organizer = organizer,
    phoneNr = phoneNr,
    email = email,
    user = user,
    comment = comment,
)

/** Extension function for a ConcertEntity to convert it to a Concert */
fun ConcertEntity.asDomainConcert() = Concert(
    id = id,
    name = name,
    date = date,
    time = time,
    price = price,
    isConfirmed = isConfirmed,
    placeId = placeId,
    address = address,
    city = city,
    organizerId = organizerId,
    organizer = organizer,
    phoneNr = phoneNr,
    email = email,
    user = user,
    comment = comment,
)

/** Extension function for a List of ConcertEntities to convert it to a List of Concerts */
fun List<ConcertEntity>.asDomainConcerts() = map { it.asDomainConcert() }
