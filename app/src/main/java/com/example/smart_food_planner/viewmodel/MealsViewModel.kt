package com.example.smart_food_planner.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smart_food_planner.data.repository.Meal_Repository
import com.example.smart_food_planner.model.dataClasses.Country
import com.example.smart_food_planner.model.dataClasses.Detailed_Meal
import com.example.smart_food_planner.model.dataClasses.Filtered_Meal
import com.example.smart_food_planner.model.dataClasses.Ingrediant_Item
import com.example.smart_food_planner.model.dataClasses.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MealsViewModel : ViewModel() {

    private val repository = Meal_Repository()
    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> get() = _meals

    fun getMeals() = viewModelScope.launch {

        repository.getMeals() { meals ->
            repository.getMeals { mealList ->
                _meals.postValue(mealList)

            }
        }

    }

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> get() = _countries

    fun getListOfCountriesName() = viewModelScope.launch {
        repository.getListOfCountriesName { countries ->
            _countries.postValue(countries)
        }
    }


    private val _ingrediantsList = MutableLiveData<List<Ingrediant_Item>>()
    val ingrediantsList: LiveData<List<Ingrediant_Item>> get() = _ingrediantsList

    fun getListOfIngrediants() = viewModelScope.launch {
        repository.getIngrediants_List { IngrediantsList ->
            _ingrediantsList.postValue(IngrediantsList)
        }
    }


    private val _filteredMeals = MutableLiveData<List<Filtered_Meal>>()
    val filteredMeals: LiveData<List<Filtered_Meal>> get() = _filteredMeals

    fun getFilteredMealsList(key: String?, value: String?) = viewModelScope.launch {
        repository.getFilteredMealsList(key, value, { filteredMealsList ->
            _filteredMeals.postValue(filteredMealsList)
        })
    }


    private val _detiaildMeal = MutableLiveData<Detailed_Meal>()
    val detailedMeal: LiveData<Detailed_Meal> get() = _detiaildMeal

    fun getMealBId(id: String?) {
        // legacy: keep but avoid calling repeatedly inside loops
        repository.getMealById(id, { listOfDetaildMeals ->
            if (listOfDetaildMeals.isNotEmpty()) {
                _detiaildMeal.postValue(listOfDetaildMeals[0])
            }
        })
    }


    private val _randomMeal = MutableLiveData<Detailed_Meal>()
    val randomMeal: LiveData<Detailed_Meal> get() = _randomMeal

    fun getRandomMeal() {
        // legacy: keep but avoid calling repeatedly inside loops
        repository.getRandomMeal { listOfDetaildMeals ->
            if (listOfDetaildMeals.isNotEmpty()) {
                _randomMeal.postValue(listOfDetaildMeals[0])
            }
        }
    }


    private val _searchedMeals = MutableLiveData<List<Detailed_Meal>>()
    val searchedMeals: LiveData<List<Detailed_Meal>> get() = _searchedMeals

    fun getMealByName(mealName: String?) {
        repository.getMealByName(mealName, { list ->
            _searchedMeals.postValue(list)
        })
    }


    private val mealCache = mutableMapOf<String, Detailed_Meal?>()

    /**
     * Suspend function that returns Detailed_Meal? for given id.
     * It wraps the repository callback-based getMealById(...) into a suspend function.
     */
    suspend fun fetchMealByIdCached(id: String): Detailed_Meal? {
        // return cache if present
        mealCache[id]?.let { return it }

        return try {
            val result = withContext(Dispatchers.IO) {
                suspendCancellableCoroutine<Detailed_Meal?> { cont ->
                    try {
                        repository.getMealById(id) { listOfDetailedMeals ->
                            val item = listOfDetailedMeals.firstOrNull()
                            if (!cont.isCompleted) cont.resume(item)
                        }
                    } catch (e: Exception) {
                        if (!cont.isCompleted) cont.resumeWithException(e)
                    }

                    // cancellation handler: called if coroutine is cancelled
                    cont.invokeOnCancellation { cause ->
                        Log.d("MealsVM", "fetchMealByIdCached cancelled for $id. cause=$cause")
                        // If repository exposed a Call, you could cancel it here.
                    }
                }
            }

            mealCache[id] = result
            result
        } catch (e: Exception) {
            Log.e("MealsVM", "fetchMealByIdCached error for $id", e)
            null
        }
    }

    // optional helper to load into LiveData (use only when needed)
    fun loadDetailedMealToLiveData(id: String) {
        viewModelScope.launch {
            val meal = fetchMealByIdCached(id)
            meal?.let { _detiaildMeal.postValue(it) }
        }
    }


    suspend fun fetchRandomMealAsync(): Detailed_Meal? {
        return try {
            withContext(Dispatchers.IO) {
                suspendCancellableCoroutine<Detailed_Meal?> { cont ->
                    try {
                        repository.getRandomMeal { listOfDetailedMeals ->
                            val item = listOfDetailedMeals.firstOrNull()
                            if (!cont.isCompleted) cont.resume(item)
                        }
                    } catch (e: Exception) {
                        if (!cont.isCompleted) cont.resumeWithException(e)
                    }

                    cont.invokeOnCancellation { cause ->
                        Log.d("MealsVM", "fetchRandomMealAsync cancelled. cause=$cause")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("MealsVM", "fetchRandomMealAsync error", e)
            null
        }
    }


    suspend fun fetchMultipleRandomMeals(count: Int): MutableList<Detailed_Meal> {
        val meals = mutableListOf<Detailed_Meal>()

        repeat(count) {
            val meal = fetchRandomMealAsync()
            meal?.let { meals.add(it) }
        }

        return meals
    }



    // أضف هذه الـ function في MealsViewModel

    suspend fun fetchMealsByCategory(categoryName: String, count: Int): List<Detailed_Meal> {
        return try {
            withContext(Dispatchers.IO) {
                suspendCancellableCoroutine { cont ->
                    try {
                        repository.getFilteredMealsList("c", categoryName) { filteredMeals ->
                            // جلب تفاصيل أول 'count' وجبات
                            val detailedMeals = mutableListOf<Detailed_Meal>()
                            val mealsToFetch = filteredMeals.take(count)

                            var fetchedCount = 0
                            mealsToFetch.forEach { filteredMeal ->
                                repository.getMealById(filteredMeal.idMeal) { detailedMealsList ->
                                    detailedMealsList.firstOrNull()?.let { detailedMeals.add(it) }
                                    fetchedCount++

                                    if (fetchedCount == mealsToFetch.size) {
                                        if (!cont.isCompleted) cont.resume(detailedMeals)
                                    }
                                }
                            }

                            // في حالة عدم وجود وجبات
                            if (mealsToFetch.isEmpty() && !cont.isCompleted) {
                                cont.resume(emptyList())
                            }
                        }
                    } catch (e: Exception) {
                        if (!cont.isCompleted) cont.resumeWithException(e)
                    }

                    cont.invokeOnCancellation { cause ->
                        Log.d("MealsVM", "fetchMealsByCategory cancelled for $categoryName. cause=$cause")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("MealsVM", "fetchMealsByCategory error for $categoryName", e)
            emptyList()
        }
    }


}

