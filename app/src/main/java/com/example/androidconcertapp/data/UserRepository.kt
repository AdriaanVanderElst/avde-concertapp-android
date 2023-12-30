package com.example.androidconcertapp.data

import android.content.res.Resources
import android.util.Log
import com.example.androidconcertapp.model.User
import com.example.androidconcertapp.network.ApiUser
import com.example.androidconcertapp.network.UserApiService


interface UserRepository {
    suspend fun addUser(user: User)

}

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
            } else {
                // Log an error message if adding user fails
                Log.e("UserRepository", "Error in creating user")
            }
        } catch (e: Exception) {
            // Log an error message if adding user fails
            Log.e("UserRepository", "Error in creating user: ${e.message}")
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

    //    override suspend fun addUser(user: User): ApiUser {
//        val apiUser = ApiUser(null, user.name, user.id)
//        try {
//            val existingUser = userApiService.getUserByAuthId(user.id)
//            return existingUser
//        } catch (e: Resources.NotFoundException) {
//            Log.e("UserRepository", "User Doesn't Exist: ${e.message}")
//        }
//        try {
//            userApiService.addUser(apiUser)
//            return apiUser
//        } catch (e: Exception) {
//            Log.e("UserRepository", "Error in creating user: ${e.message}")
//        }
//        return null!!
//    }

}
