package com.plcoding.composepaging3caching.data.mappers

import com.plcoding.composepaging3caching.domain.Post
import com.techiebutler.techiebutler.data.local.PostEntity
import com.techiebutler.techiebutler.data.remote.PostDto

fun PostDto.toPostEntity(): PostEntity {
    return PostEntity(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}

fun PostEntity.toPost(): Post {
    return Post(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}