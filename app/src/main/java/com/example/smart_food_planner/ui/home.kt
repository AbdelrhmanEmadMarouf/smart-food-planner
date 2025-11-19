package com.example.smart_food_planner.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import coil.compose.AsyncImage
import com.example.smart_food_planner.R
import com.example.smart_food_planner.database.dataclasses.FavoriteMeals
import com.example.smart_food_planner.model.dataClasses.Detailed_Meal
import com.example.smart_food_planner.viewmodel.Favorite_Meals_Viewmodel
import com.example.smart_food_planner.viewmodel.MealsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

var favoriteButtonIsSelected = false

// Data class for category with meals
data class CategoryWithMeals(
    val categoryName: String,
    val meals: List<Detailed_Meal>
)

class home : Fragment() {

    private var randomMeal = Detailed_Meal(
        dateModified = "2025-11-13 10:45:01",
        idMeal = "53133",
        strArea = "Argentinian",
        strCategory = "Beef",
        strCreativeCommonsConfirmed = "",
        strImageSource = "",
        strIngredient1 = "Mixed Beef Cuts",
        strIngredient10 = "",
        strIngredient11 = "",
        strIngredient12 = "",
        strIngredient13 = "",
        strIngredient14 = "",
        strIngredient15 = "",
        strIngredient16 = "",
        strIngredient17 = "",
        strIngredient18 = "",
        strIngredient19 = "",
        strIngredient2 = "Chorizo",
        strIngredient20 = "",
        strIngredient3 = "Morcilla",
        strIngredient4 = "Salt",
        strIngredient5 = "",
        strIngredient6 = "",
        strIngredient7 = "",
        strIngredient8 = "",
        strIngredient9 = "",
        strInstructions = "Prepare the Fire: Start a wood fire in your grill and let it burn down to coals.\r\nSeason the Meat: Generously salt the beef cuts.\r\nGrill the Meat: Place the beef on the grill, starting with the thickest cuts farthest from the coals. Add chorizo and morcilla after the beef has been cooking for a while.\r\nCook to Perfection: Cook the meat, turning occasionally, until it reaches your desired doneness. Typically, ribs may take up to 2 hours; thinner cuts will cook faster.\r\nRest and Serve: Let the meat rest for about 10 minutes before slicing. Serve with chimichurri sauce and grilled vegetables.\r\nPro Tips:\r\n\r\nUse a mix of wood and charcoal for a consistent heat source. Wood adds flavor, while charcoal maintains temperature.\r\nSeason the meat just before grilling to ensure it retains its moisture and flavor.\r\nServing Suggestions:\r\n\r\nServe with a side of chimichurri sauce, a fresh tomato salad, and crusty bread. Pair with a robust Malbec wine to complement the rich flavors of the meat.",
        strMeal = "Asado",
        strMealAlternate = "",
        strMealThumb = "https://www.themealdb.com/images/media/meals/kgfh3q1763075438.jpg",
        strMeasure1 = "2kg",
        strMeasure10 = " ",
        strMeasure11 = " ",
        strMeasure12 = " ",
        strMeasure13 = " ",
        strMeasure14 = " ",
        strMeasure15 = " ",
        strMeasure16 = " ",
        strMeasure17 = " ",
        strMeasure18 = " ",
        strMeasure19 = " ",
        strMeasure2 = "4",
        strMeasure20 = " ",
        strMeasure3 = "2",
        strMeasure4 = "To taste",
        strMeasure5 = " ",
        strMeasure6 = " ",
        strMeasure7 = " ",
        strMeasure8 = " ",
        strMeasure9 = " ",
        strSource = "https://www.munchery.com/blog/the-ten-iconic-dishes-of-argentina-and-how-to-cook-them-at-home/",
        strTags = "",
        strYoutube = "https://www.youtube.com/watch?v=q5f6Z6i1hQ8"
    )

    private val favoriteMealsViewModel: Favorite_Meals_Viewmodel by viewModels()
    private val mealViewModel: MealsViewModel by viewModels()

    private var favoriteMealIds = listOf<FavoriteMeals>()

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val composeView = view.findViewById<ComposeView>(R.id.home_compose_view)

        // تحضير الـ favorite button
        prepareFavoriteButton()

