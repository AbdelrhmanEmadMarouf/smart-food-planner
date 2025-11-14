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


data class Countries_Name(
    @SerializedName("meals")
    val countriesList: List<Country>
)

data class Country(
    val strArea: String
)

data class Ingrediants(
    @SerializedName("meals")
    val Ingrediants_list: List<Ingrediant_Item>
)

data class Ingrediant_Item(
    val idIngredient: String,
    val strDescription: String,
    val strIngredient: String,
    val strThumb: String,
    val strType: String
)


data class Filtered_Meals(
    @SerializedName("meals")
    val filteredMealsList: List<Filtered_Meal>
)


data class Filtered_Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)


