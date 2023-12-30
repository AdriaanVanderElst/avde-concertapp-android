package com.example.androidconcertapp.ui.listScreen

import com.example.androidconcertapp.model.Concert
import com.example.androidconcertapp.model.User

/** The state of the app's list screen. */
data class ConcertViewState(
    val scrollActionIndex: Int = 0,
    val scrollToItemIndex: Int = 0,
    val user: User? = null,
    val newComment: String = "",
    val concertDetail: Concert? = null,
)

/** The state containing the list of concerts from the repository. */
data class ConcertListState(val concertList: List<Concert> = listOf())

/** The state of the API call. */
sealed interface ConcertApiState {
    object Success : ConcertApiState
    object Error : ConcertApiState
    object Loading : ConcertApiState
}
