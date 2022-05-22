package com.example.news_api_code_case.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.news_api_code_case.model.Article
import com.example.news_api_code_case.network.RetrofitInterface
import javax.inject.Inject

//@Singleton
class NewsRepository @Inject constructor(private val retrofitInterface: RetrofitInterface) {

    fun getNewsPagingSource(
        searchQuery: String,
        onTotalResultsReceived: (Int) -> Unit
    ): Pager<Int, Article> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                initialLoadSize = 20
            )
        ) {
            ArticlePagingSource(
                retrofitInterface,
                searchQuery = searchQuery,
                onTotalResultsReceived = onTotalResultsReceived
            )
        }
    }
}