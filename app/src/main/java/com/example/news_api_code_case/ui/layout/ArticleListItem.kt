package com.example.news_api_code_case.ui.layout

import android.text.format.DateFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.news_api_code_case.model.Article
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ArticleListItem(article: Article?, onClick: (Article) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable(enabled = article != null) {
                onClick(article!!)
            },
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                imageModel = article?.urlToImage,
                failure = {
                    Image(
                        painter = ColorPainter(MaterialTheme.colors.primary),
                        contentDescription = null
                    )
                },
                shimmerParams = ShimmerParams(
                    baseColor = MaterialTheme.colors.background,
                    highlightColor = MaterialTheme.colors.secondary,
                    durationMillis = 500,
                    dropOff = 0.65f,
                    tilt = 20f
                ),
                circularReveal = CircularReveal(duration = 350),
                modifier = Modifier.size(100.dp, 100.dp),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            article?.let {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = article.title,
                        modifier = Modifier,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = DateFormat.getDateFormat(LocalContext.current)
                                .format(article.publishedAt),
                            modifier = Modifier,
                        )
                    }
                }
            }
        }
    }
}