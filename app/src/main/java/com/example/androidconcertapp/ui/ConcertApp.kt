package com.example.androidconcertapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.androidconcertapp.ui.components.ConcertAppBottomBar
import com.example.androidconcertapp.ui.components.ConcertAppTopBar
import com.example.androidconcertapp.ui.listScreen.ConcertViewModel
import com.example.androidconcertapp.ui.loginScreen.UserState
import com.example.androidconcertapp.ui.navigation.ConcertScreen
import com.example.androidconcertapp.ui.navigation.NavComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConcertApp(
    navController: NavHostController = rememberNavController(),
    sharedViewModel: ConcertViewModel = viewModel(factory = ConcertViewModel.Factory),
) {
    val userStateVm = UserState.current
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = navController.previousBackStackEntry != null
    val navigateUp: () -> Unit = { navController.navigateUp() }

    val goHome: () -> Unit = {
        navController.popBackStack(
            ConcertScreen.List.name,
            inclusive = false,
        )
    }
//    val goHomeAfterLogin: () -> Unit = {
//        navController.navigate(ConcertScreen.List.name) {
//            popUpTo(ConcertScreen.Login.name) {
//                inclusive = true
//            }
//        }
//    }

//    val goToLogin: () -> Unit = {
//        navController.navigate(ConcertScreen.Login.name) {
//            popUpTo(ConcertScreen.List.name) {
//                inclusive = true
//            }
//        }
//    }

    val goToDetail: (id: Int) -> Unit = { id ->
        navController.navigate("${ConcertScreen.Detail.name}/$id") {
            launchSingleTop = true
        }
    }
    val currentScreenTitle = ConcertScreen.valueOf(
        backStackEntry?.destination?.route?.substringBefore("/") ?: ConcertScreen.List.name,
    ).title

    if (userStateVm.isBusy) {
        Column(
            modifier = Modifier.padding(all = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            topBar = {
                ConcertAppTopBar(
                    canNavigateBack = canNavigateBack,
                    navigateUp = navigateUp,
                    currentScreenTitle = currentScreenTitle,
                )
            },
            bottomBar = { ConcertAppBottomBar(goHome) },
        ) { innerPadding ->

            NavComponent(
                navController = navController,
                sharedViewModel = sharedViewModel,
//            loginViewModel = loginViewModel,
                modifier = Modifier.padding(innerPadding),
//            goHomeAfterLogin = goHomeAfterLogin,
                goToDetail = goToDetail,
            )
        }
    }
}
