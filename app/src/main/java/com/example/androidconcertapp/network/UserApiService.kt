package com.example.androidconcertapp.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/** A service for users. */
interface UserApiService {

    /** Gets all the users from the server.
     * @return all the users from the server as a [Response].
     */
    @POST("users")
    suspend fun addUser(@Body user: ApiUser): Response<ApiUser>

    /** Gets a user from the server by id.
     * @param id the auth0id of the user to get.
     * @return the user from the server as a [Response].
     */
    @GET("users/auth0/{auth0id}")
    suspend fun getUserByAuthId(@Path("auth0id") id: String): Response<ApiUser>
}
