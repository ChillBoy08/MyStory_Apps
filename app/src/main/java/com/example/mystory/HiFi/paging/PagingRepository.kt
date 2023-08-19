package com.example.mystory.HiFi.paging

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.mystory.api.ApiService
import com.example.mystory.data.ListItem
import com.example.mystory.data.database.StoryDatabase
import com.example.mystory.data.respository.UserRepository

class PagingRepository(
    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService,
    ) {
    fun getStory(token: String): LiveData<PagingData<ListItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 1000
            ),
            remoteMediator = UserRemote(storyDatabase,apiService,token),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }
}