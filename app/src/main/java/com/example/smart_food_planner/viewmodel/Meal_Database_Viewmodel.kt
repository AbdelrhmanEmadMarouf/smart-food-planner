package com.example.smart_food_planner.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.smart_food_planner.data.repository.MealTable_Repo
import com.example.smart_food_planner.data.repository.Meal_Repository
import com.example.smart_food_planner.database.MealTableDatabase
import com.example.smart_food_planner.database.dataclasses.Meal_Data
import kotlinx.coroutines.launch


class Meal_Database_Viewmodel(application: Application) : AndroidViewModel(application) {



    val repository: MealTable_Repo

    init {
        val mealDatabase = MealTableDatabase.getInstance(getApplication<Application>().applicationContext)
        val mealDao = mealDatabase.getMealDao()

        repository = MealTable_Repo(mealDao)

    }


    fun getAllMealsLive(): LiveData<List<Meal_Data>> {
        return repository.getAllMealsLive()
    }

    fun deleteMeal(meal: Meal_Data) = viewModelScope.launch {
        repository.delete(meal)
    }

    fun updateMeal(meal: Meal_Data) = viewModelScope.launch {
        repository.update(meal)
    }

    fun addMeal(meal: Meal_Data) = viewModelScope.launch {
        repository.insert(meal)
    }

    fun getIdOfMeal(day: Int, month: Int, year: Int , mealNumber : Int): LiveData<Int> {
        return repository.getIdOfMeal(day, month, year,mealNumber)
    }





}