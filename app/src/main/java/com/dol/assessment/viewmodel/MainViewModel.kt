package com.dol.assessment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dol.assessment.data.model.PostsResponse
import com.dol.assessment.data.repositories.ApiRepositoryImpl
import com.dol.assessment.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeState(
    val isLoading: Boolean = false,
    val postsList: PostsResponse? = null,
    val isError: Boolean = false
)

@HiltViewModel
class MainViewModel @Inject constructor(private val repositoryImpl: ApiRepositoryImpl) :
    ViewModel() {

    private val _posts = MutableStateFlow(HomeState())
    val posts: StateFlow<HomeState> = _posts.asStateFlow()

    fun getPosts() {
        viewModelScope.launch {
            _posts.update {
                it.copy(
                    isLoading = true
                )
            }

            repositoryImpl.getPosts().collect { result ->
                when(result) {
                    is NetworkResult.Loading -> {

                    }
                    is NetworkResult.Error -> {

                    }
                    is NetworkResult.Success -> {
//                        _posts.emit(
//                            HomeState().copy(
//                                isLoading = false, postsList = result.data, isError = false
//                            )
//                        )
                        _posts.update {
                            it.copy(
                                isLoading = false, postsList = result.data, isError = false
                            )
                        }
                    }
                }
            }
        }
    }
}