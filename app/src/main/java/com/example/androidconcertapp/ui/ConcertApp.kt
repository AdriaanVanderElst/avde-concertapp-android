package com.example.androidconcertapp.ui

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.androidconcertapp.R
import com.example.androidconcertapp.ui.components.ConcertAppBottomBar
import com.example.androidconcertapp.ui.components.ConcertAppTopBar
import com.example.androidconcertapp.ui.components.ConcertNavigationRail
import com.example.androidconcertapp.ui.components.NavigationDrawerContent
import com.example.androidconcertapp.ui.listScreen.ConcertListViewModel
import com.example.androidconcertapp.ui.loginScreen.UserState
import com.example.androidconcertapp.ui.navigation.ConcertScreen
import com.example.androidconcertapp.ui.navigation.NavComponent
import com.example.androidconcertapp.ui.navigation.NavigationType

@Composable
fun ConcertApp(
    navigationType: NavigationType,
    navController: NavHostController = rememberNavController(),
    sharedViewModel: ConcertListViewModel = viewModel(factory = ConcertListViewModel.Factory),
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

    val goToDetail: (id: Int) -> Unit = { id ->
        sharedViewModel.setConcertDetail(id)
        navController.navigate("${ConcertScreen.Detail.name}/$id")
    }

    val saveConcertsToApi: () -> Unit = {
        sharedViewModel.saveConcertsToApi()
        goHome()
    }

    val onLogout: (Context) -> Unit = {
        userStateVm.onLogout(it)
    }

    val currentScreenTitle = ConcertScreen.valueOf(
        backStackEntry?.destination?.route?.substringBefore("/") ?: ConcertScreen.List.name,
    ).title

    if (userStateVm.isBusy) {
        Box(
            modifier = Modifier
                .width(100.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
            PermanentNavigationDrawer(drawerContent = {
                PermanentDrawerSheet(Modifier.width(dimensionResource(R.dimen.drawer_width))) {
                    NavigationDrawerContent(
                        goHome = goHome,
                        onLogout = onLogout,
                        saveConcertsToApi = saveConcertsToApi,
                        modifier = Modifier
                            .wrapContentWidth()
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                            .padding(dimensionResource(R.dimen.drawer_padding_content)),
                    )
                }
            }) {
                Scaffold(
                    topBar = {
                        ConcertAppTopBar(
                            canNavigateBack = canNavigateBack,
                            navigateUp = navigateUp,
                            currentScreenTitle = currentScreenTitle,
                        )
                    },
                ) { innerPadding ->
                    NavComponent(
                        navController = navController,
                        sharedViewModel = sharedViewModel,
                        modifier = Modifier.padding(innerPadding),
                        goToDetail = goToDetail,
                    )
                }
            }
        } else if (navigationType == NavigationType.BOTTOM_NAVIGATION) {

            Scaffold(
                topBar = {
                    ConcertAppTopBar(
                        canNavigateBack = canNavigateBack,
                        navigateUp = navigateUp,
                        currentScreenTitle = currentScreenTitle,
                    )
                },
                bottomBar = { ConcertAppBottomBar(goHome, saveConcertsToApi, onLogout) },
            ) { innerPadding ->
                NavComponent(
                    navController = navController,
                    sharedViewModel = sharedViewModel,
                    modifier = Modifier.padding(innerPadding),
                    goToDetail = goToDetail,
                )
            }

        } else {
            Row {
                AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
                    ConcertNavigationRail(
                        goHome = goHome, onLogout = onLogout, saveConcertsToApi = saveConcertsToApi
                    )
                }
                Scaffold(
                    topBar = {
                        ConcertAppTopBar(
                            canNavigateBack = canNavigateBack,
                            navigateUp = navigateUp,
                            currentScreenTitle = currentScreenTitle,
                        )
                    },
                ) { innerPadding ->
                    NavComponent(
                        navController = navController,
                        sharedViewModel = sharedViewModel,
                        modifier = Modifier.padding(innerPadding),
                        goToDetail = goToDetail,
                    )
                }
            }
        }

    }
}



