package com.example.smart_food_planner.ui.adapters

import android.view.LayoutInflater
import com.example.smart_food_planner.R
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smart_food_planner.model.dataClasses.IngrediantItem




class Ingrediants_detals_screen_adapter(
    private val Ingrediants_List: List<IngrediantItem>,
) : RecyclerView.Adapter<Ingrediants_detals_screen_adapter.Ingrediants_ViewHolder>()  {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Ingrediants_ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingrediant_item_in_details_scrrem, parent, false)
        return Ingrediants_ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: Ingrediants_ViewHolder,
        position: Int
    ) {




        val Ingrediant = Ingrediants_List[position]


        holder.IngrediantName.text = Ingrediant.ingredientName


        Glide.with(holder.itemView.context)
            .load(Ingrediant.image_url)
            .placeholder(R.drawable.ingrediant_image)
            .error(R.drawable.ingrediant_image)
            .fallback(R.drawable.ingrediant_image)
            .centerCrop()
            .into(holder.IngrediantImage)


        holder.IngrediantQuantity.text = Ingrediant.ingredientQuantity



    }

    override fun getItemCount(): Int = Ingrediants_List.size


    inner class Ingrediants_ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val IngrediantImage: ImageView = itemView.findViewById(R.id.ivIngredientThumb)
        val IngrediantQuantity: TextView = itemView.findViewById(R.id.tvIngredientMeasure)
        val IngrediantName: TextView = itemView.findViewById(R.id.tvIngredientName)


    }

}