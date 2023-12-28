package com.example.androidconcertapp.ui.listScreen

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConcertDetails(
    id: Int,
    viewModel: ConcertViewModel,
) {
    val concertListState by viewModel.uiListState.collectAsState()
    val concertState by viewModel.uiState.collectAsState()
    val concert = concertListState.concertList.find { it.id == id }

    if (concert != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Text(text = concert.name, style = MaterialTheme.typography.headlineMedium)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Datum: ${concert.date}")
                    Text(text = "Tijd: ${concert.time}")
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = concert.address)
                    Text(text = concert.city)
                }
                Text(text = concert.price.toString())
                Text(text = concert.email)

                Row(modifier = Modifier.padding(16.dp)) {

                }
                Row(modifier = Modifier.padding(16.dp)) {
                    EditComment(
                        comment = concertState.comment,
                        onCommentChange = { viewModel.setNewComment(it) },
                        onAddComment = { viewModel.addComment(concert) })
                }

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