package com.example.news_api_code_case.model

data class ErrorModel(
    val status: String,
    val code: String,
    val message: String
)