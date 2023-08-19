package com.example.mystory.HiFi.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mystory.HiFi.paging.PagingRepository
import com.example.mystory.data.ListItem
import com.example.mystory.data.respository.StoryRepository
import com.example.mystory.data.respository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRepository: UserRepository,
    private val storyRepository: StoryRepository,
    private val pagingRepository: PagingRepository,
) : ViewModel() {

    fun getToken() = userRepository.getToken().asLiveData()

    fun getStory(token: String): LiveData<PagingData<ListItem>> =
        pagingRepository.getStory(token).cachedIn(viewModelScope)

    fun isLogin() : LiveData<Boolean> {
        return userRepository.isLogin().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }

    fun getStories(token: String) = storyRepository.getStories(token)
}