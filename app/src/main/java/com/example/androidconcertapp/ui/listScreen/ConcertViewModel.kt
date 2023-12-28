package com.example.androidconcertapp.ui.listScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.androidconcertapp.ConcertApplication
import com.example.androidconcertapp.data.ConcertRepository
import com.example.androidconcertapp.model.Concert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

class ConcertViewModel(private val concertRepository: ConcertRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ConcertListState())
    val uiState: StateFlow<ConcertListState> = _uiState.asStateFlow()

    lateinit var uiListState: StateFlow<List<Concert>>

    var concertApiState: ConcertApiState by mutableStateOf(ConcertApiState.Loading)
        private set

    init {
        getRepoConcerts()
    }

    private fun getRepoConcerts() {
        try {
            viewModelScope.launch { concertRepository.refresh() }
            uiListState = concertRepository.getItems().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = emptyList(),
            )

            concertApiState = ConcertApiState.Success
        } catch (e: IOException) {
            concertApiState = ConcertApiState.Error
        }
    }

//    fun addConcert() {
//
//    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ConcertApplication)
                val concertRepository = application.container.concertRepository
                ConcertViewModel(concertRepository)
            }
        }
    }
}
