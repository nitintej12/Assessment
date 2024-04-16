package com.dol.assessment.data.repositories

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.dol.assessment.data.model.Post
import com.dol.assessment.data.model.PostsResponse
import com.dol.assessment.utils.NetworkResult
import com.dol.assessment.data.network.ApiService
import com.dol.assessment.data.paging.PostPagingSource
import com.dol.assessment.utils.BaseApiResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.math.log

@ActivityRetainedScoped
class ApiRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    BaseApiResponse() {
    suspend fun getPosts(page : Int): Flow<NetworkResult<List<Post>>> {
        return flow {
            emit(safeApiCall { apiService.getPosts(page) })
        }.flowOn(Dispatchers.IO)
    }
}