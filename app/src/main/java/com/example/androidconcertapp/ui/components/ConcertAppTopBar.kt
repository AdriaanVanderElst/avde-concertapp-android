package com.example.androidconcertapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.androidconcertapp.ui.loginScreen.LocalUserState

/**
 * The ConcertAppTopBar component.
 *
 * @param canNavigateBack The boolean to check if the user can navigate back.
 * @param navigateUp The function to navigate back.
 * @param currentScreenTitle The title of the current screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConcertAppTopBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    currentScreenTitle: Int,
) {
    val viewModel = LocalUserState.current
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(id = currentScreenTitle))
                Text(
                    text = viewModel.user.name + " ")
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "go back")
                }
            }
        },
    )
}
