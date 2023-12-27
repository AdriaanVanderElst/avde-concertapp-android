package com.example.androidconcertapp.data

import com.example.androidconcertapp.model.User

interface UserRepository {

    suspend fun addUser(user: User): User
}

class UserRepositoryImpl : UserRepository {

    override suspend fun addUser(user: User): User {
        return user
    }
}