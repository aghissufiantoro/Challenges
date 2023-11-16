package com.example.challenges.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.challenges.Hasil
import com.example.challenges.Hasil.Success
import com.example.challenges.data.pref.UserPreference
import com.example.challenges.data.response.ErrorResponse
import com.example.challenges.data.response.LoginResponse
import com.example.challenges.data.response.RegisterResponse
import com.example.challenges.retrofit.APIService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response



class StoryRepository private constructor(
    private val apiService: APIService,
    private val userPreference: UserPreference
)
{
    private val loginResult = MediatorLiveData<Hasil<LoginResponse>>()
    private val registerResult = MediatorLiveData<Hasil<RegisterResponse>>()


    fun login(email: String, password: String): LiveData<Hasil<LoginResponse>> {
        loginResult.value = Hasil.Loading

        apiService.login(email, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                try {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            loginResult.value = Hasil.Success(responseBody)
                        } else {
                            // Handle the case where responseBody is null
                            loginResult.value = Hasil.Error("Response body is null")
                        }
                    } else {
                        throw HttpException(response)
                    }
                } catch (e: HttpException) {
                    val jsonInString = e.response()?.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody.message
                    loginResult.value = Hasil.Error(errorMessage!!)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginResult.value = Hasil.Error(t.message.toString())
            }
        })

        return loginResult
    }

    fun register(name: String, email: String, password : String): LiveData<Hasil<RegisterResponse>> {
        registerResult.value = Hasil.Loading
        apiService.register(name, email, password).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>,
            ) {
                try {
                    if(response.isSuccessful) {
                        val responseBody = response.body()!!
                        registerResult.value = Success(responseBody)
                    } else throw HttpException(response)
                } catch (e: HttpException) {
                    val jsonInString = e.response()?.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody.message
                    registerResult.value = Hasil.Error(errorMessage!!)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                registerResult.value = Hasil.Error(t.message.toString())
            }

        })
        return registerResult
    }

    suspend fun saveSession(token: String) {
        userPreference.saveSession(token)
    }

    fun getSession(): Flow<String> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }


    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: APIService,
            userPreference: UserPreference
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, userPreference)
            }.also { instance = it }
    }
}