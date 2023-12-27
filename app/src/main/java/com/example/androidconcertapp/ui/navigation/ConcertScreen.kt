package com.example.androidconcertapp.ui.navigation

import androidx.annotation.StringRes
import com.example.androidconcertapp.R

enum class ConcertScreen(@StringRes val title: Int) {
    List(title = R.string.app_name),
    Detail(title = R.string.detail),
}
