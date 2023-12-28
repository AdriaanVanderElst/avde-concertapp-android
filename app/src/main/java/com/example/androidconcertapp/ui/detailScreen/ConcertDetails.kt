package com.example.androidconcertapp.ui.detailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidconcertapp.ui.listScreen.ConcertApiState

@Composable
fun ConcertDetails(
    id: Int,
    viewModel: ConcertDetailViewModel,
) {

    val concertState by viewModel.uiConcertState.collectAsState()
    val concertDetailState by viewModel.uiState.collectAsState()

    val concertApiState = viewModel.concertApiState

    Box {
        when (concertApiState) {
            is ConcertApiState.Loading -> Text("Loading...")
            is ConcertApiState.Error -> Text("Couldn't load...")
            is ConcertApiState.Success -> ConcertDetailComponent(
                concertState = concertState,
                concertDetailState = concertDetailState,
                viewModel = viewModel,
            )

        }
    }
}

@Composable
fun ConcertDetailComponent(
    concertState: ConcertState,
    concertDetailState: ConcertDetailState,
    viewModel: ConcertDetailViewModel
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = concertState.concert.name,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Datum: ${concertState.concert.date}")
                Text(text = "Tijd: ${concertState.concert.time}")
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = concertState.concert.address)
                Text(text = concertState.concert.city)
            }
            Text(text = concertState.concert.price.toString())
            Text(text = concertState.concert.email)

            Row(modifier = Modifier.padding(16.dp)) {

            }
            Row(modifier = Modifier.padding(16.dp)) {
                EditComment(comment = concertDetailState.comment,
                    onCommentChange = { viewModel.setNewComment(it) },
                    onAddComment = { viewModel.addComment(concertState.concert) })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditComment(comment: String, onCommentChange: (String) -> Unit, onAddComment: () -> Unit) {
    Column {
        OutlinedTextField(
            value = comment,
            onValueChange = { onCommentChange(it) },
            label = { Text("comment") },
        )
        TextButton(
            onClick = { onAddComment() },
        ) {
            Text("Save")
        }
    }
}