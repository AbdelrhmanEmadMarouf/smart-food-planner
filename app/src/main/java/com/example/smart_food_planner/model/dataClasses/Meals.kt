package com.example.smart_food_planner.model.dataClasses

import com.google.gson.annotations.SerializedName

data class Meals(
    @SerializedName("categories")
    val categories: List<Meal>
)

data class Meal(
    @SerializedName("idCategory")
    val idMeal: String,

    @SerializedName("strCategory")
    val strMealTitle: String,

    @SerializedName("strCategoryDescription")
    val strMealDescription: String,

    @SerializedName("strCategoryThumb")
    val strMealUrl: String
)
