package com.techiebutler.techiebutler.data.remote

import androidx.annotation.RequiresApi
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.plcoding.composepaging3caching.data.mappers.toPostEntity
import com.techiebutler.techiebutler.data.local.PostDatabase
import com.techiebutler.techiebutler.data.local.PostEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val postDb: PostDatabase,
    private val postApi: PostApi
) : RemoteMediator<Int, PostEntity>() {
    @RequiresApi(34)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            delay(2000L)
            val posts = postApi.getPosts(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            postDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    postDb.dao.clearAll()
                }

                val postEntity = posts.map { it.toPostEntity() }
                postDb.dao.upsertAll(postEntity)
            }
            MediatorResult.Success(
                endOfPaginationReached = true
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}