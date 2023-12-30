package com.example.androidconcertapp.network

import kotlinx.serialization.Serializable

/** Data Transfer Object representing a user from the API */
@Serializable
data class ApiUser(
    val id: Int? = null,
    val name: String,
    val auth0id: String,
)
