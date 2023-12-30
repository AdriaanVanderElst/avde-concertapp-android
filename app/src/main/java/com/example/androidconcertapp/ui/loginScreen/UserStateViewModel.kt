package com.example.androidconcertapp.ui.loginScreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.example.androidconcertapp.ConcertApplication
import com.example.androidconcertapp.R
import com.example.androidconcertapp.data.UserRepository
import com.example.androidconcertapp.model.User
import com.example.androidconcertapp.utils.NetworkResponse
import kotlinx.coroutines.launch


/**
 * The [ViewModel] of the UserState.
 *
 * @property userRepository the [UserRepository] that is used to get the User.
 */
class UserStateViewModel(private val userRepository: UserRepository) : ViewModel() {

    val loginState: MutableState<NetworkResponse<String>> =
        mutableStateOf(NetworkResponse.Failure(""))

    var isLoggedIn by mutableStateOf(false)
    var isBusy by mutableStateOf(false)

    var user by mutableStateOf(User())

    /**
     * Logs the user in and tries to add the user to the repository.
     * If the user exists in the Api already, an error occurs, and the user is not added to the Api.
     * @param context The context of the app.
     */
    fun onLogin(context: Context) {
        isBusy = true
        val auth = Auth0(context)

        WebAuthProvider
            .login(auth)
            .withScheme("app")
            .withAudience(context.getString(R.string.com_auth0_audience))
            .start(
                context,
                object : Callback<Credentials, AuthenticationException> {
                    override fun onFailure(exception: AuthenticationException) {
                        Log.e("Login", "Error: ${exception.message}")
                    }

                    override fun onSuccess(result: Credentials) {
                        Log.d("Access Token", result.accessToken)
                        user = User(result.idToken, result.accessToken)
                        user.accessToken?.let { saveTokenToSharedPreferences(context, it) }
                        Log.d("UserInfo", "${user.name} with id: ${user.id} logged in.")
                        viewModelScope.launch {
                            userRepository.addUser(user)
                        }
                        isBusy = false
                        isLoggedIn = true
                    }
                },
            )
    }
    internal fun saveTokenToSharedPreferences(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("bearerToken", token).apply()
    }

    /**
     * Logs the user out.
     * @param context The context of the app.
     */
    fun onLogout(context: Context) {
        isBusy = true
        val auth0 = Auth0(context)

        WebAuthProvider.logout(auth0)
            .withScheme("app")
            .start(
                context,
                object : Callback<Void?, AuthenticationException> {
                    override fun onFailure(error: AuthenticationException) {
                        Log.e("Logout", "Error: ${error.message}")
                    }

                    override fun onSuccess(result: Void?) {
                        user = User()
                        saveTokenToSharedPreferences(context, "")
                        Log.d("Logout", "Success")
                        isBusy = false
                        isLoggedIn = false
                    }
                },
            )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ConcertApplication)
                val userRepository = application.container.userRepository
                UserStateViewModel(userRepository)
            }
        }
    }
}

/**
 * The providableCompositionLocal of the [UserStateViewModel].
 */
val LocalUserState = compositionLocalOf<UserStateViewModel> { error("User State context not found") }
