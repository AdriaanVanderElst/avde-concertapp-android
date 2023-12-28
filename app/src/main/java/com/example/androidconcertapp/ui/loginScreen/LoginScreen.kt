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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androidconcertapp.R
import com.example.androidconcertapp.utils.NetworkResponse

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    goHomeAfterLogin: () -> Unit,
) {
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = viewModel.loginState.value) {
        when (viewModel.loginState.value) {
            is NetworkResponse.Success -> {
                isLoading.value = false
                goHomeAfterLogin()
            }
            is NetworkResponse.Failure -> {
                isLoading.value = false
            }
            is NetworkResponse.Loading -> {
                isLoading.value = true
            }
        }
    }

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
                    viewModel.onLogin(context, onLoginNavigation = goHomeAfterLogin)
                },
            ) {
                Text(text = stringResource(R.string.login_button_text))
            }
        }
    }
}
