package com.example.mystory.data.respository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.mystory.api.ApiService
import com.example.mystory.data.login.LoginResponse
import com.example.mystory.data.respon.RegisterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.mystory.data.Result

class UserRepository private constructor(private val dataStore: DataStore<Preferences>, private val apiService: ApiService) {

    fun login(email: String, password: String) : LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val result = apiService.login(email, password)
            emit(Result.Success(result))
        } catch (e: Exception) {
            Log.d("UserRepository", "Login: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun register(name: String, email: String, password: String) : LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val result = apiService.register(name, email, password)
            emit(Result.Success(result))
        } catch (e: Exception) {
            Log.d("UserRepository", "Register: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN] ?: ""
        }
    }

    suspend fun setToken(token: String, isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
            preferences[STATE_KEY] = isLogin
        }
    }

    fun isLogin(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[STATE_KEY] ?: false
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[TOKEN] = ""
            preferences[STATE_KEY] = false
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        private val TOKEN = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>, apiService: ApiService): UserRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepository(dataStore, apiService)
                INSTANCE = instance
                instance
            }
        }
    }
}