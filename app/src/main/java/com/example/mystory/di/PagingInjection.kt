package com.example.mystory.di

import android.content.Context
import com.example.mystory.HiFi.paging.PagingRepository
import com.example.mystory.api.ApiConfig
import com.example.mystory.data.database.StoryDatabase

object PagingInjection {
    fun provideRepository( context: Context): PagingRepository {

        val database = StoryDatabase.getDatabase(context)

        val apiService = ApiConfig.getApiService()

        return PagingRepository(database,apiService)

    }
}