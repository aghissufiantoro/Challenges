package com.example.challenges.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenges.data.StoryRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: StoryRepository) : ViewModel() {

    fun login(email: String, password: String) = repository.login(email, password)

    fun saveSession(token: String) {
        viewModelScope.launch {
            repository.saveSession(token)
        }
    }
}