package com.example.androidconcertapp.ui.listScreen

import com.example.androidconcertapp.model.Concert
import com.example.androidconcertapp.model.User

data class ConcertState(
    val scrollActionIndex: Int = 0,
    val scrollToItemIndex: Int = 0,
    val user: User? = null,
    val comment: String = "",
)

data class ConcertListState(val concertList: List<Concert> = listOf())

sealed interface ConcertApiState {
    object Success : ConcertApiState
    object Error : ConcertApiState
    object Loading : ConcertApiState
}
