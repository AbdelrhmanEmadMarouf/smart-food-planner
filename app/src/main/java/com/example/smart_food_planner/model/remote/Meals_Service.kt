package com.example.smart_food_planner.model.remote



import com.example.smart_food_planner.model.dataClasses.Countries_Name
import com.example.smart_food_planner.model.dataClasses.Ingrediants
import com.example.smart_food_planner.model.dataClasses.Meals
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Meals_Service {

    @GET("categories.php")
    fun getMeals() : Call<Meals>


    @GET("list.php")
    fun getListOfCountriesName(
        @Query("a") category : String ="list"
    ) : Call<Countries_Name>

    @GET("list.php")
    fun getListOfIngretiants(
    @Query("i") ingrediant : String ="list"
    ): Call<Ingrediants>



}