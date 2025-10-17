package com.example.smart_food_planner.model.remote


import com.example.smart_food_planner.model.dataClasses.GenerateContentRequest
import com.example.smart_food_planner.model.dataClasses.GenerateContentResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


interface GeminiApiService {
    @Headers("Content-Type: application/json")

    @POST("v1beta/models/gemini-2.0-flash:generateContent")
    fun generateText(
        @Query("key") apiKey: String,
        @Body requestBody: GenerateContentRequest
    ): Call<GenerateContentResponse>
}
