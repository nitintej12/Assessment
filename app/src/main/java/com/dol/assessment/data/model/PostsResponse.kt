package com.dol.assessment.data.model

data class PostsResponseItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)

class PostsResponse : ArrayList<PostsResponseItem>()