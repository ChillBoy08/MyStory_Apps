package com.example.mystory.di

import com.example.mystory.api.ApiConfig
import com.example.mystory.data.respository.StoryRepository

object StoryInjection {
    fun provideRepository(): StoryRepository {

        val apiService = ApiConfig.getApiService()

        return StoryRepository.getInstance(apiService)

    }
}