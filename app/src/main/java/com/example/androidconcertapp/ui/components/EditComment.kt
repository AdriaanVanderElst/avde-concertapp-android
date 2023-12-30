package com.example.androidconcertapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidconcertapp.ui.theme.ConcertAppTheme

/**
 * The EditComment component of the ConcertDetails.
 *
 * @param comment The comment.
 * @param onCommentChange The function to change the comment.
 * @param onAddComment The function to add the comment.
 */
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