package com.example.homeui_foodplannerproject.model



data class CategoriesResponse(
    val categories: List<Category>
)

data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
)

data class CategoryWithMeals(
    val categoryName: String,
    val meals: List<Meal>
)
