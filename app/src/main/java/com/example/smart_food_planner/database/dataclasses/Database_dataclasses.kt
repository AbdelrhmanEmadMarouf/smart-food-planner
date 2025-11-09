package com.example.smart_food_planner.database.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey

//data class Date(val day : Int , val month : Int , val  year : Int)

@Entity(
    tableName = "MealTable",
    primaryKeys = ["day", "month", "year", "numberOfMeal"]
)
data class Meal_Data(
    val id: Int,
    val day: Int,
    val month: Int,
    val year: Int,
    val numberOfMeal : Int
)


