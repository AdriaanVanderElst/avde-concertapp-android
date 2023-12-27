package com.example.androidconcertapp.ui.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androidconcertapp.R
import com.example.androidconcertapp.ui.listScreen.ConcertListViewModel

@Composable
fun LoginScreen(
    viewModel: ConcertListViewModel,
    goHomeAfterLogin: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 32.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.kicks_logo_antraciet),
            contentDescription = stringResource(id = R.string.kicks_logo_content_description),
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    viewModel.onLogin(onLoginNavigation = goHomeAfterLogin)
                },
            ) {
                Text(text = stringResource(R.string.login_button_text))
            }
        }
    }
}
