package com.example.smart_food_planner.data.repository


import android.util.Log
import com.example.smart_food_planner.model.dataClasses.Countries_Name
import com.example.smart_food_planner.model.dataClasses.Country
import com.example.smart_food_planner.model.dataClasses.Detailed_Meal
import com.example.smart_food_planner.model.dataClasses.Detailed_Meals
import com.example.smart_food_planner.model.dataClasses.Filtered_Meal
import com.example.smart_food_planner.model.dataClasses.Filtered_Meals
import com.example.smart_food_planner.model.dataClasses.Ingrediant_Item
import com.example.smart_food_planner.model.dataClasses.Ingrediants
import com.example.smart_food_planner.model.dataClasses.Meal
import com.example.smart_food_planner.model.dataClasses.Meals
import com.example.smart_food_planner.model.remote.Meals_Instance
import com.example.smart_food_planner.model.remote.Meals_Service
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response


class Meal_Repository() {

    val MealServiceObject: Meals_Service =
        Meals_Instance.getMealRetrofitInstance().create(Meals_Service::class.java)

    fun getMeals(callback:(List<Meal>) -> Unit){

        MealServiceObject.getMeals().enqueue(object : Callback<Meals> {
            override fun onResponse(
                request: Call<Meals?>,
                response: Response<Meals?>
            ) {
                if(response.isSuccessful){
                    callback(response.body()!!.categories)
                }
            }

            override fun onFailure(
                request: Call<Meals?>,
                throwable: Throwable
            ) {

            }

        })
    }



        fun getListOfCountriesName(callback:(List<Country>) -> Unit){

            MealServiceObject.getListOfCountriesName().enqueue(object : Callback<Countries_Name> {
                override fun onResponse(
                    request: Call<Countries_Name?>,
                    response: Response<Countries_Name?>
                ) {
                    if(response.isSuccessful){
                        callback(response.body()!!.countriesList)
                    }
                }

                override fun onFailure(
                    request: Call<Countries_Name?>,
                    throwable: Throwable
                ) {
                    Log.d("Error", "onFailure: ${throwable.message.toString()}")
                }

            })
        }

    fun getIngrediants_List(callback: (List<Ingrediant_Item>) -> Unit){

        MealServiceObject.getListOfIngretiants()
            .enqueue(object : Callback<Ingrediants>{
                override fun onResponse(
                    request: Call<Ingrediants?>,
                    response: Response<Ingrediants?>
                ) {
                    if(response.isSuccessful){
                        callback(response.body()!!.Ingrediants_list)
                    }
                }

                override fun onFailure(
                    request: Call<Ingrediants?>,
                    throwable: Throwable
                ) {
                    Log.d("Error", "onFailure: ${throwable.message.toString()}")
                }
            })

    }

    fun getFilteredMealsList(key: String?, value: String?, callback: (List<Filtered_Meal>) -> Unit){

        val queryMap : Map<String,String> = mapOf(key to value) as Map<String, String>

        MealServiceObject.filterMeals(queryMap)
            .enqueue(object : Callback<Filtered_Meals>{
                override fun onResponse(
                    request: Call<Filtered_Meals?>,
                    response: Response<Filtered_Meals?>
                ) {
                    if(response.isSuccessful){
                        callback(response.body()!!.filteredMealsList)
                    }
                }

                override fun onFailure(
                    request: Call<Filtered_Meals?>,
                    throwable: Throwable
                ) {
                    Log.d("Error", "onFailure: ${throwable.message.toString()}")
                }
            })


    }


    fun getMealById(id: String?, callback: (List<Detailed_Meal>) -> Unit){


        MealServiceObject.getMealByID(id)
            .enqueue(object : Callback<Detailed_Meals>{
                override fun onResponse(
                    request: Call<Detailed_Meals?>,
                    response : Response<Detailed_Meals?>
                ) {
                    if(response.isSuccessful){
                        callback(response.body()!!.detailedMealsList)
                    }
                }

                override fun onFailure(
                    request: Call<Detailed_Meals?>,
                    throwable: Throwable
                ) {
                    Log.d("ERROR", "onFailure: ${throwable.message.toString()}")
                }
            })


    }


}

