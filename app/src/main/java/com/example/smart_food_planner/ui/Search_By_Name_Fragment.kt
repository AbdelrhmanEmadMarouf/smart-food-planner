package com.example.smart_food_planner.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smart_food_planner.R
import com.example.smart_food_planner.model.dataClasses.Detailed_Meal
import com.example.smart_food_planner.model.dataClasses.Filtered_Meal
import com.example.smart_food_planner.model.dataClasses.Filtered_Meals
import com.example.smart_food_planner.ui.adapters.Filtered_Meal_Adapter
import com.example.smart_food_planner.viewmodel.MealsViewModel

class Search_By_Name_Fragment : Fragment() {



    private lateinit var recyclerView: RecyclerView

    private val mealViewModel : MealsViewModel by viewModels()

    private var meals = listOf<Detailed_Meal>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search__by__name_, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView = view.findViewById(R.id.search_by_name_recycler_view)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        val  searchQuery = arguments?.getString("search query").toString()

        Log.d("TESTT", searchQuery)


        mealViewModel.getMealByName(searchQuery)

        mealViewModel.searchedMeals.observe(viewLifecycleOwner){ list->
            meals = list

            val filteredMeals = mutableListOf<Filtered_Meal>()

            meals.forEach {
                filteredMeals.add(
                    Filtered_Meal(
                        it.idMeal,
                        it.strMeal,
                        it.strMealThumb
                    )
                )
            }



            recyclerView.adapter = Filtered_Meal_Adapter(filteredMeals)

        }





    }





}