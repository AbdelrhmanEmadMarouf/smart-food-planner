package com.example.homeui_foodplannerproject

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.alpha

@Preview(showSystemUi = true)
@Composable
fun HomeScreen(){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(18.dp)
        ) {
        Spacer(Modifier.height(20.dp))

        Text(text = "Today's Meal! 🥗", fontSize = 40.sp)

        Spacer(Modifier.height(20.dp))

        Card(
            onClick = {/*TODO*/}, modifier = Modifier.padding(0.dp),
            elevation = CardDefaults.cardElevation(30.dp)
        )
        {
            val ImagePainter = painterResource(id =  R.drawable.images)

            Spacer(Modifier.height(20.dp))
            Image(
                painter = ImagePainter,
                contentDescription = "imageTest",
                modifier = Modifier.size(400.dp)
            )
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {



               Text("Meal Name" , fontSize = 20.sp)
                Spacer(Modifier.width(180.dp))
                Button(onClick = {/**/}, colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color.Gray )) {
                    Icon(Icons.Default.Favorite, contentDescription = "icon_fevorate")
                }
            }
        }
        Spacer(Modifier.height(50.dp))
        Text(text = "Set special Days! 🗓️", fontSize = 30.sp)
        Spacer(Modifier.height(20.dp))


        Column ( modifier = Modifier, horizontalAlignment = Alignment.Start){

            Card(

                onClick = {/*TODO*/}, modifier = Modifier.padding(0.dp),
                elevation = CardDefaults.cardElevation(20.dp)
            )
            {
                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(" \nTurn Any Day into \n a Step-by-Step \n Recipe \n", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                        Text(" Cook Anyday You Want!\n",fontSize = 20.sp, fontStyle = FontStyle.Italic )
                        Text(" Log Now\n", fontSize = 20.sp, textDecoration = TextDecoration.Underline)
                    }
                    //Spacer(modifier = Modifier.width(150.dp))
                    val imageCalender = painterResource(id= R.drawable.untitled_design)
                    Image(
                        painter = imageCalender,
                        contentDescription = "imageCalender",
                    )
                }

            }

            Spacer(Modifier.height(50.dp))


        }






       repeat (5) {
        Spacer(Modifier.height(20.dp))
           Row (verticalAlignment = Alignment.CenterVertically) {

               Text(text = "Type of food ", fontSize = 30.sp)
               Spacer(Modifier.width(130.dp))
               Text("more",
                   fontSize = 18.sp,
                   textDecoration = TextDecoration.Underline,
                   modifier = Modifier.clickable(
                       interactionSource = remember { MutableInteractionSource() }, // its obvuose "the source of interactions"
                       indication = null,  // the interactoin of animation when clicked i guess
                       onClick = { Log.i("TAG", "HomeScreen: ")}

                   )
               )
           }
               Spacer(Modifier.height(20.dp))
           val horizontalScroll = rememberScrollState()

           Row(modifier = Modifier
               .fillMaxWidth()
               .horizontalScroll(horizontalScroll)) {


               repeat(10) {
                   FoodListHorizontal()

               }
           }
       }


    }

    }

@Composable
fun FoodListHorizontal() {
    Card(
        onClick = {/*TODO*/ }, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(10.dp),


    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val ImagePainter = painterResource(id =  R.drawable.image_processing20250210)

            Spacer(Modifier.height(20.dp))
            Image(
                painter = ImagePainter,
                contentDescription = "imageTest2",
                modifier = Modifier.size(180.dp)

            )
            Text("meal name", fontSize = 20.sp)

        }
    }
}




