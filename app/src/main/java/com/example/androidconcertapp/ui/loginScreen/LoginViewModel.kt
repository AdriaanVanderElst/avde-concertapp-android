package com.example.androidconcertapp.ui.loginScreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.example.androidconcertapp.model.User
import com.example.androidconcertapp.utils.NetworkResponse

class LoginViewModel() : ViewModel() {

    val loginState: MutableState<NetworkResponse<String>> =
        mutableStateOf(NetworkResponse.Failure(""))

    var isLoggedIn by mutableStateOf(false)

    var user by mutableStateOf(User())
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
                        user = User(result.idToken, result.accessToken)

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

    fun onLogout(context: Context, onLogoutNavigation: () -> Unit) {
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
                        Log.d("Logout", "Success")
                        onLogoutNavigation()
                    }
                },
            )
    }
}
