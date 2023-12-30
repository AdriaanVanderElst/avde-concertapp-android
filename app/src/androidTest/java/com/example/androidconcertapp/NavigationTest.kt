package com.example.androidconcertapp

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.androidconcertapp.data.UserRepository
import com.example.androidconcertapp.model.User
import com.example.androidconcertapp.ui.ConcertApp
import com.example.androidconcertapp.ui.loginScreen.LocalUserState
import com.example.androidconcertapp.ui.loginScreen.UserStateViewModel
import com.example.androidconcertapp.ui.navigation.NavigationType
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FakeUserRepository : UserRepository {
    override suspend fun addUser(user: User) {
        "Do nothing"
    }
}


class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController
    private val userStateTest = UserStateViewModel(FakeUserRepository())

    @Before
    fun setupNavHost() {
        composeTestRule.setContent {

            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            CompositionLocalProvider(LocalUserState provides userStateTest) {
                ConcertApp(
                    navController = navController,
                    navigationType = NavigationType.BOTTOM_NAVIGATION
                )
            }
        }
    }

    @Test
    fun verifyStartDestination() {
        composeTestRule
            .onNodeWithText("Overzicht")
            .assertIsDisplayed()
    }

    @Test
    fun navigateToHome() {
        composeTestRule
            .onNodeWithContentDescription("navigate to home page")
            .performClick()
        composeTestRule
            .onNodeWithText("Overzicht")
            .assertIsDisplayed()
    }

    // This won't work -- Only works when data is loaded from ConcertRepository
//    @Test
//    fun navigateToDetail() {
//        composeTestRule
//            .onNodeWithContentDescription("ConcertItem")
//            .assertIsDisplayed()
//        composeTestRule
//            .onNodeWithContentDescription("ConcertItem")
//            .performClick()
//        composeTestRule
//            .onNodeWithText("Concert Detail")
//            .assertIsDisplayed()
//    }

    // This won't work -- Auth0
//    @Test
//    fun navigateToLogOut() {
//        composeTestRule
//            .onNodeWithContentDescription("navigate to login page")
//            .performClick()
//        composeTestRule
//            .onNodeWithText("Concert Planner")
//            .assertIsDisplayed()
//    }

}