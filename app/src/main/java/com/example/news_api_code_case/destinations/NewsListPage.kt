package com.example.news_api_code_case.destinations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.news_api_code_case.model.Article
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news_api_code_case.Greeting
import com.example.news_api_code_case.viewModels.NewsListViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination()
@Composable
fun NewsListPage(navigator: DestinationsNavigator) {
    val vm: NewsListViewModel = hiltViewModel()

//State
    val articles: State<List<Article>> = vm.newsList.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Greeting(name = "news list page")

        articles.value.forEach { androidx.compose.material.Text(text = it.title) }

    }
}