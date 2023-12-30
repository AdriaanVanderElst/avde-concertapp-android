package com.example.androidconcertapp.data

import android.content.res.Resources
import android.util.Log
import com.example.androidconcertapp.model.User
import com.example.androidconcertapp.network.ApiUser
import com.example.androidconcertapp.network.UserApiService
import java.net.SocketTimeoutException

/**
 * A repository for [User]s.
 */
interface UserRepository {

    /**
     * Adds a [User] to the API.
     *
     * @param user the [User] to add.
     */
    suspend fun addUser(user: User)

}

/**
 * A repository for [User]s. This repository saves the [User]s to the API.
 *
 * @property userApiService the [UserApiService] that is used to send the [User]s to the API.
 */
class UserRepositoryImpl(private val userApiService: UserApiService) : UserRepository {

    override suspend fun addUser(user: User) {
        // Check if user exists -- STOPPED WORKING
//      if (userExists(user.id)) {}

        // User doesn't exist, attempt to add
        val apiUser = ApiUser(null, user.name, user.id)
        try {
            val response = userApiService.addUser(apiUser)
            if (response.isSuccessful) {
                // Log a success message if adding user succeeds
                Log.i("UserRepository", "User added successfully")
            }
        } catch (e: Exception) {
            // Log an error message if adding user fails
            Log.e("UserRepository", "Error in creating user: ${e.message}")
        } catch (e: SocketTimeoutException) {
            Log.e("UserRepository", "Backend connection failed: ${e.message}")
        }
    }


    private suspend fun userExists(id: String): Boolean {
        return try {
            // Check if user exists
            userApiService.getUserByAuthId(id)
            true
        } catch (e: Resources.NotFoundException) {
            // User not found, return false
            false
        }
    }
}