        composeView.setContent {
            var forYouMealsList by remember { mutableStateOf<List<Detailed_Meal>>(emptyList()) }
            var randomMealState by remember { mutableStateOf<Detailed_Meal?>(null) }
            var categoriesWithMeals by remember { mutableStateOf<List<CategoryWithMeals>>(emptyList()) }
            var isLoading by remember { mutableStateOf(true) }

            // observe meals (categories) from ViewModel
            val mealsState by mealViewModel.meals.observeAsState(emptyList())

            LaunchedEffect(Unit) {
                isLoading = true

                // جلب 16 وجبة عشوائية للـ For You section
                forYouMealsList = mealViewModel.fetchMultipleRandomMeals(16)
                randomMealState = forYouMealsList.lastOrNull() ?: randomMeal

                // جلب كل الـ Categories من الـ API
                mealViewModel.getMeals()
            }

            // عند استلام الـ categories، نجلب 4 وجبات لكل category
            LaunchedEffect(mealsState) {
                if (mealsState.isNotEmpty() && categoriesWithMeals.isEmpty()) {

                    // جلب 4 وجبات لكل category بشكل متوازي
                    val categoriesData = coroutineScope {
                        mealsState.map { category ->
                            async {
                                val categoryName = category.strMealTitle
                                val meals = mealViewModel.fetchMealsByCategory(categoryName, 4)
                                CategoryWithMeals(categoryName, meals)
                            }
                        }.awaitAll()
                    }


                    categoriesWithMeals = categoriesData.filter { it.meals.isNotEmpty() }

                    isLoading = false
                    Log.d("TEST", "Loaded ${categoriesWithMeals.size} categories with meals")
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                // عرض الشاشة بعد انتهاء التحميل
                HomeScreen(
                    randomMeal = randomMealState ?: randomMeal,
                    forYouMeals = forYouMealsList,
                    categoriesWithMeals = categoriesWithMeals
                )
            }
        }
    }


    @Composable
    fun HomeScreen(
        randomMeal: Detailed_Meal,
        forYouMeals: List<Detailed_Meal>,
        categoriesWithMeals: List<CategoryWithMeals>
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            item {
                // random meal
                RandomMeal(randomMeal)
            }

            item {
                //calenderButton
                CalenderButton()
            }

            // For You Section
            item {
                if (forYouMeals.isNotEmpty()) {
                    ForYouSection(forYouMeals)
                }
            }

            item {
                Spacer(modifier = Modifier.size(20.dp))
                Text("More Meals🍴", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.size(10.dp))
            }

            // عرض الـ categories مع الوجبات
            if (categoriesWithMeals.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else {
                items(categoriesWithMeals) { categoryData ->
                    CategorySection(categoryData)
                }
            }
        }
    }


