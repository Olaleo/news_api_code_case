package com.example.news_api_code_case.network

import com.example.news_api_code_case.model.ErrorModel
import com.example.news_api_code_case.network.responses.EveryThingResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET

interface RetrofitInterface {

    @GET("everything?q=apple")
    suspend fun everyThing(): NetworkResponse<EveryThingResponse, ErrorModel>
}