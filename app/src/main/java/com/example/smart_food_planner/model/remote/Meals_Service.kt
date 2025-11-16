package com.example.smart_food_planner.model.remote



import com.example.smart_food_planner.model.dataClasses.Countries_Name
import com.example.smart_food_planner.model.dataClasses.Detailed_Meals
import com.example.smart_food_planner.model.dataClasses.Filtered_Meals
import com.example.smart_food_planner.model.dataClasses.Ingrediants
import com.example.smart_food_planner.model.dataClasses.Meals
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

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


    @GET("filter.php")
    fun filterMeals(
        @QueryMap parameter : Map<String,String>
    ): Call<Filtered_Meals>


    @GET("lookup.php")
    fun getMealByID(
        @Query("i") id: String?
    ) : Call<Detailed_Meals>


    @GET("search.php")
    fun getMealByName(
        @Query("s") mealName : String?
    ) : Call<Detailed_Meals>


}