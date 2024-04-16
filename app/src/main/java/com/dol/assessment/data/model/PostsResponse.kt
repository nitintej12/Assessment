package com.dol.assessment.data.model

data class Post(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)

class PostsResponse : ArrayList<Post>()