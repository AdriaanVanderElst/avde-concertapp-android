package com.example.androidconcertapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.androidconcertapp.model.Concert
import com.example.androidconcertapp.ui.components.ConcertAppBottomBar
import com.example.androidconcertapp.ui.components.ConcertAppTopBar
import com.example.androidconcertapp.ui.navigation.ConcertScreen
import com.example.androidconcertapp.ui.navigation.NavComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConcertApp(
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = navController.previousBackStackEntry != null
    val navigateUp: () -> Unit = { navController.navigateUp() }
    //
    val goHome: () -> Unit = {
        navController.popBackStack(
            ConcertScreen.List.name,
            inclusive = false,
        )
    }
    val goToDetail: (c: Concert) -> Unit = { c ->
        navController.navigate("${ConcertScreen.Detail.name}/${c.id}") { launchSingleTop = true }
    }
//    val currentScreenTitle = ConcertScreen.valueOf(
//        backStackEntry?.destination?.route ?: ConcertScreen.List.name,
//    ).title
    val currentScreenTitle = ConcertScreen.List.title

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
        NavComponent(navController = navController, modifier = Modifier.padding(innerPadding), goToDetail = goToDetail)
    }
}
