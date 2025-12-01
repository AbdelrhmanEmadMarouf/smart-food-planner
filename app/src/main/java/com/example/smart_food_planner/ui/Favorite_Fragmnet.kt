package com.example.smart_food_planner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smart_food_planner.R
import com.example.smart_food_planner.database.dataclasses.FavoriteMeals
import com.example.smart_food_planner.model.dataClasses.Filtered_Meal
import com.example.smart_food_planner.ui.adapters.Filtered_Meal_Adapter
import com.example.smart_food_planner.viewmodel.Favorite_Meals_Viewmodel


class Favorite_Fragmnet : Fragment() {


    private var favoriteMealsId = listOf<FavoriteMeals>()

    private lateinit var favoriteMealsRecyclerView: RecyclerView

    private val favoriteMealsViewModel: Favorite_Meals_Viewmodel by this.viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite__fragmnet, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        favoriteMealsRecyclerView = view.findViewById(R.id.favorite_meals_recycler_view)

        favoriteMealsRecyclerView.setHasFixedSize(true)

        favoriteMealsRecyclerView.layoutManager = LinearLayoutManager(requireContext())



        favoriteMealsViewModel.getFavoriteMeals()

        favoriteMealsViewModel.favorite_meals.observe(viewLifecycleOwner) { meals ->
            favoriteMealsId = meals

            val adapterList = mutableListOf<Filtered_Meal>()
            meals.forEach {

                adapterList.add(
                    Filtered_Meal(
                        it.mealId,
                        it.strMeal,
                        it.strMealThumb
                    )
                )
            }

            favoriteMealsRecyclerView.adapter = Filtered_Meal_Adapter(adapterList)


        }


    }


}