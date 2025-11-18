package com.example.homeui_foodplannerproject.view
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.homeui_foodplannerproject.R
import com.example.homeui_foodplannerproject.model.CategoryWithMeals
import com.example.homeui_foodplannerproject.model.Meal
import com.example.homeui_foodplannerproject.viewmodel.MealViewModel

@Composable
fun HomeScreen(mealsViewModel: MealViewModel = viewModel()){
    val categoriesWithMeals by mealsViewModel.CategoryWithMeals.collectAsState()
    val randomMeal by mealsViewModel.randomMeal.collectAsState()
    val randomCategory by mealsViewModel.randomMealCategory.collectAsState()

    LaunchedEffect(Unit) {
        mealsViewModel.FetchAllData()
        mealsViewModel.randomMeal
    }


    LazyColumn (modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 10.dp)){
        item {


                // random meal
                RandomMeal(randomMeal)
            }


        item {
            //calenderButton

            CalenderButton()
        }
        item {
            ForYouSection(randomCategory)
        }

        item {
            Spacer(modifier = Modifier.size(20.dp))
            Text("More Meals🍴", fontSize = 30.sp,fontWeight = FontWeight.Bold,)
            Spacer(modifier = Modifier.size(10.dp))

        }

        if (categoriesWithMeals.isEmpty() && randomMeal != null ){
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
        }else{

            items(categoriesWithMeals){
                CategoryData->

                CategorySection(CategoryData)
            }
        }
    }
    }



@Composable
@Preview(showSystemUi = true)
fun CalenderButton(){
    Spacer(Modifier.height(50.dp))
    Text(text = "Set special Days! 🗓️", fontSize = 30.sp,fontWeight = FontWeight.Bold,)
    Spacer(Modifier.height(20.dp))
    Column(modifier = Modifier, horizontalAlignment = Alignment.Start) {

        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
            ) {
            Card(
                onClick = {/*TODO*/ }, modifier = Modifier.padding(0.dp),
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
                                textDecoration = TextDecoration.Underline
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
fun CategorySection(categoryWithMeals: CategoryWithMeals){

    Column(modifier = Modifier.padding(vertical = 12.dp)){

        Row (verticalAlignment = Alignment.CenterVertically) {

            Text(text = categoryWithMeals.categoryName, fontSize = 30.sp)
            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Icon(
                    contentDescription = "more",
                    imageVector = Icons.Default.KeyboardArrowRight,
                    tint = Color.Black,
                    modifier = Modifier.size(35.dp).clickable(
                        interactionSource = remember { MutableInteractionSource() }, // its obvuose "the source of interactions"
                        indication = null,  // the interactoin of animation when clicked i guess
                        onClick = { Log.i("TAG", "HomeScreen: ") }

                    ),
                )
                Spacer(Modifier.size(20.dp))
            }
        }
        LazyRow{
            items(categoryWithMeals.meals) { meal ->
                MealCard(meal = meal)
            }
        }
    }

    }


@Composable
fun ForYouSection(meals: List<Meal>){
    Column(modifier = Modifier.padding(vertical = 12.dp)){

            Text("For You! ✨", fontSize = 30.sp,fontWeight = FontWeight.Bold,)

        LazyRow{
            items(meals) { Rmeal ->
                MealCardForRandom(Rmeal)
            }
        }
    }


}



@Composable
fun MealCard(meal : Meal?) {
    Card(

        onClick = {/*TODO*/ },
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
        ){ if (meal == null){

            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
         }
            else{

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
fun MealCardForRandom(meal : Meal?) {
    Card(

        onClick = {/*TODO*/ },
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
        ){ if (meal == null){

            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        else{

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
//@Composable
//@Preview(showSystemUi = true)
//fun test () {
//    Text(
//        "more",
//        fontSize = 18.sp,
//        textDecoration = TextDecoration.Underline,
//        modifier = Modifier.clickable(
//            interactionSource = remember { MutableInteractionSource() }, // its obvuose "the source of interactions"
//            indication = null,  // the interactoin of animation when clicked i guess
//            onClick = { Log.i("TAG", "HomeScreen: ") }
//
//        )
//    )
//}

@Composable
fun RandomMeal(meal: Meal?, /*isFavorite : Boolean, onToggleFavorite : (String)-> Unit*/){
    Text(text = "Today's Meal! 🥗", fontSize = 40.sp,fontWeight = FontWeight.Bold,)
    Spacer(Modifier.height(20.dp))
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }


    var isFavorite by remember { mutableStateOf(false) }


    val iconColor = if(isFavorite){
        Color(0xFFFF0000)
    }else
        Color(0xFFdddedc)


    Box(modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
    Card(
        modifier = Modifier.size(370.dp),
        onClick = {/*TODO*/ },
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

            if (meal == null){

                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }else{

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
            if (meal != null){
            Row(
                modifier = Modifier.fillMaxWidth().offset(x = (-10).dp, y = (10).dp),
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
                         //       onToggleFavorite(meal.idMeal)
                                isFavorite = !isFavorite
                                if ( isFavorite )
                                {
                                    Toast.makeText(context, "Meal added to favorite!", Toast.LENGTH_SHORT).show()
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




