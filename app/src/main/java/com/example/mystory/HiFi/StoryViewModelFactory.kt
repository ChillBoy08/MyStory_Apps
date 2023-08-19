package com.example.mystory.HiFi

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mystory.HiFi.main.MainViewModel
import com.example.mystory.HiFi.paging.PagingRepository
import com.example.mystory.HiFi.story.StoryViewModel
import com.example.mystory.data.respository.StoryRepository
import com.example.mystory.data.respository.UserRepository
import com.example.mystory.di.PagingInjection
import com.example.mystory.di.StoryInjection
import com.example.mystory.di.UserInjection

class StoryViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val storyRepository: StoryRepository,
    private val pagingRepository: PagingRepository

    ) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userRepository, storyRepository, pagingRepository) as T
            }
            modelClass.isAssignableFrom(StoryViewModel::class.java) -> {
                StoryViewModel(userRepository, storyRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: StoryViewModelFactory? = null
        fun getInstance(context: Context): StoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: StoryViewModelFactory(UserInjection.providePreferences(context), StoryInjection.provideRepository(), PagingInjection.provideRepository(context))
            }.also { instance = it }
    }
}