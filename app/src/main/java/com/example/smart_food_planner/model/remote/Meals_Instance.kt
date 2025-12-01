package com.example.smart_food_planner.model.remote

import com.example.smart_food_planner.utils.BaseUrlOfGemeini
import com.example.smart_food_planner.utils.BaseUrlOfMeals
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Meals_Instance {


    fun getMealRetrofitInstance() : Retrofit{
        return  Retrofit.Builder()
            .baseUrl(BaseUrlOfMeals)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}