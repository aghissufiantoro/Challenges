package com.example.challenges.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIConfig {
    companion object{
        fun retrofitInstance(): APIService {
            val api = Retrofit.Builder()
                .baseUrl("https://testing.jasa-nikah-siri-amanah-profesional.com/category-menu")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return api.create(APIService::class.java)
            }
        }
}