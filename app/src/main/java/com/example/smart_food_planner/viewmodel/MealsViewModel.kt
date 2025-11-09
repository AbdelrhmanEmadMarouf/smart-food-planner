package com.example.smart_food_planner.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smart_food_planner.data.repository.Meal_Repository
import com.example.smart_food_planner.model.dataClasses.Meal
import kotlinx.coroutines.launch

class MealsViewModel : ViewModel() {

    private val repository = Meal_Repository()
    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> get() = _meals

    fun getMeals() = viewModelScope.launch {

        repository.getMeals(){meals->
            repository.getMeals { mealList ->
                _meals.postValue(mealList)

            }
        }

    }

}
