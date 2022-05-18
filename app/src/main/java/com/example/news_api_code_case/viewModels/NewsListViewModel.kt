package com.example.news_api_code_case.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_api_code_case.destinations.destinations.ArticlePageDestination
import com.example.news_api_code_case.model.Article
import com.example.news_api_code_case.repositories.NewsRepository
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    newsRepository: NewsRepository
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<Direction>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _newsList = MutableStateFlow(listOf<Article>())
    val newsList = _newsList.asStateFlow()

    init {
        viewModelScope.launch { _newsList.emit(newsRepository.getNewsList()) }
    }

    fun articleOnClick(article: Article) {
        viewModelScope.launch {
            _navigationEvent.emit(ArticlePageDestination(article))
        }
    }

}