package com.dol.assessment.data.network

import com.dol.assessment.data.model.PostsResponseItem
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("posts")
    suspend fun getPosts(): Response<ArrayList<PostsResponseItem>>
}