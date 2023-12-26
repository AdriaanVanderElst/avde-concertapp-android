package com.example.androidconcertapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidconcertapp.model.Concert
import kotlinx.coroutines.launch

@Composable
fun ConcertList(
    goToDetail: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    concertListViewModel: ConcertListViewModel = viewModel(factory = ConcertListViewModel.Factory),
) {
    val concertListState by concertListViewModel.uiState.collectAsState()

    val concertApiState = concertListViewModel.concertApiState

    Box() {
        when (concertApiState) {
            is ConcertApiState.Loading -> Text("Loading...")
            is ConcertApiState.Error -> Text("Couldn't load...")
            is ConcertApiState.Success -> ConcertListComponent(concertListState = concertListState, goToDetail = goToDetail)
        }
    }
}

@Composable
fun ConcertListComponent(concertListState: ConcertListState, goToDetail: (id: Int) -> Unit) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        items(concertListState.concertList) {
            ConcertRow(concert = it, goToDetail = goToDetail)
        }
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(concertListState.scrollActionIndex) {
        if (concertListState.scrollActionIndex != 0) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(concertListState.scrollToItemIndex)
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
            .padding(4.dp)
            .fillMaxWidth()
            .height(130.dp)
            .clickable {
                goToDetail(concert.id)
            },
    ) {
        Row() {
            Text(text = concert.name, fontSize = 24.sp)
        }
    }
}
