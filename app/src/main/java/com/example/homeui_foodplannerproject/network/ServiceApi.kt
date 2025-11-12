package com.example.homeui_foodplannerproject.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance{

    val  BaseURL = "https://www.themealdb.com/api/json/v1/1/"
    val api : MealApi by lazy {
        Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)

    }
}