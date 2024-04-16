package com.dol.assessment.data.network

import com.dol.assessment.data.model.Post
import com.dol.assessment.data.model.PostsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("posts")
    suspend fun getPosts(@Query("_page") page: Int): Response<List<Post>>
}