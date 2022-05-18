package com.example.news_api_code_case.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_api_code_case.model.Article
import com.example.news_api_code_case.repositories.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    newsRepository: NewsRepository
) : ViewModel() {

    private val _newsList = MutableStateFlow(listOf<Article>())
    val newsList = _newsList.asStateFlow()

    init {
        viewModelScope.launch { _newsList.emit(newsRepository.getNewsList()) }
    }

}