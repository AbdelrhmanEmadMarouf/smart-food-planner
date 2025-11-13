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

    private val mealRepository = Meal_Repository()


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


//        mealRepository.getListOfCategoriesName()


        btn_category = view.findViewById(R.id.btn_category)
        btn_ingredients = view.findViewById(R.id.btn_ingredients)
        btn_country = view.findViewById(R.id.btn_country)



        btn_category.setOnClickListener{
            make_all_buttons_unselected()
            btn_category.setBackgroundResource(R.drawable.segment_selected)
            btn_category.setTextColor(ContextCompat.getColor(requireContext(), R.color.segment_selected_text))
        }
        btn_ingredients.setOnClickListener{
            make_all_buttons_unselected()
            btn_ingredients.setBackgroundResource(R.drawable.segment_selected)
            btn_ingredients.setTextColor(ContextCompat.getColor(requireContext(), R.color.segment_selected_text))
        }
        btn_country.setOnClickListener{
            make_all_buttons_unselected()
            btn_country.setBackgroundResource(R.drawable.segment_selected)
            btn_country.setTextColor(ContextCompat.getColor(requireContext(), R.color.segment_selected_text))
        }


    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Search().apply {
                arguments = Bundle().apply {

                }
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

}