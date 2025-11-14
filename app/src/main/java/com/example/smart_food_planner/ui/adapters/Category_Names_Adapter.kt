package com.example.smart_food_planner.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import com.example.smart_food_planner.R
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smart_food_planner.model.dataClasses.Meal
import com.example.smart_food_planner.ui.Filtered_Meals
import com.example.smart_food_planner.ui.home
import com.example.smart_food_planner.viewmodel.Meal_Database_Viewmodel

class Category_Names_Adapter(
    private val Category_Cards_List: List<Meal>,
) : RecyclerView.Adapter<Category_Names_Adapter.Category_Names_ViewHolder>()  {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Category_Names_ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item_card_in_searchscreen, parent, false)
        return Category_Names_ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: Category_Names_ViewHolder,
        position: Int
    ) {
        val meal = Category_Cards_List[position]
        holder.mealTitle.text = meal.strMealTitle


        Glide.with(holder.itemView.context)
            .load(meal.strMealUrl)
            .into(holder.mealImage)


        holder.recyclerViewItem.setOnClickListener {

            holder.recyclerViewItem.setOnClickListener {



                val fragment = Filtered_Meals()

                val args = bundleOf(
                    "Fragment title" to meal.strMealTitle,
                    "key" to "c"
                )
                fragment.arguments = args

                val activity = holder.itemView.context as? AppCompatActivity
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.container, fragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }

        }



    }

    override fun getItemCount(): Int = Category_Cards_List.size


    inner class Category_Names_ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mealImage: ImageView = itemView.findViewById(R.id.Category_image_view)
        val mealTitle: TextView = itemView.findViewById(R.id.Category_item_title)

        val recyclerViewItem : ConstraintLayout = itemView.findViewById(R.id.Recycler_View_Item)



    }

}

