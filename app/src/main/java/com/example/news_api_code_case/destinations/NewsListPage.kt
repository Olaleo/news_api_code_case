package com.example.news_api_code_case.destinations

import android.text.format.DateFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news_api_code_case.model.Article
import com.example.news_api_code_case.util.observeWithLifecycle
import com.example.news_api_code_case.viewModels.NewsListViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage


@RootNavGraph(start = true)
@Destination()
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
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clickable {
                            vm.articleOnClick(it)
                        },
                    elevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.height(IntrinsicSize.Min),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GlideImage(
                            imageModel = it.urlToImage,
                            shimmerParams = ShimmerParams(
                                baseColor = MaterialTheme.colors.background,
                                highlightColor = MaterialTheme.colors.secondary,
                                durationMillis = 500,
                                dropOff = 0.65f,
                                tilt = 20f
                            ), modifier = Modifier.sizeIn(maxHeight = 100.dp, maxWidth = 100.dp)
                        )
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = it.title,
                                modifier = Modifier,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = DateFormat.getDateFormat(LocalContext.current)
                                        .format(it.publishedAt),
                                    modifier = Modifier,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}