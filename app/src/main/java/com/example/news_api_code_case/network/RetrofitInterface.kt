package com.example.news_api_code_case.network

import com.example.news_api_code_case.model.ErrorModel
import com.example.news_api_code_case.network.responses.EveryThingResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("everything")
    suspend fun everyThing(@Query("q") searchTerm: String): NetworkResponse<EveryThingResponse, ErrorModel>
}