package com.example.smart_food_planner.database


import android.content.Context
import com.example.smart_food_planner.model.dataClasses.Detailed_Meal
import com.google.gson.Gson

object DailyMealStorage {

    private const val PREF_NAME = "daily_meal_pref"
    private const val KEY_MEAL = "daily_meal"
    private const val KEY_TIMESTAMP = "meal_timestamp"

    private val gson = Gson()

    fun saveMeal(context: Context, meal: Detailed_Meal) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = gson.toJson(meal)
        editor.putString(KEY_MEAL, json)
        editor.putLong(KEY_TIMESTAMP, System.currentTimeMillis())
        editor.apply()
    }

    fun getSavedMeal(context: Context): Detailed_Meal? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_MEAL, null)
        return json?.let { gson.fromJson(it, Detailed_Meal::class.java) }
    }

    fun getSavedTimestamp(context: Context): Long {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getLong(KEY_TIMESTAMP, 0)
    }

    fun clearMeal(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
    // ---------------------- For You Meals ----------------------

    private const val KEY_FOR_YOU_MEALS = "for_you_meals"
    private const val KEY_FOR_YOU_TIMESTAMP = "for_you_timestamp"

    fun saveForYouMeals(context: Context, meals: List<Detailed_Meal>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = gson.toJson(meals)
        editor.putString(KEY_FOR_YOU_MEALS, json)
        editor.putLong(KEY_FOR_YOU_TIMESTAMP, System.currentTimeMillis())
        editor.apply()
    }

    fun getSavedForYouMeals(context: Context): List<Detailed_Meal>? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_FOR_YOU_MEALS, null)
        return json?.let {
            val type = com.google.gson.reflect.TypeToken.getParameterized(
                List::class.java, Detailed_Meal::class.java
            ).type
            gson.fromJson(it, type)
        }
    }

    fun getForYouTimestamp(context: Context): Long {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getLong(KEY_FOR_YOU_TIMESTAMP, 0)
    }
}