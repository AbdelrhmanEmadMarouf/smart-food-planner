package com.example.smart_food_planner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smart_food_planner.R
import com.example.smart_food_planner.model.dataClasses.Detailed_Meal
import com.example.smart_food_planner.model.dataClasses.IngrediantItem
import com.example.smart_food_planner.model.dataClasses.Ingrediant_Item
import com.example.smart_food_planner.ui.adapters.Ingrediants_detals_screen_adapter
import com.example.smart_food_planner.viewmodel.MealsViewModel
import com.google.android.material.imageview.ShapeableImageView


import kotlin.getValue
// imports مهمة
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import com.example.smart_food_planner.database.dataclasses.FavoriteMeals
import com.example.smart_food_planner.viewmodel.Favorite_Meals_Viewmodel

class Details_Meal_Fragment : Fragment() {


    private lateinit var currentMeal: Detailed_Meal
    private lateinit var MealImage: ImageView
    private lateinit var mealCountryFlage: ShapeableImageView

    private lateinit var mealName: TextView
    private lateinit var mealCountry: TextView
    private lateinit var mealCategory: TextView
    private lateinit var numberOfIngredientsItems: TextView
    private lateinit var numberOfSteps: TextView
    private lateinit var CongratulationText: TextView
    private lateinit var InstructionText: TextView

    private lateinit var favoriteButton: AppCompatButton
    private lateinit var nextStepButton: AppCompatButton
    private lateinit var lastStepButton: AppCompatButton
    private lateinit var askAiButton: AppCompatButton

    private lateinit var ingrediants_recycler_view: RecyclerView

    private lateinit var stepsProgressBar: ProgressBar
    private var favoriteButtonIsSelected = false

    private val mealViewModel: MealsViewModel by viewModels()


    private val currentMealIngredientsList = mutableListOf<String>()
    private val currentMealMeasuresList = mutableListOf<String>()
    private var allIngredientsList = listOf<Ingrediant_Item>()

    private var instructionsList = listOf<String>()


    private lateinit var webView: WebView

    private var currentStep = 1

    private val favoriteMealsViewModel: Favorite_Meals_Viewmodel by this.viewModels()

    private var favoriteMealIds = listOf<FavoriteMeals>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details__meal_, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        MealImage = view.findViewById(R.id.DetaildMealImage)
        mealName = view.findViewById(R.id.detaildMealTitle)
        mealCountry = view.findViewById(R.id.meal_country)
        mealCategory = view.findViewById(R.id.meal_category)
        mealCountryFlage = view.findViewById(R.id.Meal_Flag)
        favoriteButton = view.findViewById(R.id.favorite_button)
        ingrediants_recycler_view = view.findViewById(R.id.rv_ingredients)
        numberOfIngredientsItems = view.findViewById(R.id.tv_ingredients_count)
        numberOfSteps = view.findViewById(R.id.tv_steps_status)
        stepsProgressBar = view.findViewById(R.id.steps_progress)
        nextStepButton = view.findViewById(R.id.btn_next_step)
        lastStepButton = view.findViewById(R.id.btn_last_step)
        CongratulationText = view.findViewById(R.id.Congratulation_title)
        InstructionText = view.findViewById(R.id.tv_step_sub)
        webView = view.findViewById(R.id.webView)
        askAiButton = view.findViewById(R.id.btn_ask_bot)


