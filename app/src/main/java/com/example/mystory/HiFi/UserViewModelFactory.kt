package com.example.mystory.HiFi

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mystory.HiFi.login.LoginViewModel
import com.example.mystory.HiFi.register.RegisterViewModel
import com.example.mystory.data.respository.UserRepository
import com.example.mystory.di.UserInjection

class UserViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Tidak Tau Kelas ViewModel : " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: UserViewModelFactory? = null
        fun getInstance(context: Context): UserViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: UserViewModelFactory(UserInjection.providePreferences(context))
            }.also { instance = it }
    }
}