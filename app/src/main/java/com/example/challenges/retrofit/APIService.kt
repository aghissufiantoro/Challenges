package com.example.challenges.retrofit


import com.example.challenges.data.response.LoginResponse
import com.example.challenges.data.response.RegisterResponse
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @retrofit2.http.Field("name") name: String,
        @retrofit2.http.Field("email") email: String,
        @retrofit2.http.Field("password") password: String
    ): retrofit2.Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @retrofit2.http.Field("email") email: String,
        @retrofit2.http.Field("password") password: String
    ): retrofit2.Call<LoginResponse>



}