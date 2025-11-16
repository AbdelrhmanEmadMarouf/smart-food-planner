package com.example.smart_food_planner.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.smart_food_planner.database.dao.Favorite_Meals_DAO
import com.example.smart_food_planner.database.dataclasses.FavoriteMeals
import com.example.smart_food_planner.database.dataclasses.Meal_Data


class Favorite_Table_Repository(private val favoriteDao: Favorite_Meals_DAO) {


    fun getAllFavorite(): LiveData<List<FavoriteMeals>> {
        return favoriteDao.getAllFavorite()

    }


    suspend fun insert(meal: FavoriteMeals) {
        favoriteDao.insert(meal)

    }

    suspend fun delete(meal: FavoriteMeals) {
        favoriteDao.delete(meal)

    }




}