package com.example.news_api_code_case

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.news_api_code_case.destinations.NavGraphs
import com.example.news_api_code_case.ui.theme.News_api_code_caseTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            News_api_code_caseTheme {
                // A surface container using the 'background' color from the theme

                Scaffold {
                    DestinationsNavHost(modifier = Modifier.padding(it), navGraph = NavGraphs.root)
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