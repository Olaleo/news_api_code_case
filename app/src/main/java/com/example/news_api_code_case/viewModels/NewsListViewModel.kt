package com.example.news_api_code_case.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.news_api_code_case.destinations.destinations.ArticlePageDestination
import com.example.news_api_code_case.model.Article
import com.example.news_api_code_case.repositories.NewsRepository
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _totalResults = MutableStateFlow(0)
    val totalResults = _totalResults.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val newsList = searchQuery.debounce(500).map {
        newsRepository.getNewsPagingSource(it) { viewModelScope.launch { _totalResults.emit(it) } }.flow
    }.flatMapLatest { it }

    fun articleOnClick(article: Article) {
        viewModelScope.launch {
            _navigationEvent.emit(ArticlePageDestination(article))
        }
    }

    fun onSearchQueryChanged(searchQuery: String) {
        viewModelScope.launch {
            _searchQuery.emit(searchQuery)
        }
    }

    sealed class NewsListState {
        object Empty : NewsListState()
        class Populated(val list: Flow<PagingData<Article>>) : NewsListState()
    }

}