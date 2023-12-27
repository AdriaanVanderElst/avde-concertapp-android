package com.example.androidconcertapp.ui.listScreen

data class ConcertListState(
    val scrollActionIndex: Int = 0,
    val scrollToItemIndex: Int = 0,
)

sealed interface ConcertApiState {
    object Success : ConcertApiState
    object Error : ConcertApiState
    object Loading : ConcertApiState
}
