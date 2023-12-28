package com.example.androidconcertapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.androidconcertapp.ui.loginScreen.LoginViewModel

@Composable
fun ConcertAppBottomBar(goHome: () -> Unit, loginViewModel: LoginViewModel, goToLogin: () -> Unit) {
    val context = LocalContext.current
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        actions = {
            IconButton(onClick = goHome) {
                Icon(Icons.Filled.Home, contentDescription = "Go home")
            }
            IconButton(onClick = { loginViewModel.onLogout(context, goToLogin) }) {
                Icon(Icons.Filled.ExitToApp, contentDescription = "Go home")
            }
        },
    )
}
