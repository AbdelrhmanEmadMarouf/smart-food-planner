package com.example.homeui_foodplannerproject.model

data class MealsResponse(val meals: List<Meal>)
data class Meal (
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
    )