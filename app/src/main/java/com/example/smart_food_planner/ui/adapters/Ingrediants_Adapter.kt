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
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smart_food_planner.model.dataClasses.Ingrediant_Item
import com.example.smart_food_planner.ui.Filtered_Meals

class Ingrediants_Adapter(
    private val Ingrediants_List: List<Ingrediant_Item>,
) : RecyclerView.Adapter<Ingrediants_Adapter.Ingrediants_ViewHolder>()  {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Ingrediants_ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingrediant_card_item_in_search_screen, parent, false)
        return Ingrediants_ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: Ingrediants_ViewHolder,
        position: Int
    ) {




        val Ingrediant = Ingrediants_List[position]

        if(Ingrediant.strType==null){
            holder.IngrediantType.text = "Type"
        }else{
            holder.IngrediantType.text = Ingrediant.strType
        }

        holder.IngrediantName.text = Ingrediant.strIngredient



            Glide.with(holder.itemView.context)
                .load(Ingrediant.strThumb)
                .placeholder(R.drawable.ingrediant_image)
                .error(R.drawable.ingrediant_image)
                .fallback(R.drawable.ingrediant_image)
                .centerCrop()
                .into(holder.IngrediantImage)



        holder.recyclerViewItem.setOnClickListener {


                val fragment = Filtered_Meals()

                val args = bundleOf(
                    "Fragment title" to holder.IngrediantName.text,
                    "key" to "i"
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

    override fun getItemCount(): Int = Ingrediants_List.size


    inner class Ingrediants_ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val IngrediantImage: ImageView = itemView.findViewById(R.id.meal_image)
        val IngrediantType: TextView = itemView.findViewById(R.id.tv_category)
        val IngrediantName: TextView = itemView.findViewById(R.id.tv_title)

        val recyclerViewItem : ConstraintLayout = itemView.findViewById(R.id.Ingrediants_card_id)

    }

}

