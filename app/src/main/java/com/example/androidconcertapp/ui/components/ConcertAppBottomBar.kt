package com.example.androidconcertapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ConcertAppBottomBar(goHome: () -> Unit, logOut: () -> Unit) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        actions = {
            IconButton(onClick = goHome) {
                Icon(Icons.Filled.Home, contentDescription = "Go home")
            }
            IconButton(onClick = logOut) {
                Icon(Icons.Filled.ExitToApp, contentDescription = "Go home")
            }
        },
    )
}
