package com.example.androidconcertapp.ui.listScreen

import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConcertListViewModel(private val concertRepository: ConcertRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ConcertViewState())
    val uiState: StateFlow<ConcertViewState> = _uiState.asStateFlow()

    lateinit var uiListState: StateFlow<ConcertListState>

    var concertApiState: ConcertApiState by mutableStateOf(ConcertApiState.Loading)
        private set

    init {

        getRepoConcerts()
    }

    private fun getRepoConcerts() {
        try {
            viewModelScope.launch { concertRepository.refresh() }
            uiListState = concertRepository.getItems().map { ConcertListState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = ConcertListState(),
                )

            concertApiState = ConcertApiState.Success
        } catch (e: Exception) {
            concertApiState = ConcertApiState.Error
        }
    }

    fun setConcertDetail(cId: Int) {
        _uiState.update { it ->
            val concertDetail = uiListState.value.concertList.find { it.id == cId }
            it.copy(
                concertDetail = concertDetail,
                newComment = concertDetail?.comment ?: ""
            )
        }
    }

    fun setNewComment(comment: String) {
        _uiState.update { it.copy(newComment = comment) }
    }

    fun addComment() {
        Log.d("Comment", uiState.value.newComment)
        if (uiState.value.concertDetail != null) {
            val concert = uiState.value.concertDetail!!.copy(comment = uiState.value.newComment)
            viewModelScope.launch {
                concertRepository.addComment(concert)
            }
        }
    }

    fun saveConcertsToApi() {
        viewModelScope.launch {
            concertRepository.updateConcertsToApi()
            concertRepository.refresh()
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