        val layoutAnim =
            AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_grid)
        ingrediants_recycler_view.layoutAnimation = layoutAnim

        ingrediants_recycler_view.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        ingrediants_recycler_view.setHasFixedSize(true)


        val MealId = arguments?.getString("Meal Id")



        favoriteMealsViewModel.favorite_meals.observe(viewLifecycleOwner) { Ids ->
            favoriteMealIds = Ids


            favoriteMealIds.forEach {
                if (it.mealId == MealId) {
                    favoriteButtonIsSelected = true
                    favoriteButton.setBackgroundResource(R.drawable.fav_button_selected)
                    favoriteButton.text = "Remove From Favorites"
                }
            }


        }


        favoriteMealsViewModel.getFavoriteMeals()





        mealViewModel.getMealBId(MealId)

        mealViewModel.detailedMeal.observe(viewLifecycleOwner) { meal ->
            currentMeal = meal
            mealViewModel.getListOfIngrediants()

            webView.loadUrl(currentMeal.strYoutube)
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = WebViewClient()

            prepareCardMeal()
            setStepsProcess()
        }

        mealViewModel.ingrediantsList.observe(viewLifecycleOwner) { allIngredients ->
            allIngredientsList = allIngredients
            setIngredientsRecyclerView()
        }




        favoriteButton.setOnClickListener {
            animateFavoriteButton(favoriteButton)

            if (favoriteButtonIsSelected) {
                favoriteButtonIsSelected = false
                favoriteButton.setBackgroundResource(R.drawable.fav_button_unselected)
                favoriteButton.text = "♡  Add to Favorites"

                favoriteMealsViewModel.deleteMeal(
                    FavoriteMeals(
                        currentMeal.idMeal,
                        currentMeal.strMeal,
                        currentMeal.strMealThumb
                    )
                )
            } else {
                favoriteButtonIsSelected = true
                favoriteButton.setBackgroundResource(R.drawable.fav_button_selected)
                favoriteButton.text = "Remove From Favorites"
                favoriteMealsViewModel.addFavoriteMeal(
                    FavoriteMeals(
                        currentMeal.idMeal,
                        currentMeal.strMeal,
                        currentMeal.strMealThumb
                    )
                )
            }
        }

        nextStepButton.setOnClickListener {
            animateFavoriteButton(nextStepButton)


            if (currentStep == instructionsList.size - 1) {
                currentStep++
                nextStepButton.visibility = View.GONE
                CongratulationText.visibility = View.VISIBLE
                InstructionText.text = "Have a good time"


            } else if (currentStep < instructionsList.size) {
                currentStep++
                InstructionText.text = instructionsList[currentStep - 1]
                CongratulationText.visibility = View.GONE

            }

            stepsProgressBar.progress++

            numberOfSteps.text = "step ${currentStep} of ${instructionsList.size}"

            if (currentStep != 1) {
                lastStepButton.visibility = View.VISIBLE
            }

        }

        lastStepButton.setOnClickListener {
            animateFavoriteButton(nextStepButton)

            if (currentStep == 2) {

                lastStepButton.visibility = View.GONE

            }

            currentStep--
            stepsProgressBar.progress--
            InstructionText.text = instructionsList[currentStep - 1]
            numberOfSteps.text = "step ${currentStep} of ${instructionsList.size}"

            if (currentStep != instructionsList.size) {
                nextStepButton.visibility = View.VISIBLE
            }

        }

        askAiButton.setOnClickListener {
            animateFavoriteButton(askAiButton)


            val fragment = AI().apply {
                arguments = bundleOf("Meal Id" to currentMeal.idMeal)
            }


            (requireActivity() as? MainActivity)?.let { main ->
                main.setBottomNavVisible(false)
                main.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit()
            }


        }


    }


    fun prepareCardMeal() {

        mealName.text = currentMeal.strMeal
        mealCountry.text = currentMeal.strArea
        mealCategory.text = currentMeal.strCategory

        Glide.with(requireContext())
            .load(currentMeal.strMealThumb)
            .placeholder(R.drawable.ingrediant_image)
            .error(R.drawable.ingrediant_image)
            .fallback(R.drawable.ingrediant_image)
            .centerCrop()
            .into(MealImage)

        setCorrectFlag()


    }

    fun setCorrectFlag() {
        when (currentMeal.strArea) {
            "American" -> mealCountryFlage.setImageResource(R.drawable.american_icon)
            "Argentinian" -> mealCountryFlage.setImageResource(R.drawable.argentinian_icon)
            "Australian" -> mealCountryFlage.setImageResource(R.drawable.australian_icon)
            "British" -> mealCountryFlage.setImageResource(R.drawable.british_icon)
            "Canadian" -> mealCountryFlage.setImageResource(R.drawable.canadian_icon)
            "Chinese" -> mealCountryFlage.setImageResource(R.drawable.chinese_icon)
            "Croatian" -> mealCountryFlage.setImageResource(R.drawable.croatian_icon)
            "Dutch" -> mealCountryFlage.setImageResource(R.drawable.dutch_icon)
            "Egyptian" -> mealCountryFlage.setImageResource(R.drawable.egyptian_icon)
            "Filipino" -> mealCountryFlage.setImageResource(R.drawable.filipino_icon)
            "French" -> mealCountryFlage.setImageResource(R.drawable.french_icon)
            "Greek" -> mealCountryFlage.setImageResource(R.drawable.greek_icon)
            "Irish" -> mealCountryFlage.setImageResource(R.drawable.irish_icon)
            "Italian" -> mealCountryFlage.setImageResource(R.drawable.italian_icon)
            "Jamaican" -> mealCountryFlage.setImageResource(R.drawable.jamaican_icon)
            "Japanese" -> mealCountryFlage.setImageResource(R.drawable.japanese_icon)
            "Kenyan" -> mealCountryFlage.setImageResource(R.drawable.kenyan_icon)
            "Malaysian" -> mealCountryFlage.setImageResource(R.drawable.malaysian_icon)
            "Mexican" -> mealCountryFlage.setImageResource(R.drawable.mexican_icon)
            "Moroccan" -> mealCountryFlage.setImageResource(R.drawable.moroccan_icon)
            "Norwegian" -> mealCountryFlage.setImageResource(R.drawable.norwegian_icon)
            "Polish" -> mealCountryFlage.setImageResource(R.drawable.polish_icon)
            "Portuguese" -> mealCountryFlage.setImageResource(R.drawable.portuguese_icon)
            "Russian" -> mealCountryFlage.setImageResource(R.drawable.russian_icon)
            "Spanish" -> mealCountryFlage.setImageResource(R.drawable.spanish_icon)
            "Syrian" -> mealCountryFlage.setImageResource(R.drawable.syrian_icon)
            "Thai" -> mealCountryFlage.setImageResource(R.drawable.thai_icon)
            "Tunisian" -> mealCountryFlage.setImageResource(R.drawable.tunisian_icon)
            "Turkish" -> mealCountryFlage.setImageResource(R.drawable.turkish_icon)
            "Ukrainian" -> mealCountryFlage.setImageResource(R.drawable.ukrainian_icon)
            "Uruguayan" -> mealCountryFlage.setImageResource(R.drawable.uruguayan_icon)
            "Vietnamese" -> mealCountryFlage.setImageResource(R.drawable.vietnamese_icon)
        }
    }


    private fun animateFavoriteButton(button: View) {
        button.animate()
            .scaleX(1.08f)
            .scaleY(1.08f)
            .setDuration(120)
            .withEndAction {
                button.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(120)
                    .start()
            }
            .start()
    }


    private fun fillMealIngredientsList() {

        if (currentMeal.strIngredient1 != null && currentMeal.strMeasure1 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient1)
            currentMealMeasuresList.add(currentMeal.strMeasure1)
        }
        if (currentMeal.strIngredient2 != null && currentMeal.strMeasure2 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient2)
            currentMealMeasuresList.add(currentMeal.strMeasure2)
        }
        if (currentMeal.strIngredient3 != null && currentMeal.strMeasure3 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient3)
            currentMealMeasuresList.add(currentMeal.strMeasure3)
        }
        if (currentMeal.strIngredient4 != null && currentMeal.strMeasure4 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient4)
            currentMealMeasuresList.add(currentMeal.strMeasure4)
        }
        if (currentMeal.strIngredient5 != null && currentMeal.strMeasure5 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient5)
            currentMealMeasuresList.add(currentMeal.strMeasure5)
        }
        if (currentMeal.strIngredient6 != null && currentMeal.strMeasure6 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient6)
            currentMealMeasuresList.add(currentMeal.strMeasure6)
        }
        if (currentMeal.strIngredient7 != null && currentMeal.strMeasure7 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient7)
            currentMealMeasuresList.add(currentMeal.strMeasure7)
        }
        if (currentMeal.strIngredient8 != null && currentMeal.strMeasure8 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient8)
            currentMealMeasuresList.add(currentMeal.strMeasure8)
        }
        if (currentMeal.strIngredient9 != null && currentMeal.strMeasure9 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient9)
            currentMealMeasuresList.add(currentMeal.strMeasure9)
        }
        if (currentMeal.strIngredient10 != null && currentMeal.strMeasure10 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient10)
            currentMealMeasuresList.add(currentMeal.strMeasure10)
        }
        if (currentMeal.strIngredient11 != null && currentMeal.strMeasure11 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient11)
            currentMealMeasuresList.add(currentMeal.strMeasure11)
        }
        if (currentMeal.strIngredient12 != null && currentMeal.strMeasure12 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient12)
            currentMealMeasuresList.add(currentMeal.strMeasure12)
        }
        if (currentMeal.strIngredient13 != null && currentMeal.strMeasure13 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient13)
            currentMealMeasuresList.add(currentMeal.strMeasure13)
        }
        if (currentMeal.strIngredient14 != null && currentMeal.strMeasure14 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient14)
            currentMealMeasuresList.add(currentMeal.strMeasure14)
        }
        if (currentMeal.strIngredient15 != null && currentMeal.strMeasure15 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient15)
            currentMealMeasuresList.add(currentMeal.strMeasure15)
        }
        if (currentMeal.strIngredient16 != null && currentMeal.strMeasure16 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient16)
            currentMealMeasuresList.add(currentMeal.strMeasure16)
        }
        if (currentMeal.strIngredient17 != null && currentMeal.strMeasure17 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient17)
            currentMealMeasuresList.add(currentMeal.strMeasure17)
        }
        if (currentMeal.strIngredient18 != null && currentMeal.strMeasure18 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient18)
            currentMealMeasuresList.add(currentMeal.strMeasure18)
        }
        if (currentMeal.strIngredient19 != null && currentMeal.strMeasure19 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient19)
            currentMealMeasuresList.add(currentMeal.strMeasure19)
        }
        if (currentMeal.strIngredient20 != null && currentMeal.strMeasure20 != null) {
            currentMealIngredientsList.add(currentMeal.strIngredient20)
            currentMealMeasuresList.add(currentMeal.strMeasure20)
        }


    }


    private fun setIngredientsRecyclerView() {

        fillMealIngredientsList()

        val listOfIngredients = mutableListOf<IngrediantItem>()

        for (i in currentMealIngredientsList.indices) {

            allIngredientsList.forEach {
                if (currentMealIngredientsList[i] == it.strIngredient) {
                    listOfIngredients.add(
                        IngrediantItem(
                            it.strThumb,
                            currentMealIngredientsList[i],
                            currentMealMeasuresList[i]
                        )
                    )
                }
            }


        }


        ingrediants_recycler_view.adapter = Ingrediants_detals_screen_adapter(listOfIngredients)

        numberOfIngredientsItems.text = "${listOfIngredients.size} items"


    }

    fun splitInstructionsIntoList(instructions: String): List<String> {
        return instructions
            .split(".")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }


    fun setStepsProcess() {

        instructionsList = splitInstructionsIntoList(currentMeal.strInstructions)
        numberOfSteps.text = "step 1 of ${instructionsList.size}"
        stepsProgressBar.max = instructionsList.size
        InstructionText.text = instructionsList[0]


    }


}
