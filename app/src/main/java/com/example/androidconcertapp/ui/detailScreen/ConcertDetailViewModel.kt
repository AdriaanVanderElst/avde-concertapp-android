package com.example.androidconcertapp.ui.detailScreen

import android.util.Log
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
import com.example.androidconcertapp.model.Concert
import com.example.androidconcertapp.ui.listScreen.ConcertApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// THIS CLASS IS UNUSED
class ConcertDetailViewModel(
    private val concertRepository: ConcertRepository,
    cId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConcertDetailState())
    val uiState: StateFlow<ConcertDetailState> = _uiState.asStateFlow()

    lateinit var uiConcertState: StateFlow<ConcertState>

    var concertApiState: ConcertApiState by mutableStateOf(ConcertApiState.Loading)
        private set

    init {
        getRepoConcertDetail(cId)
    }

    private fun getRepoConcertDetail(cId: Int) {
        try {
            viewModelScope.launch { concertRepository.refresh() }
            uiConcertState = concertRepository.getItemById(id = cId).map { ConcertState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = ConcertState(),
                )

            concertApiState = ConcertApiState.Success
        } catch (e: Exception) {
            concertApiState = ConcertApiState.Error
        }
    }

    fun setNewComment(comment: String) {
        _uiState.update { it.copy(comment = comment) }
    }

    fun addComment(concert: Concert) {
        Log.d("Comment", uiState.value.comment)
//        viewModelScope.launch {
//            concertRepository.addComment(concert, comment)
//        }
    }

    companion object {
//        val Factory: ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val application =
//                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ConcertApplication)
//                val concertRepository = application.container.concertRepository
//                ConcertDetailViewModel(concertRepository)
//            }
//        }

        private var Instance: ConcertDetailViewModel? = null

        fun Factory(cId: Int): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ConcertApplication)
                    val concertRepository = application.container.concertRepository
                    Instance = ConcertDetailViewModel(concertRepository, cId)
                }
                Instance!!
            }
        }
    }
}