package com.example.androidconcertapp.network

import com.example.androidconcertapp.model.Concert
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiConcertResponse(
    @SerialName("items") val concerts: List<ApiConcert>,
    @SerialName("count") val count: Int,
)

@Serializable
data class ApiConcert(
    val id: Int,
    val name: String,
    val date: String,
    val price: Int,
    val isConfirmed: Boolean,
    val place: Place,
    val organizer: Organizer,
    val user: User,
)

@Serializable
data class Place(
    val id: Int,
    val houseNr: Int,
    val street: String,
    val city: String,
)

@Serializable
data class Organizer(
    val id: Int,
    val name: String,
    val phoneNr: String,
    val email: String,
)

@Serializable
data class User(
    val id: Int,
    val name: String,
)

fun List<ApiConcert>.asDomainObjects(): List<Concert> {
    var domainList = this.map {
        Concert(
            it.id,
            it.name,
            it.date.substringBefore("T"),
            it.date.substringAfter("T").substringBefore("."),
            it.price,
            it.isConfirmed,
            it.place.street + " " + it.place.houseNr.toString(),
            it.place.city,
            it.organizer.name,
            it.organizer.phoneNr,
            it.organizer.email,
            it.user.name,
        )
    }
    return domainList
}
