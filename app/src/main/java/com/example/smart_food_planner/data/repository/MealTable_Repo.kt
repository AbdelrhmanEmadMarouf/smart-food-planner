package com.example.smart_food_planner.data.repository

import androidx.lifecycle.LiveData
import com.example.smart_food_planner.database.dao.MealDao
import com.example.smart_food_planner.database.dataclasses.Meal_Data

class MealTable_Repo(private val mealDao: MealDao) {



    fun getAllMealsLive(): LiveData<List<Meal_Data>> {
        return mealDao.getAllMealsLive()
    }



    suspend fun insert(meal: Meal_Data) {
        mealDao.insert(meal)
    }

    suspend fun delete(meal: Meal_Data) {
        mealDao.delete(meal)
    }

    suspend fun update(meal: Meal_Data) {
        mealDao.update(meal)
    }

    fun getIdOfMeal(day: Int, month: Int, year: Int,mealNumber : Int): LiveData<Int> {
        return mealDao.getIdOfMeal(day, month, year , mealNumber)
    }





}