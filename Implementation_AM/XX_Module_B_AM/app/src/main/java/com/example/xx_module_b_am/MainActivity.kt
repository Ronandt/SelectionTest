package com.example.xx_module_b_am

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.xx_module_b_am.ui.theme.XX_Module_B_AMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XX_Module_B_AMTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login" ) {
                    composable("login") {
                        LoginScreen(navController)
                    }
                    composable("notes") {

                        NotesScreen(navController)
                    }
                    composable("addNote") {
                        AddNoteScreen(navController)
                    }
                    composable("favourites") {
                        FavouritesScreen(navController)
                    }
                    composable("edit/{id}") {
                        EditScreen(navController, id = it.arguments?.getString("id"))
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
    XX_Module_B_AMTheme {
        Greeting("Android")
    }
}