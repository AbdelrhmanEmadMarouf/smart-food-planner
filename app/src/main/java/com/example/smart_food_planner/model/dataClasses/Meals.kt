package com.example.smart_food_planner.model.dataClasses

import com.google.gson.annotations.SerializedName

data class Meals(
    @SerializedName("categories")
    val categories: List<Meal>
)

data class Meal(
    @SerializedName("idCategory")
    val idMeal: String,

    @SerializedName("strCategory")
    val strMealTitle: String,

    @SerializedName("strCategoryDescription")
    val strMealDescription: String,

    @SerializedName("strCategoryThumb")
    val strMealUrl: String
)



data class CategoryNamesList(
    @SerializedName("meals")
    val categoriesNameList: List<CategoryName>
)

data class CategoryName(
    @SerializedName("strCategory")
    val strCategory: String
)



//data class ListOfCategoriesName(
//    val CategoriesNamesList : MutableList<CategoryName>
//)
//data class ListOfCountriesName(
////    val CountriesNameList : MutableList<String>
////)
//
//data class CategoryName(
//    val strCategory: String
//)






