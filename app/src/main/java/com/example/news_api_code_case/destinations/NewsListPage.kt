package com.example.news_api_code_case.destinations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.news_api_code_case.R
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

    val searchQuery by vm.searchQuery.collectAsState()
    val articles = vm.newsList.collectAsLazyPagingItems()
    val totalResult by vm.totalResults.collectAsState()
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = vm::onSearchQueryChanged,
            singleLine = true,
            label = { Text(text = stringResource(R.string.label_search)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        when {

            articles.loadState.refresh is LoadState.Loading || totalResult != articles.itemCount -> Box(
                modifier = Modifier.weight(1f)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            searchQuery == "" -> Box(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.label_empty_search_query),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            articles.itemCount == 0 -> Box(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.label_search_no_results),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> LazyColumn(
                contentPadding = PaddingValues(
                    10.dp
                )
            ) {
                items(articles) {
                    ArticleListItem(article = it, onClick = vm::articleOnClick)
                }
            }
        }

    }
}