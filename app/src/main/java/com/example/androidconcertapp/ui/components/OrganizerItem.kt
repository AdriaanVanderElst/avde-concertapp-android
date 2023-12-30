package com.example.androidconcertapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** The OrganizerItem component of the ConcertDetails.
 *
 * @param modifier The modifier.
 * @param v1 The first value.
 * @param v2 The second value.
 * @param v3 The third value.
 */
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