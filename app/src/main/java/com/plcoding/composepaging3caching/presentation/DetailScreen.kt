package com.plcoding.composepaging3caching.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.plcoding.composepaging3caching.domain.Post

@Composable
fun DetailScreen(
    post: Post?,
    itemIndex: Int?,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = post?.id.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .size(150.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .height(150.dp)
                    .padding(8.dp)
                    .weight(5f)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = post?.title.toString(),
                    style = MaterialTheme.typography.h6,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = post?.body.toString(),
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}