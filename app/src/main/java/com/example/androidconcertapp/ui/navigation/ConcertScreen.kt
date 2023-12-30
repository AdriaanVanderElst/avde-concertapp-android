package com.example.androidconcertapp.ui.navigation

import androidx.annotation.StringRes
import com.example.androidconcertapp.R

/**
 * The screens available in the app.
 *
 * @property title The string resource ID for the title of the screen.
 */
enum class ConcertScreen(@StringRes val title: Int) {
    Login(title = R.string.login),
    List(title = R.string.list),
    Detail(title = R.string.detail),
}
