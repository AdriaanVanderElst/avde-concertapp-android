package com.example.androidconcertapp.ui.listScreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.example.androidconcertapp.ConcertApplication
import com.example.androidconcertapp.data.ConcertRepository
import com.example.androidconcertapp.model.Concert
import com.example.androidconcertapp.model.User
import com.example.androidconcertapp.utils.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class ConcertListViewModel(private val concertRepository: ConcertRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ConcertListState())
    val uiState: StateFlow<ConcertListState> = _uiState.asStateFlow()

    lateinit var uiListState: StateFlow<List<Concert>>

    val loginState: MutableState<NetworkResponse<String>> =
        mutableStateOf(NetworkResponse.Failure(""))

    var concertApiState: ConcertApiState by mutableStateOf(ConcertApiState.Loading)
        private set

    fun onLogin(context: Context, onLoginNavigation: () -> Unit) {
        val auth = Auth0(context)

        WebAuthProvider
            .login(auth)
            .withScheme("app")
            // .withAudience
            .start(
                context,
                object : Callback<Credentials, AuthenticationException> {
                    override fun onFailure(exception: AuthenticationException) {
                        Log.e("Login", "Error: ${exception.message}")
                    }

                    override fun onSuccess(result: Credentials) {
                        val user = User(result.idToken, result.accessToken)
                        _uiState.update { state ->
                            state.copy(user = user)
                        }

                        user.accessToken?.let { saveTokenToSharedPreferences(context, it) }
                        Log.d("Login", "Success: ${user.name}")
//                        viewModelScope.launch {
//                            userApiRepository.addUser(user)
//                        }
                        onLoginNavigation()
                    }
                },
            )
    }

    internal fun saveTokenToSharedPreferences(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences("AuthPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("auth0_token", token).apply()
    }

    fun onLogout(onLogoutNavigation: () -> Unit) {
        onLogoutNavigation()
    }

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
                ConcertListViewModel(concertRepository)
            }
        }
    }
}
