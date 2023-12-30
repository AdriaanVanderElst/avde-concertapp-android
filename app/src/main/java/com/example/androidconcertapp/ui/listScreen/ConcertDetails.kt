package com.example.androidconcertapp.ui.listScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidconcertapp.model.Concert
import com.example.androidconcertapp.ui.components.DetailItem
import com.example.androidconcertapp.ui.components.EditComment
import com.example.androidconcertapp.ui.components.OrganizerItem
import com.example.androidconcertapp.ui.theme.ConcertAppTheme

/**
 * The ConcertDetails screen.
 *
 * @param viewModel The [ConcertListViewModel] used by the List and Detail screens.
 */
@Composable
fun ConcertDetails(
    viewModel: ConcertListViewModel,
) {

    val concertViewState by viewModel.uiState.collectAsState()

    ConcertDetailsComponent(
        concertViewState = concertViewState,
        onCommentChange = { viewModel.setNewComment(it) },
        onAddComment = { viewModel.addComment() },
    )

}

/** The ConcertDetails component.
 *
 * @param concertViewState The [ConcertViewState] used by the List and Detail screens.
 * @param onCommentChange The function to change the comment in the textfield.
 * @param onAddComment The function to add the comment.
 * @param modifier The modifier.
 */
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