package com.plcoding.composepaging3caching.domain

data class Post(
    val id: Int,
    val userId: Int,
    val title: String?,
    val body: String?
)
