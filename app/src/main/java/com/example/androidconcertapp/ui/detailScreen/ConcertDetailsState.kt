package com.example.androidconcertapp.ui.detailScreen

import com.example.androidconcertapp.model.Concert

data class ConcertDetailsState(val concert: Concert)

sealed interface ConcertDetailsApiState {
    data class Success(val concert: Concert) : ConcertDetailsApiState
    object Error : ConcertDetailsApiState
    object Loading : ConcertDetailsApiState
}
