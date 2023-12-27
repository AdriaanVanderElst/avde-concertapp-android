package com.example.androidconcertapp.ui.detailScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// class ConcertDetailsViewModel(private val concertRepository: ConcertRepository) : ViewModel() {
//    private val _uiState = MutableStateFlow(ConcertDetailsState(ConcertSampler.getOne()))
//    val uiState: StateFlow<ConcertDetailsState> = _uiState.asStateFlow()
//
//    var concertDetailsApiState: ConcertDetailsApiState by mutableStateOf(ConcertDetailsApiState.Loading)
//        private set
//
//    fun getApiConcertById(id: Int) {
//        viewModelScope.launch {
//            try {
//                val concert = concertRepository.getConcertById(id)
//                _uiState.update { it.copy(concert = concert) }
//                concertDetailsApiState = ConcertDetailsApiState.Success(concert)
//            } catch (e: IOException) {
//                concertDetailsApiState = ConcertDetailsApiState.Error
//            }
//        }
//    }
//    companion object {
//        val Factory: ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ConcertApplication)
//                val concertRepository = application.container.concertRepository
//                ConcertDetailsViewModel(concertRepository)
//            }
//        }
//    }
// }
