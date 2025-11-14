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
import com.example.smart_food_planner.ui.adapters.Category_Names_Adapter
import com.example.smart_food_planner.ui.adapters.Country_Names_Adapter
import com.example.smart_food_planner.viewmodel.MealsViewModel


class Country_Name_Fragment : Fragment() {

    private val mealViewModel : MealsViewModel by viewModels()

    private lateinit var countriesNameRecyclerView : RecyclerView
    private var countriesNameList = listOf<Country>()

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
        return inflater.inflate(R.layout.fragment_country__name_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        countriesNameRecyclerView = view.findViewById(R.id.recycler_countries_name)

        val layoutAnim = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_grid)
        countriesNameRecyclerView.layoutAnimation = layoutAnim


        countriesNameRecyclerView.setHasFixedSize(true)

        val spanCount = 2
        countriesNameRecyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)

        mealViewModel.countries.observe(viewLifecycleOwner){list->
            countriesNameList =list
            countriesNameRecyclerView.adapter = Country_Names_Adapter(countriesNameList)
            countriesNameRecyclerView.scheduleLayoutAnimation()

        }

        mealViewModel.getListOfCountriesName()


    }


}