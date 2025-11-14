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
import com.example.smart_food_planner.model.dataClasses.Countries_Name
import com.example.smart_food_planner.model.dataClasses.Country
import com.example.smart_food_planner.ui.Filtered_Meals
import com.google.android.material.imageview.ShapeableImageView


class Country_Names_Adapter(
    private val Country_Cards_List: List<Country>,
) : RecyclerView.Adapter<Country_Names_Adapter.Country_Names_ViewHolder>()  {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Country_Names_ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.country_card_item_in_scearch_screen, parent, false)
        return Country_Names_ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: Country_Names_ViewHolder,
        position: Int
    ) {
        val country = Country_Cards_List[position]
        holder.countryName.text = country.strArea



        when(holder.countryName.text){
            "American" -> holder.countryImage.setImageResource(R.drawable.american)
            "Argentinian" -> holder.countryImage.setImageResource(R.drawable.argentinian)
            "Australian" -> holder.countryImage.setImageResource(R.drawable.australian)
            "British" -> holder.countryImage.setImageResource(R.drawable.british)
            "Canadian" -> holder.countryImage.setImageResource(R.drawable.canadian)
            "Chinese" -> holder.countryImage.setImageResource(R.drawable.chinese)
            "Croatian" -> holder.countryImage.setImageResource(R.drawable.croatian)
            "Dutch" -> holder.countryImage.setImageResource(R.drawable.dutch)
            "Egyptian" -> holder.countryImage.setImageResource(R.drawable.egyptian)
            "Filipino" -> holder.countryImage.setImageResource(R.drawable.filipino)
            "French" -> holder.countryImage.setImageResource(R.drawable.french)
            "Greek" -> holder.countryImage.setImageResource(R.drawable.greek)
            "Irish" -> holder.countryImage.setImageResource(R.drawable.irish)
            "Italian" -> holder.countryImage.setImageResource(R.drawable.italian)
            "Jamaican" -> holder.countryImage.setImageResource(R.drawable.jamaican)
            "Japanese" -> holder.countryImage.setImageResource(R.drawable.japanese)
            "Kenyan" -> holder.countryImage.setImageResource(R.drawable.kenyan)
            "Malaysian" -> holder.countryImage.setImageResource(R.drawable.malaysian)
            "Mexican" -> holder.countryImage.setImageResource(R.drawable.mexican)
            "Moroccan" -> holder.countryImage.setImageResource(R.drawable.moroccan)
            "Norwegian" -> holder.countryImage.setImageResource(R.drawable.norwegian)
            "Polish" -> holder.countryImage.setImageResource(R.drawable.polish)
            "Portuguese" -> holder.countryImage.setImageResource(R.drawable.portuguese)
            "Russian" -> holder.countryImage.setImageResource(R.drawable.russian)
            "Spanish" -> holder.countryImage.setImageResource(R.drawable.spanish)
            "Syrian" -> holder.countryImage.setImageResource(R.drawable.syrian)
            "Thai" -> holder.countryImage.setImageResource(R.drawable.thai)
            "Tunisian" -> holder.countryImage.setImageResource(R.drawable.tunisian)
            "Turkish" -> holder.countryImage.setImageResource(R.drawable.turkish)
            "Ukrainian" -> holder.countryImage.setImageResource(R.drawable.ukrainian)
            "Uruguayan" -> holder.countryImage.setImageResource(R.drawable.uruguayan)
            "Vietnamese" -> holder.countryImage.setImageResource(R.drawable.vietnamese)
        }


        holder.recyclerViewItem.setOnClickListener {



            val fragment = Filtered_Meals()

            val args = bundleOf(
                "Fragment title" to holder.countryName.text,
                "key" to "a"
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

    override fun getItemCount(): Int = Country_Cards_List.size


    inner class Country_Names_ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryImage: ShapeableImageView = itemView.findViewById(R.id.Country_image_view)
        val countryName: TextView = itemView.findViewById(R.id.Country_name)

        val recyclerViewItem : ConstraintLayout = itemView.findViewById(R.id.Country_Card_id)

    }

}

