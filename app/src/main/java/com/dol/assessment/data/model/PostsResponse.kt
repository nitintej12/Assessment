package com.dol.assessment.data.model

data class PostsResponse(
    val results : ArrayList<PostsResponseItem>
)
data class PostsResponseItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)