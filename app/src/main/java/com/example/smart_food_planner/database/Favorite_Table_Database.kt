package com.example.smart_food_planner.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smart_food_planner.database.dao.Favorite_Meals_DAO
import com.example.smart_food_planner.database.dataclasses.FavoriteMeals
import com.example.smart_food_planner.database.dataclasses.Meal_Data


@Database(
    entities = [FavoriteMeals::class],
    version = 1,
    exportSchema = false
)
abstract class Favorite_Table_Database : RoomDatabase() {


    abstract fun getFavoriteDao() : Favorite_Meals_DAO

    companion object {
        @Volatile
        private var INSTANCE: Favorite_Table_Database? = null

        fun getInstance(context: Context): Favorite_Table_Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Favorite_Table_Database::class.java,
                    "meal_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }


}