package com.example.mystory.HiFi.register

import androidx.lifecycle.ViewModel
import com.example.mystory.data.respository.UserRepository

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) = userRepository.register(name, email, password)
}