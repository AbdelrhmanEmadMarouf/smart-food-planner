package com.example.smart_food_planner.data.repository

import android.util.Log
import com.example.smart_food_planner.model.dataClasses.CategoryNamesList
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

//    fun getListOfCategoriesName(){
//        MealServiceObject.getListOfCategoriesName()
//            .enqueue(object : Callback<CategoryNamesList>{
//                override fun onResponse(
//                    request: Call<CategoryNamesList?>,
//                    response: Response<CategoryNamesList?>
//                ) {
//                    if(response.isSuccessful){
//                        Log.d("Testt", "onResponse: ${response.code()}")
//                        Log.d("Testt", "onResponse: ${response.body()?.categoriesNameList.toString()}")
//                        val list = response.body()?.categoriesNameList
//
//                        list?.forEach {
//                            Log.d("Testt", "onResponse: ${it.strCategory}")
//                        }
//
//                    }
//                }
//
//                override fun onFailure(
//                    request: Call<CategoryNamesList?>,
//                    throwable: Throwable
//                ) {
//                    Log.i("Error", "onFailure: ${throwable.message.toString()}")
//                }
//
//            })
//    }


}

