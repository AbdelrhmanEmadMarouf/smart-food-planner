package com.example.smart_food_planner.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.smart_food_planner.data.repository.Favorite_Table_Repository
import com.example.smart_food_planner.data.repository.MealTable_Repo
import com.example.smart_food_planner.database.Favorite_Table_Database
import com.example.smart_food_planner.database.MealTableDatabase
import com.example.smart_food_planner.database.dataclasses.FavoriteMeals
import com.example.smart_food_planner.database.dataclasses.Meal_Data
import com.example.smart_food_planner.model.dataClasses.Meal
import kotlinx.coroutines.launch

class Favorite_Meals_Viewmodel(application: Application) : AndroidViewModel(application) {


    val repository: Favorite_Table_Repository

    init {
        val favoriteMealDatabase = Favorite_Table_Database.getInstance(getApplication<Application>().applicationContext)
        val mealDao = favoriteMealDatabase.getFavoriteDao()

        repository = Favorite_Table_Repository(mealDao)

    }


    private val _favorite_meals = MutableLiveData<List<FavoriteMeals>>()
    val favorite_meals: LiveData<List<FavoriteMeals>> get() = _favorite_meals

    fun getFavoriteMeals() = viewModelScope.launch {

        repository.getAllFavorite().observeForever { list ->

            _favorite_meals.postValue(list)
        }

    }


    fun deleteMeal(meal: FavoriteMeals) = viewModelScope.launch {
        repository.delete(meal)
    }


    fun addFavoriteMeal(meal: FavoriteMeals) = viewModelScope.launch {
        repository.insert(meal)
    }



}