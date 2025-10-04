package com.example.smart_food_planner.model.remote

import com.example.smart_food_planner.utils.BaseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Gemini_Retrofit_Instanse {

    fun getGeminiRetrofitInstance() : Retrofit{
        return  Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}