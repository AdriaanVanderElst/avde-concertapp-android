package com.example.androidconcertapp.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {
    @POST("users")
    suspend fun addUser(@Body user: ApiUser): Response<ApiUser>
    @GET("users/auth0/{auth0id}")
    suspend fun getUserByAuthId(@Path("auth0id") id: String): Response<ApiUser>
}
