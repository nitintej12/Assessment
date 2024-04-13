package com.dol.assessment.data.repositories

import com.dol.assessment.data.model.PostsResponse
import com.dol.assessment.ui.NetworkResult
import com.dol.assessment.data.network.ApiService
import com.dol.assessment.ui.BaseApiResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class ApiRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    BaseApiResponse() {
    suspend fun getPosts(): Flow<NetworkResult<PostsResponse>> {
        return flow {
            emit(safeApiCall { apiService.getPosts() })
        }.flowOn(Dispatchers.IO)
    }
}