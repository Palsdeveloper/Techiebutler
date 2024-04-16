package com.techiebutler.techiebutler.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.plcoding.composepaging3caching.domain.Post

@Composable
fun PostScreen(
    posts: LazyPagingItems<Post>,
    navController: NavController,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = posts.loadState) {
        if (posts.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error:" + (posts.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (posts.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(posts) { post ->
                    if (post != null) {
                        val itemIndex = post.id - 1
                        PostItem(
                            post = post,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(route = "DetailScreen/$itemIndex")
                                },
                        )
                    }
                }
                item {
                    if (posts.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}