package com.example.challenges.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.challenges.data.StoryRepository

class HomeViewModel(private val repository: StoryRepository) : ViewModel() {

    fun getSession(): LiveData<String>{
        return repository.getSession().asLiveData()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}