package com.example.smart_food_planner.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.smart_food_planner.database.dataclasses.FavoriteMeals
import com.example.smart_food_planner.database.dataclasses.Meal_Data

@Dao
interface MealDao {

    @Query("SELECT * FROM MealTable")
    fun getAllMealsLive(): LiveData<List<Meal_Data>>



    @Insert
    suspend fun insert(meal : Meal_Data)

    @Delete
    suspend fun delete(meal : Meal_Data)

    @Update
    suspend fun update(meal : Meal_Data)

    @Query("SELECT id FROM MealTable WHERE day = :day AND month = :month AND year = :year  AND numberOfMeal = :mealNumber")
    fun getIdOfMeal(day: Int, month: Int, year: Int, mealNumber: Int ): LiveData<Int>

}


@Dao
interface Favorite_Meals_DAO{

    @Query("SELECT * FROM  Favorite_meals_table")
    fun getAllFavorite(): LiveData<List<FavoriteMeals>>

    @Delete
    suspend fun delete(meal : FavoriteMeals)

    @Insert
    suspend fun insert(meal : FavoriteMeals)




}