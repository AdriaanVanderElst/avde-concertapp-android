package com.example.androidconcertapp.ui

import androidx.annotation.StringRes
import com.example.androidconcertapp.R

enum class ConcertScreen(@StringRes val title: Int) {
    List(title = R.string.app_name),
    Detail(title = R.string.detail),
}
