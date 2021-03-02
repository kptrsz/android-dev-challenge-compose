/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.data.Animal
import com.example.androiddevchallenge.data.SizeEnum
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

object Destinations

// Start building your app here!
@Composable
fun MyApp() {
//    ListScreen()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ListScreen(navController)
        }
        composable(
            "details/{name}/{imageId}",
            arguments = listOf(navArgument("name") { type = NavType.StringType }, navArgument("imageId") { type = NavType.IntType })
        ) {
            DetailsScreen(it.arguments?.getString("name") ?: "Bubu", it.arguments?.getInt("imageId") ?: R.drawable.a1, navController)
        }
    }
}

@Composable
fun ListScreen(navController: NavHostController) = Scaffold(
    topBar = {
        TopAppBar(title = { Text(text = "Animals") })
    }
) {
    Surface(color = MaterialTheme.colors.background) {
        LazyColumn {
            items(20) {
                AnimalCard(animal = Animal.generate(), navController)
            }
        }
    }
}

@Composable
fun DetailsScreen(name: String, imageId: Int, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = name) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Image(modifier = Modifier.fillMaxWidth(), painter = painterResource(id = imageId), contentDescription = null)
    }
}

@Composable
fun AnimalCard(animal: Animal, navController: NavController) {
    Row(
        modifier = Modifier
            .height(100.dp)
            .padding(4.dp)
            .fillMaxWidth(1f)
            .background(Color(animal.color))
            .clickable {
                navController.navigate(
                    "details/${animal.name}/${animal.image}",

                )
            }
    ) {
        Column(
            Modifier
                .weight(1f)
                .width(100.dp)
        ) {
            Text(
                text = animal.name,
                Modifier
                    .padding(all = 16.dp)
            )
            Row(Modifier.padding(4.dp)) {
                Image(
                    colorFilter = ColorFilter.tint(if (animal.size == SizeEnum.SMALL) Color.Black else Color.Gray),
                    modifier = Modifier
                        .scale(0.5f),
                    painter = painterResource(id = R.drawable.ic_dog_side),
                    contentDescription = null
                )
                Image(
                    colorFilter = ColorFilter.tint(if (animal.size == SizeEnum.MEDIUM) Color.Black else Color.Gray),
                    modifier = Modifier.scale(0.7f),
                    painter = painterResource(id = R.drawable.ic_dog_side),
                    contentDescription = null
                )
                Image(
                    colorFilter = ColorFilter.tint(if (animal.size == SizeEnum.BIG) Color.Black else Color.Gray),
                    modifier = Modifier.scale(1f),
                    painter = painterResource(id = R.drawable.ic_dog_side),
                    contentDescription = null
                )
            }
        }
        Image(painter = painterResource(id = animal.image), contentDescription = null)
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
