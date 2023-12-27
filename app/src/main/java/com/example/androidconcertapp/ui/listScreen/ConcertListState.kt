package com.example.androidconcertapp.ui.listScreen

import com.example.androidconcertapp.model.User

data class ConcertListState(
    val scrollActionIndex: Int = 0,
    val scrollToItemIndex: Int = 0,
    val user: User? = null,
)

sealed interface ConcertApiState {
    object Success : ConcertApiState
    object Error : ConcertApiState
    object Loading : ConcertApiState
}
