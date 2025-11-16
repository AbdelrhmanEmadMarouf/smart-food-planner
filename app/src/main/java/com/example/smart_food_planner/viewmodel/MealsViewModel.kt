package com.example.smart_food_planner.viewmodel

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
import kotlinx.coroutines.launch

class MealsViewModel : ViewModel() {

    private val repository = Meal_Repository()
    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> get() = _meals

    fun getMeals() = viewModelScope.launch {

        repository.getMeals(){meals->
            repository.getMeals { mealList ->
                _meals.postValue(mealList)

            }
        }

    }

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> get() = _countries

    fun getListOfCountriesName() =viewModelScope.launch {
        repository.getListOfCountriesName { countries ->
            _countries.postValue(countries)
        }
    }


    private val _ingrediantsList = MutableLiveData<List<Ingrediant_Item>>()
    val ingrediantsList: LiveData<List<Ingrediant_Item>> get() = _ingrediantsList

    fun getListOfIngrediants() =viewModelScope.launch {
        repository.getIngrediants_List { IngrediantsList ->
            _ingrediantsList.postValue(IngrediantsList)
        }
    }



    private val _filteredMeals = MutableLiveData<List<Filtered_Meal>>()
    val filteredMeals : LiveData<List<Filtered_Meal>> get() = _filteredMeals

    fun getFilteredMealsList(key: String?, value: String?) = viewModelScope.launch{
        repository.getFilteredMealsList(key,value,{ filteredMealsList ->
            _filteredMeals.postValue(filteredMealsList)
        })
    }


    private val _detiaildMeal = MutableLiveData<Detailed_Meal>()
    val detailedMeal : LiveData<Detailed_Meal>  get() = _detiaildMeal

    fun getMealBId(id: String?){
        repository.getMealById(id,{listOfDetaildMeals->
            _detiaildMeal.postValue(listOfDetaildMeals[0])
        })
    }

    private val _searchedMeals = MutableLiveData<List<Detailed_Meal>>()
    val searchedMeals : LiveData<List<Detailed_Meal>> get() = _searchedMeals

    fun getMealByName(mealName : String?){
        repository.getMealByName(mealName,{list->
            _searchedMeals.postValue(list)
        })
    }








}
