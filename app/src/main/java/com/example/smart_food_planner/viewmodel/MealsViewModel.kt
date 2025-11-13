package com.example.smart_food_planner.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smart_food_planner.data.repository.Meal_Repository
import com.example.smart_food_planner.model.dataClasses.Country
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

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> get() = _countries

    fun getListOfCountriesName() =viewModelScope.launch {
        repository.getListOfCountriesName { countries ->
            _countries.postValue(countries)
        }
    }




}
