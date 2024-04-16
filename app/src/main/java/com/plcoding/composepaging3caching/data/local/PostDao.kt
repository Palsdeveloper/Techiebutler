package com.techiebutler.techiebutler.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PostDao {

    @Upsert
    suspend fun upsertAll(post: List<PostEntity>)

    @Query("Select * from postentity")
    fun pagingSource(): PagingSource<Int, PostEntity>

    @Query("Delete from postentity")
    suspend fun clearAll()
}