package com.example.homeui_foodplannerproject.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeui_foodplannerproject.model.Meal
import com.example.homeui_foodplannerproject.network.MealApi
import com.example.homeui_foodplannerproject.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.homeui_foodplannerproject.model.CategoryWithMeals
import com.example.homeui_foodplannerproject.model.MealRepositoryData
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Dispatchers


class MealViewModel(application: Application) : AndroidViewModel(application) {
  private val _CategoryWithMeals = MutableStateFlow<List<CategoryWithMeals>>(emptyList())
    val CategoryWithMeals : StateFlow<List<CategoryWithMeals>> = _CategoryWithMeals

    private val _randomMeal = MutableStateFlow<Meal?>(null)
    val randomMeal: StateFlow<Meal?> = _randomMeal.asStateFlow()

    private val _randomMealCategory = MutableStateFlow<List<Meal>>(emptyList())

    val randomMealCategory : StateFlow<List<Meal>> = _randomMealCategory

    private val persistenceRepository = MealRepositoryData(application)

    private val _isFavorite = MutableStateFlow<Set<String>>(emptySet())

    val isFavorite : StateFlow<Set<String>> = _isFavorite

    fun toggleFavorite (mealId: String){

        val currentFavorite = _isFavorite.value.toMutableSet()
        if(currentFavorite.contains(mealId)){
            currentFavorite.remove(mealId)
        }else{
            currentFavorite.add(mealId)
        }
        _isFavorite.value = currentFavorite

    }


init {
    FetchAllData()
    fetchTodaysMeal()
}

    private fun fetchTodaysMeal() {
        viewModelScope.launch(Dispatchers.IO) {
            val savedData = persistenceRepository.getSavedMealData()
            val twentyFourHoursInMillis = 24 * 60 * 60 * 1000

            if (savedData != null && (System.currentTimeMillis() - savedData.lastUpdatedTimestamp) < twentyFourHoursInMillis) {
                try {
                    val response = RetrofitInstance.api.getMealDetailsById(savedData.mealId)
                    if (response.meals.isNotEmpty()) {
                        _randomMeal.value = response.meals.first()
                    } else {
                        fetchAndSaveNewRandomMeal()
                    }
                } catch (e: Exception) {
                    fetchAndSaveNewRandomMeal()
                }
            }
            else{
                fetchAndSaveNewRandomMeal()
            }
            }
        }

    private suspend fun fetchAndSaveNewRandomMeal() {
        try {
            val response = RetrofitInstance.api.getRandomMeal()
            if (response.meals.isNotEmpty()) {
                val newMeal = response.meals.first()
                _randomMeal.value = newMeal

                persistenceRepository.saveTodayMeal(newMeal.idMeal)
            }
        } catch (e: Exception) {
            Log.e("ViewModel", "Error fetching and saving new meal", e)
        }
    }

    fun FetchAllData(){
        viewModelScope.launch {
            fetchTodaysMeal()

            try {
                val randomCategory = mutableListOf<Meal>()
                repeat(15){
                    val response = RetrofitInstance.api.getRandomMeal()
                    if (response.meals.isNotEmpty()){
                        randomCategory.add(response.meals.first())
                    }
                }
                val categories = RetrofitInstance.api.getCategories().categories
                val results = mutableListOf<CategoryWithMeals>()



                categories.take(5).forEach{ category ->
                    val meals = RetrofitInstance.api.getMealsByCategory(category.strCategory).meals
                    val limitedMeals = meals.take(8)
                    results.add(CategoryWithMeals(category.strCategory, limitedMeals))
                }



                _CategoryWithMeals.value = results
                _randomMealCategory.value = randomCategory


            }catch (e: Exception){
                println("Error while getting the data :(")
            }
        }
    }
}