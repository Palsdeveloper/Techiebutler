package com.techiebutler.techiebutler.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.plcoding.composepaging3caching.data.mappers.toPost
import com.techiebutler.techiebutler.data.local.PostEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    pager: Pager<Int, PostEntity>
) : ViewModel() {
    val postPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toPost() }
        }
        .cachedIn(viewModelScope)
}