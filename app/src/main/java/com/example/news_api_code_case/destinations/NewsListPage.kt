package com.example.news_api_code_case.destinations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news_api_code_case.model.Article
import com.example.news_api_code_case.ui.layout.ArticleListItem
import com.example.news_api_code_case.util.observeWithLifecycle
import com.example.news_api_code_case.viewModels.NewsListViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@RootNavGraph(start = true)
@Destination
@Composable
fun NewsListPage(navigator: DestinationsNavigator) {
    val vm: NewsListViewModel = hiltViewModel()

    vm.navigationEvent.observeWithLifecycle(action = {
        navigator.navigate(
            it,
            onlyIfResumed = true
        )
    })

    val articles: List<Article> by vm.newsList.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(contentPadding = PaddingValues(10.dp)) {
            items(articles) {
                ArticleListItem(article = it, onClick = vm::articleOnClick)
            }
        }
    }
}