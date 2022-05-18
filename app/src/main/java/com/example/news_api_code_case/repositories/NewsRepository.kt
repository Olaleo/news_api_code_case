package com.example.news_api_code_case.repositories

import com.example.news_api_code_case.model.Article
import com.example.news_api_code_case.network.RetrofitInterface
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

//@Singleton
class NewsRepository @Inject constructor(private val retrofitInterface: RetrofitInterface) {

    suspend fun getNewsList(): List<Article> {
      when (val res = retrofitInterface.everyThing()){
          is NetworkResponse.Success -> return res.body.articles
          is NetworkResponse.ServerError -> TODO()
          is NetworkResponse.NetworkError -> TODO()
          is NetworkResponse.UnknownError -> TODO()
      }
    }

}