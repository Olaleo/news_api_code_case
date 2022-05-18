package com.example.news_api_code_case.destinations

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news_api_code_case.model.Article
import com.example.news_api_code_case.util.observeWithLifecycle
import com.example.news_api_code_case.viewModels.ArticleViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

data class ArticlePageArgs(val article: Article)

@OptIn(ExperimentalFoundationApi::class)
@Destination(navArgsDelegate = ArticlePageArgs::class)
@Composable
fun ArticlePage() {
    val vm: ArticleViewModel = hiltViewModel()

    val context = LocalContext.current
    vm.intentEvent.observeWithLifecycle(action = { context.startActivity(it) })

    val article by vm.article
    Column(modifier = Modifier.fillMaxSize()) {

        LazyColumn(modifier = Modifier, contentPadding = PaddingValues(all = 30.dp)) {
            item {
                GlideImage(
                    imageModel = article.urlToImage,
                    shimmerParams = ShimmerParams(
                        baseColor = MaterialTheme.colors.background,
                        highlightColor = MaterialTheme.colors.secondary,
                        durationMillis = 500,
                        dropOff = 0.65f,
                        tilt = 20f
                    ),
                    modifier = Modifier.shadow(elevation = 5.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
            stickyHeader {
                Text(
                    text = article.title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
            item {

                Column(modifier = Modifier) {
                    Text(
                        text = article.author,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        Button(
                            onClick = { vm.goToArticleOnClick() },
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Text(text = "Go to Article")
                        }
                    }

                    Text(
                        text = article.description,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Text(
                        text = "Source: ${article.source.name}",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
        }
    }
}