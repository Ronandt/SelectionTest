package com.example.xx_module_b_pm

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.xx_module_b_pm.ui.theme.LightBlue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CartScreen(navController: NavController) {
    val context = LocalContext.current
    val database = remember {AppDatabase.getDb(context)}
    var data = database.cartItemDao().selectAll().collectAsState(initial = listOf())
    var dialog by remember { mutableStateOf(false   )}
    if(dialog) {
        Dialog(onDismissRequest = { dialog = false }) {
         Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
             .size(250.dp)
             .background(Color.White, shape = RoundedCornerShape(10.dp))
             .padding(12.dp)) {
             Spacer(modifier = Modifier.height(15.dp))
             Text(text = "Thank you", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.align(Alignment.Start))
             Spacer(modifier = Modifier.height(60.dp))
             Text(text = "Your order is on its way", color = Color.Gray, fontSize = 17.sp   )
             Spacer(modifier = Modifier.height(30.dp))
             TextButton(onClick = {

                 dialog = false }, colors = ButtonDefaults.buttonColors(contentColor = LightBlue, backgroundColor = Color.Transparent)) {
                 Text(text = "OK")
             }
         }

        }
    }

    val scope = rememberCoroutineScope()
Column(modifier = Modifier
    .fillMaxWidth()
    .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {

    Text(text = "Cart", fontSize = 42.sp)
    Spacer(modifier = Modifier.height(10.dp))
    LazyColumn(modifier = Modifier
        .size(500.dp)
        .height(500.dp)
        .border(width = 1.dp, color = Color.Black), verticalArrangement = Arrangement.spacedBy(1.dp  ), horizontalAlignment = Alignment.CenterHorizontally) {
        items(data.value.size) {
            val item =  data.value[it]
            Row(
                Modifier
                    .fillMaxWidth(0.95f)
                    .border(width = 1.dp, color = Color.Black)
                    .padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(20.dp  , alignment = Alignment.CenterHorizontally  ), ) {

                Image(painter =painterResource(context.resources.getIdentifier(item.imageName, "drawable", context.packageName)), contentScale = ContentScale.Crop,modifier = Modifier.size(110.dp ), contentDescription = "burger")
                Text(text = item.title, fontSize = 18.sp    )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(text = "$${item.price}", fontSize = 18.sp   )
                    Button(onClick = {
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                database.cartItemDao().delete(item)
                            }

                        }
             }, colors = ButtonDefaults.buttonColors(backgroundColor = LightBlue)) {
                        Text(text = "Remove", color = Color.White)
                    }
                }

            }
        }
    }
Card(modifier = Modifier.width(500.dp   )) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.width(350.dp    ), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Total", fontSize = 24.sp)
            Text(text = "$${data.value.sumOf { it.price.toDouble() }}", fontSize = 24.sp  )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(modifier = Modifier.width(350.dp    ), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { dialog = true

                scope.launch {
                    withContext(Dispatchers.IO) {
                        database.cartItemDao().massDelete(data.value.toList())
                    }

                }

                             }, colors = ButtonDefaults.buttonColors(backgroundColor = LightBlue)) {
                Text(text = "Confirm", color = Color.White)
            }
            Button(onClick = {  navController.navigateUp() }, colors = ButtonDefaults.buttonColors(backgroundColor = LightBlue)) {
                Text(text = "Back", color = Color.White)
            }
        }
    }

}

 }
}