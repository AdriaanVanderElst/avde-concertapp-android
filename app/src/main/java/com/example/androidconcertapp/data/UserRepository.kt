package com.example.androidconcertapp.data

import android.util.Log
import com.example.androidconcertapp.model.User
import com.example.androidconcertapp.network.ApiUser
import com.example.androidconcertapp.network.UserApiService
import com.example.androidconcertapp.utils.CustomResult



interface UserRepository {

    suspend fun addUser(user: User): ApiUser
}

class UserRepositoryImpl(private val userApiService: UserApiService) : UserRepository {

    override suspend fun addUser(user: User): ApiUser {
        val apiUser = ApiUser(null, user.name, user.id)
        val existingUser = userApiService.getUserByAuthId(user.id)
        return if (existingUser.auth0id == user.id) {
            existingUser
        } else {
            userApiService.addUser(apiUser)
        }
    }
}
