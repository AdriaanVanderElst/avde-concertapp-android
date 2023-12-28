package com.example.androidconcertapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidconcertapp.ui.detailScreen.ConcertDetailViewModel
import com.example.androidconcertapp.ui.detailScreen.ConcertDetails
import com.example.androidconcertapp.ui.listScreen.ConcertList
import com.example.androidconcertapp.ui.listScreen.ConcertListViewModel

@Composable
fun NavComponent(
    navController: NavHostController,
//    sharedViewModel: ConcertListViewModel,
//    loginViewModel: LoginViewModel,
    modifier: Modifier,
//    goHomeAfterLogin: () -> Unit,
    goToDetail: (id: Int) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = ConcertScreen.List.name,
        modifier = modifier,
    ) {
//        composable(route = ConcertScreen.Login.name) {
//            LoginScreen(viewModel = loginViewModel, goHomeAfterLogin = goHomeAfterLogin)
//        }
        composable(route = ConcertScreen.List.name) {
            ConcertList(viewModel = viewModel(factory = ConcertListViewModel.Factory), goToDetail = goToDetail)
        }
        composable(route = "${ConcertScreen.Detail.name}/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            ConcertDetails(viewModel = viewModel(factory = ConcertDetailViewModel.Factory(id)), id = id)
        }
    }
}
