package com.example.smart_food_planner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smart_food_planner.R
import com.example.smart_food_planner.model.dataClasses.Country
import com.example.smart_food_planner.model.dataClasses.Ingrediant_Item
import com.example.smart_food_planner.ui.adapters.Country_Names_Adapter
import com.example.smart_food_planner.ui.adapters.Ingrediants_Adapter
import com.example.smart_food_planner.viewmodel.MealsViewModel

class Ingrediants_Fragment : Fragment() {


    val mealViewModel : MealsViewModel by viewModels()

    private lateinit var IngrediantsRecyclerView : RecyclerView
    private var IngrediantList = listOf<Ingrediant_Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingrediants_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        IngrediantsRecyclerView = view.findViewById(R.id.recycler_ingrediants)

        val layoutAnim = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_grid)
        IngrediantsRecyclerView.layoutAnimation = layoutAnim


        IngrediantsRecyclerView.setHasFixedSize(true)

        val spanCount = 2
        IngrediantsRecyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)

        mealViewModel.ingrediantsList.observe(viewLifecycleOwner){list->
            IngrediantList =list
            IngrediantsRecyclerView.adapter = Ingrediants_Adapter(IngrediantList)
            IngrediantsRecyclerView.scheduleLayoutAnimation()

        }

        mealViewModel.getListOfIngrediants()



    }

}