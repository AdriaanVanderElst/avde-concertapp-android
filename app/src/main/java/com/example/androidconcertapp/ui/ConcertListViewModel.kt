package com.example.androidconcertapp.ui

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
import com.example.androidconcertapp.data.ConcertSampler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class ConcertListViewModel(private val concertRepository: ConcertRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ConcertListState(ConcertSampler.getAll()))
    val uiState: StateFlow<ConcertListState> = _uiState.asStateFlow()

    var concertApiState: ConcertApiState by mutableStateOf(ConcertApiState.Loading)
        private set

    init {
        getApiConcerts()
    }

    private fun getApiConcerts() {
        viewModelScope.launch {
            try {
                val concerts = concertRepository.getConcerts()
                _uiState.update { it.copy(concertList = concerts) }
                concertApiState = ConcertApiState.Success(concerts)
            } catch (e: IOException) {
                concertApiState = ConcertApiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ConcertApplication)
                val concertRepository = application.container.concertRepository
                ConcertListViewModel(concertRepository)
            }
        }
    }
}
