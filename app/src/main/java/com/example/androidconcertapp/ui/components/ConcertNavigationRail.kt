package com.example.androidconcertapp.ui.components

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun ConcertNavigationRail(
    goHome: () -> Unit,
    onLogout: (Context) -> Unit,
    saveConcertsToApi: () -> Unit
) {
    val context = LocalContext.current
    NavigationRail(modifier = Modifier) {
        NavigationRailItem(
            selected = false,
            onClick = goHome,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                )
            },
        )
        NavigationRailItem(
            selected = false,
            onClick = saveConcertsToApi,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = "Save All",
                )
            },
        )
        NavigationRailItem(
            selected = false,
            onClick = { onLogout(context) },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = "Logout",
                )
            },
        )
    }
}