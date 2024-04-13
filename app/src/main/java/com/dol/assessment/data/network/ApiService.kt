package com.dol.assessment.data.network

import com.dol.assessment.data.model.PostsResponse
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("posts")
    suspend fun getPosts(): Response<PostsResponse>
}