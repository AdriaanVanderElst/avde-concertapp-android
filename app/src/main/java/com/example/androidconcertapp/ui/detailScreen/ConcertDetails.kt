package com.example.androidconcertapp.ui.detailScreen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.Default
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidconcertapp.model.Concert
import com.example.androidconcertapp.ui.listScreen.ConcertApiState
import com.example.androidconcertapp.ui.listScreen.ConcertListViewModel
import com.example.androidconcertapp.ui.listScreen.ConcertViewState
import com.example.androidconcertapp.ui.theme.ConcertAppTheme

@Composable
fun ConcertDetails(
    id: Int,
    viewModel: ConcertListViewModel,
) {

    val concertViewState by viewModel.uiState.collectAsState()

    ConcertDetailsComponent(
        concertViewState = concertViewState,
        onCommentChange = { viewModel.setNewComment(it) },
        onAddComment = { viewModel.addComment() },
    )

}

@Composable
fun ConcertDetailsComponent (
    concertViewState: ConcertViewState,
    onCommentChange: (String) -> Unit,
    onAddComment: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(
            rememberScrollState()
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(modifier = Modifier
                .padding(24.dp)
                .defaultMinSize(minHeight = 80.dp)) {
                Text(
                    text = "${concertViewState.concertDetail?.name}",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            DetailItem(
                v1 = "Datum: ${concertViewState.concertDetail?.date}",
                v2 = "om ${concertViewState.concertDetail?.time}",
            )

            DetailItem(
                v1 = "Adres: ${concertViewState.concertDetail?.address}",
                v2 = "${concertViewState.concertDetail?.city}",
            )

            OrganizerItem(
                v1 = "Organisator: ${concertViewState.concertDetail?.organizer}",
                v2 = "${concertViewState.concertDetail?.phoneNr}",
                v3 = "${concertViewState.concertDetail?.email}",
            )

            DetailItem(
                v1 = "Prijs: ${concertViewState.concertDetail?.price}",
                v2 = concertViewState.concertDetail?.isConfirmed.let {
                    if (it == true) "bevestigd" else "niet bevestigd"
                }
            )


            EditComment(comment = concertViewState.newComment,
                onCommentChange = { onCommentChange(it) },
                onAddComment = { onAddComment() })

        }
    }
}

@Composable
fun OrganizerItem(modifier: Modifier = Modifier, v1: String, v2: String, v3: String) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        ) {
        Text(text = v1)
        Column(
            modifier = Modifier.padding(start = 30.dp)
        ) {
            Text(text = v2)
            Text(text = v3)
        }
    }
}

@Composable
fun DetailItem(
    modifier: Modifier = Modifier,
    v1: String,
    v2: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),

    ) {
        Text(modifier =modifier.padding(end = 20.dp) ,  text = v1)
        Text(text = v2)
    }
}

@Preview
@Composable
fun ConcertDetailsComponentPreview() {
    ConcertAppTheme {
        ConcertDetailsComponent(
            concertViewState = ConcertViewState(
                concertDetail = Concert(
                    1,
                    "previewconcert",
                    "10-7-23",
                    "time",
                    0,
                    false,
                    1,
                    "address",
                    "city",
                    1,
                    "organizer",
                    "phoneNr",
                    "email",
                    "User",
                    "Comment",
                ),
                newComment = "newComment",
            ),
            onCommentChange = {},
            onAddComment = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.tertiaryContainer),
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun EditComment(comment: String, onCommentChange: (String) -> Unit, onAddComment: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 150.dp),
            value = comment,
            onValueChange = {
                onCommentChange(it)
            },
            label = { Text("comment") },

        )

            TextButton(
                onClick = {
                    onAddComment()
                },
            ) {
                Text("sla op")
            }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun EditCommentPreview() {
    ConcertAppTheme {
        EditComment(
            comment = "comment",
            onCommentChange = {},
            onAddComment = {},
        )
    }
}