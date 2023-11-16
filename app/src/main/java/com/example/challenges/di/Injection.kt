package com.example.challenges.di

import android.content.Context
import com.example.challenges.data.StoryRepository
import com.example.challenges.data.pref.UserPreference
import com.example.challenges.data.pref.dataStore
import com.example.challenges.retrofit.APIConfig
import com.example.challenges.retrofit.APIService

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val APIService = APIConfig.retrofitInstance()
        return StoryRepository.getInstance(APIService, pref)
    }
}