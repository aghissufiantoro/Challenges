package com.example.challenges.data

import android.net.http.HttpException
import android.os.Build
import android.telecom.Call
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.android.volley.Response
import com.example.challenges.Hasil
import com.example.challenges.data.pref.UserPreference
import com.example.challenges.data.response.ErrorResponse
import com.example.challenges.data.response.LoginResponse
import com.example.challenges.data.response.RegisterResponse
import com.example.challenges.retrofit.APIService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import javax.security.auth.callback.Callback

class StoryRepository private constructor(
    private val apiService: APIService,
    private val userPreference: UserPreference
)
{
    private val resultLogin = MediatorLiveData<Hasil<String>>()
    private val registerResult = MediatorLiveData<Hasil<RegisterResponse>>()


    fun login(email: String, password: String): LiveData<Hasil<String>> {
        resultLogin.value = Hasil.Loading
        apiService.login(email, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                try {
                    if (response.isSuccessful) {

                        val responseBody = response.body()
                        if (responseBody != null) {
                            resultLogin.value = Hasil.Success(responseBody.loginResult?.token!!)
                        }

                    }
                    else {
                        throw HttpException(response)
                    }
                } catch (e: HttpException) {
                    val jsonInString = e.response()?.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody.message
                    resultLogin.value = Hasil.Error(errorMessage!!)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                resultLogin.value = Hasil.Error(t.message.toString())
            }


        })

        return resultLogin
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
                        registerResult.value = Hasil.Success(responseBody)
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