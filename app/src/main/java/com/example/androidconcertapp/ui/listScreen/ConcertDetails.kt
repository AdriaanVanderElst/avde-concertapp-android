package com.example.androidconcertapp.ui.listScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConcertDetails(
    id: Int,
    viewModel: ConcertViewModel,
) {
    val uiListState by viewModel.uiListState.collectAsState()
    val concert = uiListState.find { it.id == id }

    if (concert != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(32.dp)) {
                Text(text = concert.name)
                Text(text = concert.date)
                Text(text = concert.address)
                Text(text = concert.city)
                Text(text = concert.price.toString())
                Text(text = concert.email)
            }
        }
    }
}
