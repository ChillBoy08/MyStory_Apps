package com.example.mystory.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.mystory.api.ApiConfig
import com.example.mystory.data.respository.UserRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object UserInjection {
    fun providePreferences(context: Context): UserRepository {

        val apiService = ApiConfig.getApiService()

        return UserRepository.getInstance(context.dataStore, apiService)

    }
}