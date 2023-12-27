package com.example.androidconcertapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidconcertapp.model.Concert

@Entity(tableName = "concerts")
data class concertEntity(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val date: String = "",
    val time: String = "",
    val price: Int = 0,
    val isConfirmed: Boolean = false,
    val address: String = "",
    val city: String = "",
    val organizer: String = "",
    val phoneNr: String = "",
    val email: String = "",
    val user: String = "",
)

fun Concert.asDbConcert() = concertEntity(
    id = id,
    name = name,
    date = date,
    time = time,
    price = price,
    isConfirmed = isConfirmed,
    address = address,
    city = city,
    organizer = organizer,
    phoneNr = phoneNr,
    email = email,
    user = user,
)

fun concertEntity.asDomainConcert() = Concert(
    id = id,
    name = name,
    date = date,
    time = time,
    price = price,
    isConfirmed = isConfirmed,
    address = address,
    city = city,
    organizer = organizer,
    phoneNr = phoneNr,
    email = email,
    user = user,
)
fun List<concertEntity>.asDomainConcerts() = map { it.asDomainConcert() }
