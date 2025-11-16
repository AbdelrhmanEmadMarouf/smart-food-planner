package com.example.smart_food_planner.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import com.example.smart_food_planner.R
import com.example.smart_food_planner.data.repository.Meal_Repository


class Search : Fragment() {

    private lateinit var btn_category: TextView
    private lateinit var btn_ingredients: TextView
    private lateinit var btn_country: TextView
    private lateinit var searchField: TextView


    private lateinit var searchButton: AppCompatImageView


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
        searchButton = view.findViewById(R.id.ic_search)
        searchField = view.findViewById(R.id.et_search)


        searchField.addTextChangedListener { editable ->
            val text = editable.toString()

            loadSearchFragment(text)
        }






        btn_category.setOnClickListener {
            make_all_buttons_unselected()
            btn_category.setBackgroundResource(R.drawable.segment_selected)
            btn_category.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.segment_selected_text
                )
            )
            loadFragment(Categoty_Name_Fragment())
            searchField.text = null
        }
        btn_ingredients.setOnClickListener {
            make_all_buttons_unselected()
            btn_ingredients.setBackgroundResource(R.drawable.segment_selected)
            btn_ingredients.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.segment_selected_text
                )
            )
            loadFragment(Ingrediants_Fragment())
            searchField.text = null
        }
        btn_country.setOnClickListener {
            make_all_buttons_unselected()
            btn_country.setBackgroundResource(R.drawable.segment_selected)
            btn_country.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.segment_selected_text
                )
            )
            loadFragment(Country_Name_Fragment())
            searchField.text = null
        }

        searchButton.setOnClickListener {
            loadSearchFragment(searchField.text.toString())
        }


    }


    private fun make_all_buttons_unselected() {
        btn_category.setBackgroundResource(R.drawable.segment_unselected)
        btn_category.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.segment_unselected_text
            )
        )

        btn_ingredients.setBackgroundResource(R.drawable.segment_unselected)
        btn_ingredients.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.segment_unselected_text
            )
        )

        btn_country.setBackgroundResource(R.drawable.segment_unselected)
        btn_country.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.segment_unselected_text
            )
        )
    }

    private fun loadFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }

    private fun loadSearchFragment(searchedQuery: String) {

        if(searchedQuery.isEmpty()) return


        val fragment = Search_By_Name_Fragment()
        fragment.arguments = bundleOf("search query" to searchedQuery)

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
            .commit()



        make_all_buttons_unselected()


    }





}