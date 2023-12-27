package com.example.androidconcertapp.ui.detailScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.androidconcertapp.ConcertApplication
import com.example.androidconcertapp.data.ConcertRepository
import com.example.androidconcertapp.data.ConcertSampler
import com.example.androidconcertapp.ui.listScreen.ConcertListViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class ConcertDetailsViewModel(private val concertRepository: ConcertRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ConcertDetailsState(ConcertSampler.getOne()))
    val uiState: StateFlow<ConcertDetailsState> = _uiState.asStateFlow()

    var concertDetailsApiState: ConcertDetailsApiState by mutableStateOf(ConcertDetailsApiState.Loading)
        private set

    fun getApiConcertById(id: Int) {
        viewModelScope.launch {
            try {
                val concert = concertRepository.getConcertById(id)
                _uiState.update { it.copy(concert = concert) }
                concertDetailsApiState = ConcertDetailsApiState.Success(concert)
            } catch (e: IOException) {
                concertDetailsApiState = ConcertDetailsApiState.Error
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ConcertApplication)
                val concertRepository = application.container.concertRepository
                ConcertDetailsViewModel(concertRepository)
            }
        }
    }
}
