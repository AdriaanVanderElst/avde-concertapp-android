package com.example.androidconcertapp.ui.components

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/** The ConcertAppBottomBar component.
 *
 * @param goHome The function to navigate to the home screen.
 * @param saveConcertsToApi The function to save the concerts to the Api.
 * @param onLogout The function to logout.
 */
@Composable
fun ConcertAppBottomBar(goHome: () -> Unit, saveConcertsToApi: () -> Unit, onLogout: (Context) -> Unit) {
    val context = LocalContext.current
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        actions = {
            IconButton(onClick = { onLogout(context) }) {
                Icon(Icons.Filled.ExitToApp, contentDescription = "navigate to login page")
            }
            IconButton(onClick = goHome) {
                Icon(Icons.Filled.Home, contentDescription = "navigate to home page")
            }
            IconButton(onClick = saveConcertsToApi) {
                Icon(Icons.Filled.Save, contentDescription = "save concerts to Api")
            }
        },
    )
}


