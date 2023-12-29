package com.example.androidconcertapp.ui.listScreen

import com.example.androidconcertapp.model.Concert
import com.example.androidconcertapp.model.User
import org.w3c.dom.Comment

data class ConcertViewState(
    val scrollActionIndex: Int = 0,
    val scrollToItemIndex: Int = 0,
    val user: User? = null,
    val newComment: String = "",
    val concertDetail: Concert? = null,
)

data class ConcertListState(val concertList: List<Concert> = listOf())

sealed interface ConcertApiState {
    object Success : ConcertApiState
    object Error : ConcertApiState
    object Loading : ConcertApiState
}
