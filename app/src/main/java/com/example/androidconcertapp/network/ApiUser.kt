package com.example.androidconcertapp.network

import kotlinx.serialization.Serializable

// how to use ignoreUnknownKeys = true for this class, since the api returns more fields than this class has?

@Serializable
data class ApiUser(
    val id: Int? = null,
    val name: String,
    val auth0id: String,
)
