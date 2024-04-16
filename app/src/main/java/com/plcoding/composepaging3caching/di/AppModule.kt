package com.plcoding.composepaging3caching.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.techiebutler.techiebutler.data.local.PostDatabase
import com.techiebutler.techiebutler.data.local.PostEntity
import com.techiebutler.techiebutler.data.remote.PostApi
import com.techiebutler.techiebutler.data.remote.PostRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePostDatabase(
        @ApplicationContext context: Context
    ): PostDatabase {
        return Room.databaseBuilder(
            context,
            PostDatabase::class.java,
            "posts.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePostApi(): PostApi {
        return Retrofit.Builder()
            .baseUrl(PostApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providePostPager(
        postDatabase: PostDatabase,
        postApi: PostApi
    ): Pager<Int, PostEntity> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = PostRemoteMediator(
                postDb = postDatabase,
                postApi = postApi
            ),
            pagingSourceFactory = {
                postDatabase.dao.pagingSource()
            }
        )
    }
}