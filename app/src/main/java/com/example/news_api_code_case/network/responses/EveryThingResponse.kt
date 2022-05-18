package com.example.news_api_code_case.network.responses

import com.example.news_api_code_case.model.Article

data class EveryThingResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
