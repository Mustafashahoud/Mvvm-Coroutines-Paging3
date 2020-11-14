package com.mustafa.newsapp.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mustafa.newsapp.repository.BreakingNewsRepository

class BreakingNewsViewModel @ViewModelInject constructor(
        repository: BreakingNewsRepository
) : ViewModel() {

    val breakingNewsStream = repository.getBreakingNewsStream().cachedIn(viewModelScope)


}