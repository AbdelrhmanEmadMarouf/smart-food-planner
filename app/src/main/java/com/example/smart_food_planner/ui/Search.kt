package com.example.smart_food_planner.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import com.example.smart_food_planner.R
import com.example.smart_food_planner.data.repository.Meal_Repository


class Search : Fragment() {

    private lateinit var btn_category: TextView
    private lateinit var btn_ingredients: TextView
    private lateinit var btn_country: TextView


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
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        loadFragment(Categoty_Name_Fragment())

        btn_category = view.findViewById(R.id.btn_category)
        btn_ingredients = view.findViewById(R.id.btn_ingredients)
        btn_country = view.findViewById(R.id.btn_country)



        btn_category.setOnClickListener{
            make_all_buttons_unselected()
            btn_category.setBackgroundResource(R.drawable.segment_selected)
            btn_category.setTextColor(ContextCompat.getColor(requireContext(), R.color.segment_selected_text))
            loadFragment(Categoty_Name_Fragment())
        }
        btn_ingredients.setOnClickListener{
            make_all_buttons_unselected()
            btn_ingredients.setBackgroundResource(R.drawable.segment_selected)
            btn_ingredients.setTextColor(ContextCompat.getColor(requireContext(), R.color.segment_selected_text))
            loadFragment(Ingrediants_Fragment())
        }
        btn_country.setOnClickListener{
            make_all_buttons_unselected()
            btn_country.setBackgroundResource(R.drawable.segment_selected)
            btn_country.setTextColor(ContextCompat.getColor(requireContext(), R.color.segment_selected_text))
            loadFragment(Country_Name_Fragment())
        }


    }


    private fun make_all_buttons_unselected() {
        btn_category.setBackgroundResource(R.drawable.segment_unselected)
        btn_category.setTextColor(ContextCompat.getColor(requireContext(), R.color.segment_unselected_text))

        btn_ingredients.setBackgroundResource(R.drawable.segment_unselected)
        btn_ingredients.setTextColor(ContextCompat.getColor(requireContext(), R.color.segment_unselected_text))

        btn_country.setBackgroundResource(R.drawable.segment_unselected)
        btn_country.setTextColor(ContextCompat.getColor(requireContext(), R.color.segment_unselected_text))
    }

    private fun loadFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }


}