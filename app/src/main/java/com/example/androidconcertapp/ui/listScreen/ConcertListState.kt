package com.example.androidconcertapp.ui.listScreen

import com.example.androidconcertapp.model.Concert

data class ConcertListState(
    val concertList: List<Concert>,
    val scrollActionIndex: Int = 0,
    val scrollToItemIndex: Int = 0,
)

sealed interface ConcertApiState {
    data class Success(val concerts: List<Concert>) : ConcertApiState
    object Error : ConcertApiState
    object Loading : ConcertApiState
}
