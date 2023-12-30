package com.example.androidconcertapp.ui.listScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidconcertapp.model.Concert
import com.example.androidconcertapp.ui.components.ConcertRow
import com.example.androidconcertapp.ui.theme.ConcertAppTheme
import kotlinx.coroutines.launch

/**
 * The ConcertList screen.
 *
 * @param goToDetail The navigation function to the ConcertDetails screen.
 * @param viewModel The [ConcertListViewModel] used by the List and Detail screens.
 */
@Composable
fun ConcertList(
    goToDetail: (id: Int) -> Unit,
    viewModel: ConcertListViewModel = viewModel(factory = ConcertListViewModel.Factory),
) {
    val concertViewState by viewModel.uiState.collectAsState()
    val concertListState by viewModel.uiListState.collectAsState()
    val concertApiState = viewModel.concertApiState



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

/**
 * The ConcertList component.
 *
 * @param concertViewState The [ConcertViewState] used by the List and Detail screens.
 * @param concertListState The [ConcertListState] used by the List and Detail screens.
 * @param goToDetail The navigation function to the ConcertDetails screen.
 */
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
                        1,
                        "address",
                        "city",
                        1,
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
