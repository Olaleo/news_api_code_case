package com.example.news_api_code_case

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.news_api_code_case.destinations.NavGraphs
import com.example.news_api_code_case.destinations.appDestination
import com.example.news_api_code_case.destinations.destinations.ArticlePageDestination
import com.example.news_api_code_case.destinations.destinations.Destination
import com.example.news_api_code_case.destinations.destinations.NewsListPageDestination
import com.example.news_api_code_case.destinations.startAppDestination
import com.example.news_api_code_case.ui.theme.News_api_code_caseTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            News_api_code_caseTheme {
                // A surface container using the 'background' color from the theme

                val navController = rememberNavController()
                val currentDestination by navController.currentBackStackEntryAsState()
                val destination = currentDestination?.appDestination()
                    ?: NavGraphs.root.startRoute.startAppDestination
                Scaffold(topBar = {

                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(
                                    destination.title
                                )
                            )
                        },
                        elevation = 0.dp,
                        modifier = Modifier,
                        navigationIcon = when (destination) {
                            NewsListPageDestination -> null
                            else -> {
                                {
                                    IconButton(onClick = {
                                        navController.popBackStack()
                                    }) {
                                        Icon(Icons.Default.ArrowBack, null)
                                    }
                                }
                            }
                        }
                    )
                }) {
                    DestinationsNavHost(
                        modifier = Modifier.padding(it),
                        navGraph = NavGraphs.root,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    News_api_code_caseTheme {
        Greeting("Android")
    }
}

@get:StringRes
val Destination.title
    get(): Int {
        return when (this) {
            ArticlePageDestination -> R.string.page_articles_title
            NewsListPageDestination -> R.string.page_news_list_title
        }
    }