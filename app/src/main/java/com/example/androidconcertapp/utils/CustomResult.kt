package com.example.androidconcertapp.utils

sealed class CustomResult<T, E> {
    data class Success<T, E>(val value: T) : CustomResult<T, E>()
    data class Error<T, E>(val error: E) : CustomResult<T, E>()
}
