package com.example.challenges.retrofit

import android.telecom.Call
import com.example.challenges.data.response.LoginResponse
import com.example.challenges.data.response.RegisterResponse
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {
    @FormUrlEncoded
    @POST("login")
    fun register(
        @Field(id = "name") name: String,
        @Field(id = "email") email: String,
        @Field(id = "password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field(id = "email") email: String,
        @Field(id = "password") password: String,
    ): Call<LoginResponse>


}