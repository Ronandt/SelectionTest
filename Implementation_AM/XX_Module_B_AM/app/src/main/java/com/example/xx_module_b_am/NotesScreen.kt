package com.example.xx_module_b_am

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.xx_module_b_am.ui.theme.BackgroundColor
import com.example.xx_module_b_am.ui.theme.Brown
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.xx_module_b_am.ui.theme.Red
import java.text.SimpleDateFormat
enum class ViewMode {
    List,
    Card
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun NotesScreen(navController: NavController) {
    var viewMode by remember {mutableStateOf(ViewMode.List)}
    val context = LocalContext.current
    val database = remember {
        CoreDatabase.getDb(context)
    }
    var data = remember { mutableStateListOf<Note>()}

    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit ) {
        data.addAll(database.noteDao().selectAll().sortedByDescending { it.date })

    }
 ModalNavigationDrawer(drawerContent = {
                                       ModalDrawerSheet() {
                                           Column(modifier = Modifier.padding(10.dp )) {
                                               Spacer(modifier = Modifier.height(20.dp))
                                               Image(painter = painterResource(id = R.drawable.blackman), contentDescription ="blacman", modifier = Modifier
                                                   .clip(
                                                       CircleShape
                                                   )
                                                   .size(30.dp)  )
                                               Text(text = "John smith", fontSize = 14.sp   )
                                               Spacer(modifier = Modifier.height(4.dp))
                                               Divider(modifier = Modifier.fillMaxWidth(1f))
                                               Spacer(modifier = Modifier.height(10.dp))
                                               Row(
                                                   Modifier
                                                       .fillMaxWidth(1f)
                                                       .clickable { }
                                                       ) {
                                                   Text(text = "Notes", modifier = Modifier.padding(vertical = 10.dp))
                                               }
                                               Row(
                                                   Modifier
                                                       .fillMaxWidth(1f)
                                                       .clickable { navController.navigate("favourites") }
                                               ) {
                                                   Text(text = "Favourites", modifier = Modifier.padding(vertical = 10.dp))
                                               }
                                               Spacer(modifier = Modifier.weight(1f))
                                               Row(
                                                   Modifier
                                                       .fillMaxWidth(1f)
                                                       .clickable {
                                                           navController.navigate("login") {
                                                               this.launchSingleTop = true
                                                               this.popUpTo("login")

                                                           }
                                                       }
                                               ) {
                                                   Text(text = "Logout", modifier = Modifier.padding(vertical = 10.dp))
                                               }
                                           }

                                       }
 }, drawerState =drawerState ) {
     Scaffold(
         topBar = {
             TopAppBar(actions = {

                 IconButton(onClick = { navController.navigate("addNote")}) {

                     Icon(painter = painterResource(id = R.drawable.plus), contentDescription = "Plus", tint = Color.White)
                 }
                 if(data.isNotEmpty()) {
                     if(viewMode == ViewMode.List) {
                         IconButton(onClick = { viewMode = ViewMode.Card }) {
                             Icon(painter = painterResource(id = R.drawable.view_grid), contentDescription ="Grid", tint = Color.White )
                         }
                     } else {
                         IconButton(onClick = { viewMode = ViewMode.List }) {
                             Icon(painter = painterResource(id = R.drawable.list_box), contentDescription ="Grid", tint = Color.White )
                         }
                     }

                 }


             },title = { Text(text = "NOTEme", color = Color.White, fontWeight = FontWeight.SemiBold) }, colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Brown), navigationIcon = {
                 IconButton(onClick = {
                     scope.launch {
                         drawerState.open()
                     }
                   }) {
                     Icon(Icons.Default.Menu, contentDescription ="Menyu", tint = Color.White )
                 }
             })
         }
     ) {
         if(data.isEmpty()) {
             Column(modifier = Modifier
                 .fillMaxSize()
                 .background(BackgroundColor)
                 .padding(it)
                 .padding(horizontal = 20.dp, vertical = 40.dp), verticalArrangement = Arrangement.spacedBy(10.dp    ), horizontalAlignment = Alignment.CenterHorizontally) {
                 Text(text = "Currently no content to display", fontWeight = FontWeight.Bold)
                 Spacer(modifier = Modifier.height(10.dp    ))
                 Image(painter = painterResource(id = R.drawable.file), contentDescription ="File", modifier = Modifier.size(250.dp  ), contentScale = ContentScale.Crop)
                 Spacer(modifier = Modifier.height(15.dp))
                 Text(text = "Create new notes by pressing on the \"+\"")

             }
         } else {
             if(viewMode ==  ViewMode.List) {
                 LazyColumn(modifier = Modifier
                     .fillMaxSize()
                     .background(BackgroundColor)
                     .padding(it), horizontalAlignment = Alignment.CenterHorizontally) {
                     items(data.size) {

                         Column(modifier = Modifier
                             .padding(10.dp)
                             .fillMaxWidth().combinedClickable(onClick = {
                                 navController.navigate("edit/${data[it].id}")
                             }, onDoubleClick = {
                                 scope.launch {
                                     database.noteDao().update(data[it].copy(favourite = true))
                                 }
                                 Toast.makeText(context, "Note has been favourited!", Toast.LENGTH_LONG).show()
                             })
                             , verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                             Column {
                                 Column {
                                     Text(text = "${data[it].subject}", fontSize = 17.sp    )
                                     Spacer(modifier = Modifier.height(4.dp))
                                     Row(  horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.width(300.dp  )) {
                                         Column() {

                                             Text(text ="${data[it].description}", color = Color.Gray, maxLines = 2, overflow = TextOverflow.Ellipsis, fontSize = 19.sp  , modifier = Modifier.width(if(data[it].imageUri.toUri() == Uri.EMPTY) 300.dp    else 200.dp    ) )

                                         }
                                         if(data[it].imageUri.toUri() != Uri.EMPTY  ) {
                                             AsyncImage(model = data[it].imageUri.toUri(), contentDescription = "Data", modifier = Modifier.size(80.dp ), contentScale = ContentScale.Crop)
                                         } else {
                                             Spacer(modifier = Modifier.width(80.dp))
                                         }

                                     }
                                     Spacer(modifier = Modifier.height(20.dp))

                                     Text(text = "${DateChanger(data[it].date)}", modifier = Modifier.align(Alignment.End), color = Color.Gray, fontSize = 17.sp)
                                 }
                                 }

                         }
                     }
                 }
             } else {
                 LazyVerticalGrid(columns =GridCells.Fixed(2) , modifier = Modifier
                     .background(
                         BackgroundColor
                     )
                     .fillMaxSize()
                     .padding(16.dp)
                     .padding(it)
                  , verticalArrangement = Arrangement.spacedBy(6.dp ), horizontalArrangement = Arrangement.spacedBy(6.dp    )) {
                     items(data.size) {
                         Card(backgroundColor = Red, modifier = Modifier.height(300.dp).combinedClickable(onClick = {
                             navController.navigate("edit/${data[it].id}")
                         }, onDoubleClick = {
                             scope.launch {
                                 database.noteDao().update(data[it].copy(favourite = true))
                             }
                             Toast.makeText(context, "Note has been favourited!", Toast.LENGTH_LONG).show()
                         })) {
                             Column(modifier = Modifier.padding(4.dp    )) {
                                 Text(text = "${data[it].subject}", fontWeight = FontWeight.Bold)
                                 Text(text = "${DateChanger(data[it].date)}")
                                 Spacer(modifier = Modifier.height(15.dp))
                                 if(data[it].imageUri.toUri() != Uri.EMPTY) {
                                     AsyncImage(model = "${data[it].imageUri}".toUri(), contentDescription ="", modifier = Modifier.size(120.dp ), contentScale = ContentScale.Crop )
                                 }else {
                                     Spacer(modifier = Modifier.height(10.dp))
                                 }

                                 Spacer(modifier = Modifier.height(10.dp))
                                 Text(
                                     text = "${data[it].description}",
                                     maxLines = 6,
                                     overflow = TextOverflow.Ellipsis,
                                     fontSize = 22.sp
                                 )
                             }

                         }


                     }
                 }
             }
          
         }

     }
    }

}

fun DateChanger(long: Long): String{
    val sdf = SimpleDateFormat("dd/MM/YYYY HH:mm")
    return sdf.format(long)
}