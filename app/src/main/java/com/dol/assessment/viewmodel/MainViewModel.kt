package com.dol.assessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dol.assessment.data.model.Post
import com.dol.assessment.data.paging.PostPagingSource
import com.dol.assessment.data.repositories.ApiRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repositoryImpl: ApiRepositoryImpl) :
    ViewModel() {

    companion object {
        const val PAGE_SIZE = 10
    }



    val posts: Flow<PagingData<Post>> = getPagedPosts()

    private fun getPagedPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { PostPagingSource(repositoryImpl) }
        ).flow.cachedIn(viewModelScope)
    }
}



