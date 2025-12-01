package com.example.smart_food_planner.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import com.example.smart_food_planner.R
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smart_food_planner.model.dataClasses.Filtered_Meal
import com.example.smart_food_planner.model.dataClasses.Meal
import com.example.smart_food_planner.ui.Details_Meal_Fragment
import com.example.smart_food_planner.ui.Filtered_Meals
import com.example.smart_food_planner.viewmodel.Meal_Database_Viewmodel

class Filtered_Meal_Adapter(
    private val filtered_meals_list: List<Filtered_Meal>,
) : RecyclerView.Adapter<Filtered_Meal_Adapter.Filterd_Meal_ViewHolder>()  {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Filterd_Meal_ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_meal, parent, false)
        return Filterd_Meal_ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: Filterd_Meal_ViewHolder,
        position: Int
    ) {



        val meal = filtered_meals_list[position]
        holder.mealTitle.text = meal.strMeal


        Glide.with(holder.itemView.context)
            .load(meal.strMealThumb)
            .into(holder.mealImage)

        holder.mealDetailsButton.setOnClickListener {

            val fragment = Details_Meal_Fragment()

            val args = bundleOf(
                "Meal Id" to meal.idMeal
            )
            fragment.arguments = args

            val activity = holder.itemView.context as? AppCompatActivity
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.add(R.id.container, fragment)
                ?.addToBackStack(null)
                ?.commit()


        }

    }

    override fun getItemCount(): Int = filtered_meals_list.size


    inner class Filterd_Meal_ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mealImage: ImageView = itemView.findViewById(R.id.Meal_Image)
        val mealTitle: TextView = itemView.findViewById(R.id.Meal_Name)
        val mealDetailsButton: ImageButton = itemView.findViewById(R.id.meal_details_button)

    }

}

