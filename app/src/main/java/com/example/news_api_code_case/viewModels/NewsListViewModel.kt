package com.example.news_api_code_case.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_api_code_case.destinations.destinations.ArticlePageDestination
import com.example.news_api_code_case.model.Article
import com.example.news_api_code_case.repositories.NewsRepository
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    newsRepository: NewsRepository
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<Direction>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _searchTerm = MutableStateFlow("")
    val searchTerm = _searchTerm.asStateFlow()

    @OptIn(FlowPreview::class)
    val newsList = searchTerm.debounce(500).map {
        when (it) {
            "" -> NewsListState.Empty
            else -> NewsListState.Populated(newsRepository.getNewsList(it))
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, NewsListState.Empty)

    fun articleOnClick(article: Article) {
        viewModelScope.launch {
            _navigationEvent.emit(ArticlePageDestination(article))
        }
    }

    fun onSearchTermsChanged(searchTerm: String) {
        viewModelScope.launch {
            _searchTerm.emit(searchTerm)
        }
    }

    sealed class NewsListState {
        object Empty : NewsListState()
        class Populated(val list: List<Article>) : NewsListState()
    }

}