package com.example.homeui_foodplannerproject.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "todays_meal_prefs")
class MealRepositoryData (private val context: Context) {
    private object PreferencesKeys{
        val Meal_Id = stringPreferencesKey("today_mealId")
        val Last_Updated_Time = longPreferencesKey("today_meal_last_updated")
    }

    suspend fun saveTodayMeal(mealId: String){
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.Meal_Id] = mealId
            preferences[PreferencesKeys.Last_Updated_Time]= System.currentTimeMillis()
        }
    }
    suspend fun getSavedMealData(): SavedMealData? {
        return context.dataStore.data.map { preferences ->
            val mealId = preferences[PreferencesKeys.Meal_Id]
            val lastUpdated = preferences[PreferencesKeys.Last_Updated_Time ]
            if (mealId != null && lastUpdated != null) {
                SavedMealData(mealId, lastUpdated)
            } else {
                null
            }
        }.first()


}
}
data class SavedMealData(val mealId: String, val lastUpdatedTimestamp: Long)