package com.example.smart_food_planner.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.animation.AnimationUtils
import com.example.smart_food_planner.R
import com.example.smart_food_planner.model.dataClasses.Meal
import com.example.smart_food_planner.ui.adapters.Category_Names_Adapter
import com.example.smart_food_planner.viewmodel.MealsViewModel

class Categoty_Name_Fragment : Fragment() {

    private lateinit var category_items_recycler_view : RecyclerView
    private val mealViewModel: MealsViewModel by viewModels()
    private var mealsList: List<Meal> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categoty__name_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        category_items_recycler_view = view.findViewById(R.id.recycler_categories)


        val spanCount = 2
        category_items_recycler_view.layoutManager = GridLayoutManager(requireContext(), spanCount)

        category_items_recycler_view.setHasFixedSize(true)




        val layoutAnim = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_grid)
        category_items_recycler_view.layoutAnimation = layoutAnim


        mealViewModel.meals.observe(viewLifecycleOwner) { list ->
            mealsList = list
            category_items_recycler_view.adapter = Category_Names_Adapter(mealsList)

            category_items_recycler_view.scheduleLayoutAnimation()
        }

        mealViewModel.getMeals()
    }



}
