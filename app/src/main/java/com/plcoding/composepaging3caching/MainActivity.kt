package com.plcoding.composepaging3caching

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.plcoding.composepaging3caching.presentation.DetailScreen
import com.plcoding.composepaging3caching.ui.theme.ComposePaging3CachingTheme
import com.techiebutler.techiebutler.presentation.PostScreen
import com.techiebutler.techiebutler.presentation.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePaging3CachingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val viewModel = hiltViewModel<PostViewModel>()
                    val posts = viewModel.postPagingFlow.collectAsLazyPagingItems()
                    NavHost(navController = navController, startDestination = "PostScreen") {
                        composable(route = "PostScreen") {
                            PostScreen(
                                posts = posts,
                                navController = navController
                            )
                        }
                        composable(route = "DetailScreen/{index}",
                            arguments = listOf(
                                navArgument(name = "index") {
                                    type = NavType.IntType
                                }
                            )
                        ) { index ->
                            DetailScreen(
                                post = index.arguments?.getInt("index")?.let { posts.get(it) },
                                itemIndex = index.arguments?.getInt("index"),
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
        }
    }
}