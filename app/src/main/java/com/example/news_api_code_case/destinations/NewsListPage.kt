package com.example.news_api_code_case.destinations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

    val searchTerm by vm.searchTerm.collectAsState()
    val articles by vm.newsList.collectAsState()
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = searchTerm,
            onValueChange = vm::onSearchTermsChanged,
            singleLine = true,
            label = { Text(text = stringResource(R.string.label_search)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        when (articles) {
            NewsListViewModel.NewsListState.Empty -> Box(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.label_empty_search_term),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is NewsListViewModel.NewsListState.Populated -> LazyColumn(
                contentPadding = PaddingValues(
                    10.dp
                )
            ) {
                items((articles as NewsListViewModel.NewsListState.Populated).list) {
                    ArticleListItem(article = it, onClick = vm::articleOnClick)
                }
            }
        }

    }
}