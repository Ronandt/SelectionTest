package com.example.xx_module_b_pm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.xx_module_b_pm.ui.theme.Blue
import com.example.xx_module_b_pm.ui.theme.XX_Module_B_PMTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            val navController = rememberNavController()
            val scope = rememberCoroutineScope()
            val destination = navController.currentBackStackEntryAsState().value?.destination?.route
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            ModalNavigationDrawer(drawerState = drawerState,drawerContent = {
                ModalDrawerSheet(modifier = Modifier.fillMaxHeight(),) {
                    Column(modifier = Modifier
                        .width(400.dp)
                        .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painter = painterResource(id = R.drawable.icon), contentDescription = "Icon", modifier = Modifier
                            .size(150.dp)
                            .clip(
                                CircleShape
                            ))
                        Spacer(modifier = Modifier.height(30.dp))
                        Text(text = "Settings", modifier = Modifier.align(Alignment.Start), fontSize = 20.sp , fontWeight = FontWeight.Bold)
                    }
                }
            }) {
                Scaffold(topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Blue,
                            titleContentColor = Color.White
                        ),
                        actions = {
                            if(destination != "cart") {
                                IconButton(
                                    onClick = { navController.navigate("cart") }) {
                                    Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color.White)
                                }
                            }

                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {

                                        drawerState.open()
                                    }
                                },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_menu_24),
                                    contentDescription = "Menu",
                                    tint = Color.White
                                )
                            }

                        },
                        title = {
                            Text(
                                text = "Eat with me",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        },
                    )

                }) {
                    Column(modifier = Modifier.padding(it)) {
                        NavHost(navController = navController, startDestination ="home" ) {
                            composable("home") {
                                HomeScreen(navController)
                            }
                            composable("cart") {
                                CartScreen(navController)
                            }
                        }
                    }
                }

            }
            }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    XX_Module_B_PMTheme {
        Greeting("Android")
    }
}