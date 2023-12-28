package com.example.androidconcertapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidconcertapp.model.Concert

@Entity(tableName = "concerts")
data class ConcertEntity(
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
    val comment: String = "",
)

fun Concert.asDbConcert() = ConcertEntity(
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
    comment = comment,
)

fun ConcertEntity.asDomainConcert() = Concert(
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
    comment = comment,
)
fun List<ConcertEntity>.asDomainConcerts() = map { it.asDomainConcert() }
