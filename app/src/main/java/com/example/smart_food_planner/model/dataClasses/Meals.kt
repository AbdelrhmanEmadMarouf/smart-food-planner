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


data class Detailed_Meals(
    @SerializedName("meals")
    val detailedMealsList : List<Detailed_Meal>
)

data class Detailed_Meal(
    val dateModified: String,
    val idMeal: String,
    val strArea: String,
    val strCategory: String,
    val strCreativeCommonsConfirmed: String,
    val strImageSource: String,
    val strIngredient1: String,
    val strIngredient10: String,
    val strIngredient11: String,
    val strIngredient12: String,
    val strIngredient13: String,
    val strIngredient14: String,
    val strIngredient15: String,
    val strIngredient16: String,
    val strIngredient17: String,
    val strIngredient18: String,
    val strIngredient19: String,
    val strIngredient2: String,
    val strIngredient20: String,
    val strIngredient3: String,
    val strIngredient4: String,
    val strIngredient5: String,
    val strIngredient6: String,
    val strIngredient7: String,
    val strIngredient8: String,
    val strIngredient9: String,
    val strInstructions: String,
    val strMeal: String,
    val strMealAlternate: String,
    val strMealThumb: String,
    val strMeasure1: String,
    val strMeasure10: String,
    val strMeasure11: String,
    val strMeasure12: String,
    val strMeasure13: String,
    val strMeasure14: String,
    val strMeasure15: String,
    val strMeasure16: String,
    val strMeasure17: String,
    val strMeasure18: String,
    val strMeasure19: String,
    val strMeasure2: String,
    val strMeasure20: String,
    val strMeasure3: String,
    val strMeasure4: String,
    val strMeasure5: String,
    val strMeasure6: String,
    val strMeasure7: String,
    val strMeasure8: String,
    val strMeasure9: String,
    val strSource: String,
    val strTags: String,
    val strYoutube: String
)


data class IngrediantItem(
    val image_url : String ,
    val ingredientName : String ,
    val ingredientQuantity : String
)

