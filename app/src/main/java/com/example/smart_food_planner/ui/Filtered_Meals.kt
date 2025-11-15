package com.example.smart_food_planner.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.smart_food_planner.R
import com.example.smart_food_planner.model.dataClasses.Filtered_Meal
import com.example.smart_food_planner.ui.adapters.Filtered_Meal_Adapter
import com.example.smart_food_planner.viewmodel.MealsViewModel
import kotlin.getValue


class Filtered_Meals : Fragment() {



    private lateinit var titleTextView: TextView
    private lateinit var btnBack : ImageButton

    private lateinit var meals_recycler_view : RecyclerView
    private val mealViewModel: MealsViewModel by viewModels()
    private var mealsList: List<Filtered_Meal> = emptyList()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filtered__meals, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        titleTextView=view.findViewById(R.id.Filtration_Title)
        btnBack = view.findViewById(R.id.btn_back)


        meals_recycler_view = view.findViewById(R.id.filtered_meals_recycler_view)
        meals_recycler_view.setHasFixedSize(true)


        meals_recycler_view.layoutManager = androidx.recyclerview.widget.GridLayoutManager(requireContext(),1)

        val layoutAnim = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_grid)
        meals_recycler_view.layoutAnimation = layoutAnim

       val  fragmentTitle = arguments?.getString("Fragment title")
       val  key = arguments?.getString("key")

        titleTextView.text = fragmentTitle+" Meals"

        btnBack.setOnClickListener {
            val fragment = Search()
            val activity = context as? AppCompatActivity
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, fragment)
                ?.addToBackStack(null)
                ?.commit()
        }


        mealViewModel.filteredMeals
            .observe(viewLifecycleOwner) { list ->

                mealsList = list
                meals_recycler_view.adapter = Filtered_Meal_Adapter(mealsList)
                meals_recycler_view.scheduleLayoutAnimation()
        }

        mealViewModel.getFilteredMealsList(key , fragmentTitle)


    }





}