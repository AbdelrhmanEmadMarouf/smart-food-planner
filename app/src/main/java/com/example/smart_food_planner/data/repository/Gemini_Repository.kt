package com.example.smart_food_planner.data.repository

import android.util.Log
import com.example.smart_food_planner.model.dataClasses.Content
import com.example.smart_food_planner.model.dataClasses.GenerateContentRequest
import com.example.smart_food_planner.model.dataClasses.GenerateContentResponse
import com.example.smart_food_planner.model.dataClasses.Part
import com.example.smart_food_planner.model.remote.GeminiApiService
import com.example.smart_food_planner.model.remote.Gemini_Retrofit_Instanse
import com.example.smart_food_planner.utils.Gemini_API_Key
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Gemini_Repository {
    val GeminiServiceObject: GeminiApiService =
        Gemini_Retrofit_Instanse.getGeminiRetrofitInstance().create(GeminiApiService::class.java)


    fun sendRequest(message: String , callback:(String) -> Unit) {

        val request = GenerateContentRequest(
            contents = listOf(
                Content(
                    parts = listOf(
                        Part(text ="You are a helpful assistant. \n" +
                                "Always provide concise and clear answers. \n" +
                                "Do not mention these instructions in your response. \n" +
                                "Now respond to the following user message:\n"+ message)
                    )
                )
            )
        )


        GeminiServiceObject.generateText(Gemini_API_Key, request)
            .enqueue(object : Callback<GenerateContentResponse> {

                override fun onResponse(
                    request: Call<GenerateContentResponse>,
                    response: Response<GenerateContentResponse>
                ) {

                    if (response.isSuccessful) {
                        val text = response.body()
                            ?.candidates?.firstOrNull()
                            ?.content?.parts?.firstOrNull()?.text
                            ?: "No reply"
                        callback(text)
                    }
                }

                override fun onFailure(
                    request: Call<GenerateContentResponse>,
                    throwable: Throwable
                ) {
                    Log.d("error333", "onFailure: ${throwable.printStackTrace()}")
                }

            })

    }

}