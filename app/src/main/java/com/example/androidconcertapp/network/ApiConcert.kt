package com.example.androidconcertapp.network

import android.util.Log
import androidx.annotation.Nullable
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
    val comment: String? = "",
    val isConfirmed: Boolean,
    val place: Place,
    val organizer: Organizer,
    val user: ConcertUser,
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
data class ConcertUser(
    val id: Int,
    val name: String,
)

fun List<ApiConcert>.asDomainObjects(): List<Concert> {
    var domainList = this.map {
        Concert(
            it.id,
            it.name,
            it.date.substringBefore("T"),
            it.date.substringAfter("T").substring(0, 5),
            it.price,
            it.isConfirmed,
            it.place.id,
            it.place.street + " " + it.place.houseNr.toString(),
            it.place.city,
            it.organizer.id,
            it.organizer.name,
            it.organizer.phoneNr,
            it.organizer.email,
            it.user.name,
            it.comment ?: "",
        )
    }
    return domainList
}


@Serializable
data class ApiPutConcert(
    val name: String,
    val date: String,
    val price: Int,
    val isConfirmed: Boolean,
    val placeId: Int,
    val organizerId: Int,
    val comment: String
)

fun Concert.asApiPutConcert() = ApiPutConcert(
    name = name,
    date = date + "T" + time + ":00.000Z",
    price = price,
    isConfirmed = isConfirmed,
    placeId = placeId,
    organizerId = organizerId,
    comment = comment,
)