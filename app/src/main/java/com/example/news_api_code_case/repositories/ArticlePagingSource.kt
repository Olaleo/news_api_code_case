package com.example.news_api_code_case.repositories

import android.content.res.Resources
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.news_api_code_case.model.Article
import com.example.news_api_code_case.network.RetrofitInterface
import com.haroldadmin.cnradapter.NetworkResponse
import java.lang.Integer.max

class ArticlePagingSource(
    private val retrofitInterface: RetrofitInterface,
    private val searchQuery: String,
    private val onTotalResultsReceived: (Int) -> Unit
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        if (searchQuery == "") {
            return LoadResult.Error(Resources.NotFoundException())
        }

        val page = params.key ?: 1

        return when (val res =
            retrofitInterface.everyThing(
                searchTerm = searchQuery,
                page = params.key ?: 1,
                pageSize = params.loadSize
            )) {
            is NetworkResponse.Success -> {

                onTotalResultsReceived(res.body.totalResults)

                val itemsAfter = max(res.body.totalResults - (params.loadSize * page), 0)
                LoadResult.Page(
                    data = res.body.articles,
                    prevKey = null,
                    nextKey = if (itemsAfter == 0) {
                        null
                    } else {
                        page + 1
                    },
                    itemsAfter = itemsAfter,
                    itemsBefore = params.loadSize * (page - 1)
                )
            }
            is NetworkResponse.ServerError -> return LoadResult.Error(Resources.NotFoundException())
            is NetworkResponse.NetworkError -> return LoadResult.Error(Resources.NotFoundException())
            is NetworkResponse.UnknownError -> TODO()
        }
    }
}