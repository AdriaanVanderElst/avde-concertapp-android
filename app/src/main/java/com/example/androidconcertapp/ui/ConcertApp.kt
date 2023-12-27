package com.example.androidconcertapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.androidconcertapp.ui.components.ConcertAppBottomBar
import com.example.androidconcertapp.ui.components.ConcertAppTopBar
import com.example.androidconcertapp.ui.listScreen.ConcertListViewModel
import com.example.androidconcertapp.ui.navigation.ConcertScreen
import com.example.androidconcertapp.ui.navigation.NavComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConcertApp(
    navController: NavHostController = rememberNavController(),
    sharedViewModel: ConcertListViewModel = viewModel(factory = ConcertListViewModel.Factory),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = navController.previousBackStackEntry != null
    val navigateUp: () -> Unit = { navController.navigateUp() }

    val goHome: () -> Unit = {
        navController.popBackStack(
            ConcertScreen.List.name,
            inclusive = false,
        )
    }
    val goHomeAfterLogin: () -> Unit = {
        navController.navigate(ConcertScreen.List.name) {
            popUpTo(ConcertScreen.Login.name) {
                inclusive = true
            }
        }
    }

    val goToLogin: () -> Unit = {
        navController.navigate(ConcertScreen.Login.name) {
            popUpTo(ConcertScreen.List.name) {
                inclusive = true
            }
        }
    }

    val goToDetail: (id: Int) -> Unit = { id ->
        navController.navigate("${ConcertScreen.Detail.name}/$id") {
            launchSingleTop = true
        }
    }
    val currentScreenTitle = ConcertScreen.valueOf(
        backStackEntry?.destination?.route?.substringBefore("/") ?: ConcertScreen.List.name,
    ).title

    Scaffold(
        topBar = {
            ConcertAppTopBar(
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp,
                currentScreenTitle = currentScreenTitle,
            )
        },
        bottomBar = { ConcertAppBottomBar(goHome) { sharedViewModel.onLogout(goToLogin) } },
    ) { innerPadding ->
        NavComponent(
            navController = navController,
            sharedViewModel = sharedViewModel,
            modifier = Modifier.padding(innerPadding),
            goHomeAfterLogin = goHomeAfterLogin,
            goToDetail = goToDetail,
        )
    }
}
