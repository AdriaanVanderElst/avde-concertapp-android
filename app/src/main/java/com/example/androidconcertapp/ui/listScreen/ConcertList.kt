package com.example.androidconcertapp.ui.listScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidconcertapp.model.Concert
import com.example.androidconcertapp.ui.theme.ConcertAppTheme
import kotlinx.coroutines.launch

@Composable
fun ConcertList(
    goToDetail: (id: Int) -> Unit,
    viewModel: ConcertListViewModel = viewModel(factory = ConcertListViewModel.Factory),
) {
    val concertViewState by viewModel.uiState.collectAsState()

    val concertApiState = viewModel.concertApiState
    val concertListState by viewModel.uiListState.collectAsState()

    Box {
        when (concertApiState) {
            is ConcertApiState.Loading -> Text("Loading...")
            is ConcertApiState.Error -> Text("Couldn't load...")
            is ConcertApiState.Success -> ConcertListComponent(
                concertViewState = concertViewState,
                concertListState = concertListState,
                goToDetail = goToDetail,
            )
        }
    }
}

@Composable
fun ConcertListComponent(
    concertViewState: ConcertViewState,
    goToDetail: (id: Int) -> Unit,
    concertListState: ConcertListState,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        items(concertListState.concertList) {
            ConcertRow(concert = it, goToDetail = goToDetail)
        }
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(concertViewState.scrollActionIndex) {
        if (concertViewState.scrollActionIndex != 0) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(concertViewState.scrollToItemIndex)
            }
        }
    }
}

@Composable
fun ConcertRow(
    modifier: Modifier = Modifier,
    concert: Concert,
    goToDetail: (id: Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.shapes.small)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clickable {
                goToDetail(concert.id)
            },
    ) {
        Column(
            modifier = modifier.padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = concert.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = concert.city,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = concert.date,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f),
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("bevestigd: ")
                    Checkbox(checked = concert.isConfirmed, onCheckedChange = null)
                }
            }
        }
    }
}

// @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
fun ConcertListComponentPreview() {
    ConcertAppTheme {
        ConcertListComponent(
            concertViewState = ConcertViewState(),
            goToDetail = {},
            concertListState = ConcertListState(
                listOf(
                    Concert(
                        1,
                        "previewconcert",
                        "date",
                        "time",
                        0,
                        false,
                        "address",
                        "city",
                        "organizer",
                        "phoneNr",
                        "email",
                        "user",
                        "comment"
                    )
                )
            ),
        )
    }
}
