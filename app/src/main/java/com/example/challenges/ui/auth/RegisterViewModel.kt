package com.example.challenges.ui.auth

import androidx.lifecycle.ViewModel
import com.example.challenges.data.StoryRepository

class RegisterViewModel(private val repository: StoryRepository): ViewModel() {

    fun register(name: String, email: String, password : String) = repository.register(name, email, password)

}