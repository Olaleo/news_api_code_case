package com.example.news_api_code_case.viewModels

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_api_code_case.destinations.destinations.ArticlePageDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    val article = derivedStateOf { ArticlePageDestination.argsFrom(savedStateHandle).article }

    private val _intentEvent = MutableSharedFlow<Intent>()
    val intentEvent = _intentEvent.asSharedFlow()

    fun goToArticleOnClick() {
        viewModelScope.launch {
            val article by article
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            _intentEvent.emit(browserIntent)
        }
    }
}