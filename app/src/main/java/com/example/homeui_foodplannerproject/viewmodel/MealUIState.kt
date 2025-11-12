package com.example.homeui_foodplannerproject.viewmodel

import com.example.homeui_foodplannerproject.model.Meal
import com.example.homeui_foodplannerproject.model.Category


sealed class MealUIState {
    object Loading : MealUIState()
    data class Success (val meals : List<Category>) : MealUIState()
    data class Error (val msg : String) : MealUIState()
}