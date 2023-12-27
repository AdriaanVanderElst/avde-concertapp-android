package com.example.androidconcertapp.ui.listScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ConcertDetails(
    id: Int,
) {
    Text(text = "Concert Details: $id")
}