    @Composable
    fun CategorySection(categoryWithMeals: CategoryWithMeals) {

        Column(modifier = Modifier.padding(vertical = 12.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(text = categoryWithMeals.categoryName, fontSize = 30.sp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        contentDescription = "more",
                        imageVector = Icons.Default.KeyboardArrowRight,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(35.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    goToFilteredMeals(categoryWithMeals.categoryName)
                                }
                            ),
                    )
                    Spacer(Modifier.size(20.dp))
                }
            }
            LazyRow {
                items(categoryWithMeals.meals) { meal ->
                    MealCard(meal = meal)
                }
            }
        }

    }


    @Composable
    fun MealCardForRandom(meal: Detailed_Meal) {
        Card(

            onClick = { goToDetailsMeal(meal.idMeal) },
            modifier = Modifier
                .size(300.dp)
                .padding(9.6.dp)
                .padding(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),


            ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        shape = RectangleShape
                    )
            ) {
                if (meal == null) {

                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {

                    AsyncImage(
                        model = meal.strMealThumb,
                        contentDescription = meal.strMeal,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .padding(horizontal = 19.2.dp, vertical = 9.6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {


                        Text(
                            text = meal.strMeal,
                            color = Color.White,
                            fontSize = 15.6.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis

                        )

                    }
                }
            }
        }
    }


    @Composable
    fun MealCard(meal: Detailed_Meal) {
        Card(

            onClick = { goToDetailsMeal(meal.idMeal) },
            modifier = Modifier
                .size(200.dp)
                .padding(6.4.dp)
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(8.dp),


            ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        shape = RectangleShape
                    )
            ) {
                if (meal == null) {

                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {

                    AsyncImage(
                        model = meal.strMealThumb,
                        contentDescription = meal.strMeal,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {


                        Text(
                            text = meal.strMeal,
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis

                        )

                    }
                }
            }
        }
    }

    @Composable
    fun RandomMeal(meal: Detailed_Meal) {
        Text(text = "Today's Meal! 🥗", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))
        val context = LocalContext.current
        val interactionSource = remember { MutableInteractionSource() }

        // تحديد حالة الـ favorite من الـ ViewModel - استخدام observeAsState بدلاً من observeForever
        val favoriteMealsList by favoriteMealsViewModel.favorite_meals.observeAsState(emptyList())

        // التحقق من حالة الـ favorite بناءً على الـ list
        val isFavorite = favoriteMealsList.any { it.mealId == meal.idMeal }

        val iconColor = if (isFavorite) {
            Color(0xFFFF0000)
        } else
            Color(0xFFdddedc)


        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.size(370.dp),
                onClick = { goToDetailsMeal(meal.idMeal) },
                elevation = CardDefaults.cardElevation(10.dp)
            )
            {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            shape = RectangleShape
                        )
                ) {

                    if (meal == null) {

                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    } else {

                        AsyncImage(
                            model = meal.strMealThumb,
                            contentDescription = meal.strMeal,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .background(Color.Black.copy(alpha = 0.5f))
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {


                            Text(
                                text = meal.strMeal,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )

                        }

                    }
                    if (meal != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(x = (-10).dp, y = (10).dp),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.End
                        ) {


                            Icon(

                                contentDescription = "icon_favourite",
                                imageVector = Icons.Default.Favorite,
                                tint = iconColor,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = null,
                                        onClick = {
                                            if (isFavorite) {
                                                // حذف من المفضلة
                                                favoriteMealsViewModel.deleteMeal(
                                                    FavoriteMeals(
                                                        meal.idMeal,
                                                        meal.strMeal,
                                                        meal.strMealThumb
                                                    )
                                                )
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Meal removed from favorite!",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                            } else {
                                                // إضافة للمفضلة
                                                favoriteMealsViewModel.addFavoriteMeal(
                                                    FavoriteMeals(
                                                        meal.idMeal,
                                                        meal.strMeal,
                                                        meal.strMealThumb
                                                    )
                                                )
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Meal added to favorite!",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                            }

                                        }
                                    )
                            )


                        }
                    }
                }
            }
        }
    }


    @Composable
    fun CalenderButton() {
        Spacer(Modifier.height(50.dp))
        Text(text = "Set special Days! 🗓️", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))
        Column(modifier = Modifier, horizontalAlignment = Alignment.Start) {

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    onClick = { goToCalenderFragment() },
                    modifier = Modifier.padding(0.dp),
                    elevation = CardDefaults.cardElevation(20.dp)
                )
                {
                    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text(
                                " \nTurn Any Day into \n a Step-by-Step \n Recipe \n",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                " Cook Anyday You Want!\n",
                                fontSize = 20.sp,
                                fontStyle = FontStyle.Italic
                            )
                            Row {
                                Spacer(Modifier.size(10.dp))
                                Text(
                                    "Log Now\n",
                                    fontSize = 20.sp,
                                    textDecoration = TextDecoration.Underline,
                                    modifier = Modifier.clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                        onClick = { goToCalenderFragment() }
                                    )
                                )
                            }

                        }
                        //Spacer(modifier = Modifier.width(150.dp))
                        val imageCalender = painterResource(id = R.drawable.untitled_design)
                        Image(
                            painter = imageCalender,
                            contentDescription = "imageCalender",
                        )
                    }
                }
            }

        }
        Spacer(Modifier.height(50.dp))
    }


    @Composable
    fun ForYouSection(meals: List<Detailed_Meal>) {
        Column(modifier = Modifier.padding(vertical = 12.dp)) {

            Text("For You! ✨", fontSize = 30.sp, fontWeight = FontWeight.Bold)

            LazyRow {
                items(meals) { Rmeal ->
                    MealCardForRandom(Rmeal)
                }
            }
        }


    }


    fun goToCalenderFragment() {

        (activity as? MainActivity)?.let { mainActivity ->

            mainActivity.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.selectedItemId =
                R.id.nav_calender
        }
    }

    fun goToDetailsMeal(mealId: String) {
        val fragment = Details_Meal_Fragment()

        val args = bundleOf(
            "Meal Id" to mealId
        )
        fragment.arguments = args

        val activity = context as? AppCompatActivity
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    fun goToFilteredMeals(categoryName: String) {
        val fragment = Filtered_Meals()

        val args = bundleOf(
            "Fragment title" to categoryName,
            "key" to "c"
        )
        fragment.arguments = args

        val activity = context as? AppCompatActivity
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    fun prepareFavoriteButton() {
        // جلب البيانات من الـ database
        favoriteMealsViewModel.getFavoriteMeals()

        favoriteMealsViewModel.favorite_meals.observe(viewLifecycleOwner) { Ids ->
            favoriteMealIds = Ids

            favoriteMealIds.forEach {
                if (it.mealId == randomMeal.idMeal) {
                    favoriteButtonIsSelected = true
                }
            }
        }
    }

}