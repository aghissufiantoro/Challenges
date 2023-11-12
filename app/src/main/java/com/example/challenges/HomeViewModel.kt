package com.example.challenges

import android.media.session.MediaSession.Token
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.challenges.data.StoryRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: StoryRepository): ViewModel() {
    fun getSession(): LiveData<String>{
        return repository.getSession().asLiveData()
    }
    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }
    fun getAllStories(token: String,) = repository.getAllStories(token)
}