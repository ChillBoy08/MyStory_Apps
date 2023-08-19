package com.example.mystory.data.respository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.mystory.api.ApiService
import com.example.mystory.data.ListItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.example.mystory.data.Result
import com.example.mystory.data.database.StoryDatabase
import com.example.mystory.data.respon.AddStoryResponse
import com.example.mystory.data.respon.GetStoriesResponse


class StoryRepository(private val apiService: ApiService) {

    fun getStories(token: String) : LiveData<Result<GetStoriesResponse>> = liveData {
        emit(Result.Loading)
        try {
            val result = apiService.getStories("Bearer $token")
            emit(Result.Success(result))
        } catch (e: Exception) {
            Log.d("StoryRepository", "getStories: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun addStories(token: String, photo: MultipartBody.Part, desc: RequestBody) : LiveData<Result<AddStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val result = apiService.addStory("Bearer $token", photo, desc)
            emit(Result.Success(result))
        } catch (e: Exception) {
            Log.d("StoryRepository", "addStories: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }


    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(apiService: ApiService): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService)
            }.also { instance = it }
    }


}