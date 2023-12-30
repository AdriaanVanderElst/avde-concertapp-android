package com.example.androidconcertapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.androidconcertapp.model.Concert

/** The ConcertRow component of the ConcertList.
 *
 * @param modifier The modifier.
 * @param concert The concert.
 * @param goToDetail The function to navigate to the detail screen.
 */
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
            }
            .semantics { contentDescription = "ConcertItem" },
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