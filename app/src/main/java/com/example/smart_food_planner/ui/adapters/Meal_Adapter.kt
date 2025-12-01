package com.example.smart_food_planner.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smart_food_planner.R
import com.example.smart_food_planner.database.dataclasses.Meal_Data
import com.example.smart_food_planner.model.dataClasses.Meal
import com.example.smart_food_planner.viewmodel.Meal_Database_Viewmodel
import java.security.PrivateKey
import java.time.LocalDate
import java.util.Calendar

class Meal_Adapter(
    private val items: List<Meal>,
    private val viewModel: Meal_Database_Viewmodel,
    private val  numberOfMeal : Int,
    private val dialog: android.app.AlertDialog,
    private val isAdd : Boolean,
    private val day : Int ,
    private val month : Int ,
    private val year : Int
) : RecyclerView.Adapter<Meal_Adapter.MealViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meals_recyclerview_item_in_calender, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = items[position]
        holder.mealTitle.text = meal.strMealTitle
        holder.Meal_Description.text = meal.strMealDescription


        Glide.with(holder.itemView.context)
            .load(meal.strMealUrl)
            .into(holder.mealImage)

        holder.btnAdd.setOnClickListener {

            val currentMeal = Meal_Data(meal.idMeal.toInt(),day,month,year,numberOfMeal)

            if(isAdd){
                viewModel.addMeal(currentMeal)
            }else{
                viewModel.updateMeal(currentMeal)
            }

            dialog.dismiss()


        }
    }

    override fun getItemCount(): Int = items.size

    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mealImage: ImageView = itemView.findViewById(R.id.Meal_Image)

        val mealTitle: TextView = itemView.findViewById(R.id.Meal_Ttile)
        val Meal_Description : TextView = itemView.findViewById(R.id.Meal_Description)
        val btnAdd: Button = itemView.findViewById(R.id.AddButton)
    }

}
