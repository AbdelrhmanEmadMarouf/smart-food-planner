package com.example.smart_food_planner.ui.adapters


import android.view.LayoutInflater
import com.example.smart_food_planner.R
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smart_food_planner.model.dataClasses.Countries_Name
import com.example.smart_food_planner.model.dataClasses.Country


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
            "American" -> holder.countryImage.setBackgroundResource(R.drawable.american)
            "Argentinian" -> holder.countryImage.setBackgroundResource(R.drawable.argentinian)
            "Australian" -> holder.countryImage.setBackgroundResource(R.drawable.australian)
            "British" -> holder.countryImage.setBackgroundResource(R.drawable.british)
            "Canadian" -> holder.countryImage.setBackgroundResource(R.drawable.canadian)
            "Chinese" -> holder.countryImage.setBackgroundResource(R.drawable.chinese)
            "Croatian" -> holder.countryImage.setBackgroundResource(R.drawable.croatian)
            "Dutch" -> holder.countryImage.setBackgroundResource(R.drawable.dutch)
            "Egyptian" -> holder.countryImage.setBackgroundResource(R.drawable.egyptian)
            "Filipino" -> holder.countryImage.setBackgroundResource(R.drawable.filipino)
            "French" -> holder.countryImage.setBackgroundResource(R.drawable.french)
            "Greek" -> holder.countryImage.setBackgroundResource(R.drawable.greek)
            "Indian" -> holder.countryImage.setBackgroundResource(R.drawable.indian)
            "Irish" -> holder.countryImage.setBackgroundResource(R.drawable.irish)
            "Italian" -> holder.countryImage.setBackgroundResource(R.drawable.italian)
            "Jamaican" -> holder.countryImage.setBackgroundResource(R.drawable.jamaican)
            "Japanese" -> holder.countryImage.setBackgroundResource(R.drawable.japanese)
            "Kenyan" -> holder.countryImage.setBackgroundResource(R.drawable.kenyan)
            "Malaysian" -> holder.countryImage.setBackgroundResource(R.drawable.malaysian)
            "Mexican" -> holder.countryImage.setBackgroundResource(R.drawable.mexican)
            "Moroccan" -> holder.countryImage.setBackgroundResource(R.drawable.moroccan)
            "Norwegian" -> holder.countryImage.setBackgroundResource(R.drawable.norwegian)
            "Polish" -> holder.countryImage.setBackgroundResource(R.drawable.polish)
            "Portuguese" -> holder.countryImage.setBackgroundResource(R.drawable.portuguese)
            "Russian" -> holder.countryImage.setBackgroundResource(R.drawable.russian)
            "Spanish" -> holder.countryImage.setBackgroundResource(R.drawable.spanish)
            "Syrian" -> holder.countryImage.setBackgroundResource(R.drawable.syrian)
            "Thai" -> holder.countryImage.setBackgroundResource(R.drawable.thai)
            "Tunisian" -> holder.countryImage.setBackgroundResource(R.drawable.tunisian)
            "Turkish" -> holder.countryImage.setBackgroundResource(R.drawable.turkish)
            "Ukrainian" -> holder.countryImage.setBackgroundResource(R.drawable.ukrainian)
            "Uruguayan" -> holder.countryImage.setBackgroundResource(R.drawable.uruguayan)
            "Vietnamese" -> holder.countryImage.setBackgroundResource(R.drawable.vietnamese)
        }


    }

    override fun getItemCount(): Int = Country_Cards_List.size


    inner class Country_Names_ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryImage: ImageView = itemView.findViewById(R.id.Country_image_view)
        val countryName: TextView = itemView.findViewById(R.id.Country_name)
    }

}

