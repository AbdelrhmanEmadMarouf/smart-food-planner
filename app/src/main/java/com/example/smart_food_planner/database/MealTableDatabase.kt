package com.example.smart_food_planner.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smart_food_planner.database.dao.MealDao
import com.example.smart_food_planner.database.dataclasses.Meal_Data

@Database(
    entities = [Meal_Data::class],
    version = 2,
    exportSchema = false
)
abstract class MealTableDatabase : RoomDatabase() {

    abstract fun getMealDao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: MealTableDatabase? = null

        fun getInstance(context: Context): MealTableDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealTableDatabase::class.java,
                    "favorite_meals_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
