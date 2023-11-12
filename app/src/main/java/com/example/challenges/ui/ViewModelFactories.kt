package com.example.challenges.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challenges.HomeViewModel
import com.example.challenges.data.StoryRepository
import com.example.challenges.di.Injection
import com.example.challenges.ui.auth.LoginViewModel
import com.example.challenges.ui.auth.RegisterViewModel
import com.google.android.ads.mediationtestsuite.viewmodels.ViewModelFactory
import java.lang.IllegalArgumentException


@Suppress("UNCHECKED_CAST")
class ViewModelFactories private constructor(
    private val repository: StoryRepository
): ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactories? = null
        fun getInstance(context: Context): ViewModelFactories =
            instance ?: synchronized(this){
                instance ?: ViewModelFactories(
                    Injection.provideRepository(context)
                )
            }.also { instance = it }
    }
}