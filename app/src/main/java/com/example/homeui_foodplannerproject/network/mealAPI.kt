package com.example.homeui_foodplannerproject.network
import com.example.homeui_foodplannerproject.model.CategoriesResponse
import com.example.homeui_foodplannerproject.model.Meal
import com.example.homeui_foodplannerproject.model.Category
import com.example.homeui_foodplannerproject.model.MealsResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi{
@GET("categories.php")
suspend fun getCategories(): CategoriesResponse
@GET("filter.php")
suspend fun getMealsByCategory(@Query ("c")categoryName: String): MealsResponse
@GET("random.php")
suspend fun getRandomMeal(): MealsResponse

@GET("lookup.php")
suspend fun getMealDetailsById(@Query("i") mealId: String): MealsResponse
}