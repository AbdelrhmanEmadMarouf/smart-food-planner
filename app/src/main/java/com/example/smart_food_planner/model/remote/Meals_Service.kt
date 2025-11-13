package com.example.smart_food_planner.model.remote


import com.example.smart_food_planner.model.dataClasses.CategoryNamesList
import com.example.smart_food_planner.model.dataClasses.Meal
import com.example.smart_food_planner.model.dataClasses.Meals
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Meals_Service {

    @GET("categories.php")
    fun getMeals() : Call<Meals>

//    @GET("list.php")
//    fun getListOfCategoriesName(
//        @Query("c") category : String ="list"
//    ) : Call<CategoryNamesList>

//    @GET("list.php")
//    fun getListOfCountriesName(
//        @Query("a") category : String ="list"
//    ) : Call<ListOfCountriesName>

}