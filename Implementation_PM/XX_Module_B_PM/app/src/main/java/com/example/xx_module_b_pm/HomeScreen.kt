package com.example.xx_module_b_pm

import android.hardware.lights.Light
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.xx_module_b_pm.ui.theme.Blue
import com.example.xx_module_b_pm.ui.theme.LightBlue
import com.example.xx_module_b_pm.ui.theme.Orange
import com.example.xx_module_b_pm.ui.theme.Purple
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController) {

    val tabList = remember {
        listOf("Food", "Drinks", "Desserts")
    }
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val jsonExtractor = remember {JsonExtractor(context = context)}
    val data = remember {jsonExtractor.extractFoodItems()}
    val database = remember {AppDatabase.getDb(context)}

    val food = remember {data.filter { it.name.contains("burger") || it.name.contains("Sandwich") }}
    val drinks = remember {data.filter { listOf("Tea", "Coffee", "Coke", "Green tea", "Lipton tea", "Orange juice").contains(it.name)}}
    val desserts = remember {data.filter { listOf("Softee ice cream", "Ice cream").contains(it.name) }}
    val listController = listOf(food, drinks, desserts)
Column() {
    ScrollableTabRow(edgePadding = 0.dp ,backgroundColor = Purple, contentColor = Color.White,selectedTabIndex = pagerState.currentPage) {
        tabList.forEach { 
            Tab(selected = tabList[pagerState.currentPage] == it, onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(tabList.indexOf(it))
                }
        }, text = {
                Text(text = it.uppercase()  )
            })
        }
    }
    
    HorizontalPager(pageCount = tabList.size, state = pagerState) {a->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)) {

                    LazyVerticalGrid(columns = GridCells.Fixed(3), horizontalArrangement = Arrangement.spacedBy(10.dp    ), verticalArrangement = Arrangement.spacedBy(10.dp ), modifier = Modifier.fillMaxWidth(0.8f) ) {
                        items(listController[a].size) {
                            val item =listController[a][it]
                            var dialog by remember {
                                mutableStateOf(false)
                            }
                            if(dialog ) {
                                Dialog(onDismissRequest = { dialog = false   }) {
                                    Column(
                                        Modifier
                                            .padding(10.dp)
                                            .width(600.dp)
                                            .background(
                                                Color.White,
                                                shape = RoundedCornerShape(10.dp)
                                            ), horizontalAlignment = Alignment.CenterHorizontally
                                           ) {
                                        IconButton(onClick = { dialog = false   }, modifier =  Modifier.align(Alignment.Start)) {
                                            Icon(Icons.Default.ArrowBack, contentDescription ="back", tint = Color.Black )
                                        }

                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Box(modifier = Modifier
                                                    .height(200.dp)
                                                    .width(300.dp)
                                                    .background(Color(0xFF777777)), contentAlignment = Alignment.Center, ) {
                                                    Image(painter =painterResource(context.resources.getIdentifier(item.imageName, "drawable", context.packageName)), contentDescription = "California ", modifier = Modifier
                                                        .height(200.dp)
                                                        , contentScale = ContentScale.Crop)
                                                }

                                                Column(modifier = Modifier.padding(10.dp)) {
                                                    Row(modifier = Modifier.width(300.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                                        Text(text = item.name,  fontSize = 20.sp)
                                                                Text(text = "$${item.price  }", color = Orange)
                                                    }

                                                    Text(text = "${item.description}")

                                                    Spacer(modifier = Modifier.height(20.dp))
                                                    Button(modifier = Modifier.align(Alignment.End),onClick = { scope.launch {
                                                        withContext(Dispatchers.IO   ) {
                                                            database.cartItemDao().insert(CartItem(imageName = item.imageName, title = item.name, description = item.description, price = item.price))
                                                        }


                                                    }
                                                        Toast.makeText(context, "Item added!", Toast.LENGTH_LONG).show()}, colors = ButtonDefaults.buttonColors(backgroundColor = LightBlue)) {
                                                        Text(text = "Order Now", color = Color.White, modifier = Modifier.padding(3.dp  ))
                                                    }
                                                }
                                            }


                                    }
                                }
                            }

                            Card(onClick = {dialog = true}) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                   Box(modifier = Modifier
                                       .height(200.dp)
                                       .width(300.dp)
                                       .background(Color(0xFF777777)), contentAlignment = Alignment.Center, ) {
                                        Image(painter = painterResource(context.resources.getIdentifier(item.imageName, "drawable", context.packageName)), contentDescription = "California ", modifier = Modifier
                                            .height(200.dp)
                                            , contentScale = ContentScale.Crop)
                                    }

                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(text = item.name,  fontSize = 20.sp   )
                                        Text(text = item.description)
                                        Text(text = "$${item.price}", color = Orange)
                                        Spacer(modifier = Modifier.height(20.dp))
                                        Button(modifier = Modifier.align(Alignment.End),onClick = {
                                            scope.launch {
                                                withContext(Dispatchers.IO) {
                                                    database.cartItemDao().insert(CartItem(imageName = item.imageName, title = item.name, description = item.description, price = item.price))
                                                }
                }

                                            Toast.makeText(context, "Item added!", Toast.LENGTH_LONG).show()
                                        }, colors = ButtonDefaults.buttonColors(backgroundColor = LightBlue)) {
                                            Text(text = "Order Now", color = Color.White, modifier = Modifier.padding(3.dp  ))
                                        }
                                    }
                                }
                            }
                        }
                       
                    }
                
                  


            }
        }

    }
}

