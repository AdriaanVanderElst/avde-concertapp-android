package com.example.androidconcertapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** The DetailItem component of the ConcertDetails.
 *
 * @param modifier The modifier.
 * @param v1 The first value.
 * @param v2 The second value.
 */
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
        Text(modifier = modifier.padding(end = 20.dp), text = v1)
        Text(text = v2)
    }
}