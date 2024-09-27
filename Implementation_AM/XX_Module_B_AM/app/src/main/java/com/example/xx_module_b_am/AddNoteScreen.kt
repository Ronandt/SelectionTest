package com.example.xx_module_b_am

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.xx_module_b_am.ui.theme.BackgroundColor
import com.example.xx_module_b_am.ui.theme.Brown
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import coil.compose.AsyncImage
import com.example.xx_module_b_am.ui.theme.DarkGray
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun AddNoteScreen(navController: NavController) {
    var currentUri by remember { mutableStateOf<Uri>(Uri.EMPTY)}
    var keyboardManager = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val database = remember {
        CoreDatabase.getDb(context)
    }
    var activity = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia() ) {
        if(it != null) {
            context.contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            currentUri = it

        }
    }
    var focusManager = LocalFocusManager.current
    var subject by remember {
        mutableStateOf(""   )
    }
    val scope = rememberCoroutineScope()
    var focused by remember { mutableStateOf(false  )}
    var description by remember { mutableStateOf("" )}
    LaunchedEffect(key1 = keyboardManager) {

    }
    LaunchedEffect(key1 = focusManager) {

    }
    Column(modifier = Modifier
        .background(color = BackgroundColor)
      ) {
        Column(
            Modifier.padding(10.dp)
          ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = {
                    scope.launch {
                        database.noteDao().insert(Note(date = System.currentTimeMillis(), description = description, subject = subject, favourite =  false, imageUri = currentUri.toString(), ))
                    }
                    navController.navigateUp()}) {

                    Icon(painter = painterResource(id = R.drawable.arrow_left), contentDescription ="Left", modifier = Modifier.size(30.dp  ) )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = subject , onValueChange = {
                if(subject.length < 51) {
                    subject = it
                }
              }, modifier = Modifier
                .fillMaxWidth()
  , label = {
                Text(text = "Subject")
            }, singleLine = true)
            Spacer(modifier = Modifier.height(10.dp))
            TextField(value = description, onValueChange ={
                if(description.length < 51) {
                    description = it
                }
            } , modifier = Modifier
                .fillMaxWidth()
                .height(if (currentUri != Uri.EMPTY) 150.dp else 250.dp)
            )
            var contextpopup by remember {
                mutableStateOf(false    )
            }
            if(currentUri != Uri.EMPTY) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(onClick = {}, onLongClick = {
                        contextpopup = true
                    }), horizontalArrangement = Arrangement.Center) {
                    DropdownMenu(expanded = contextpopup, onDismissRequest = {contextpopup = false  }) {
                        DropdownMenuItem(text = { Text(text = "Remove") }, onClick = {
                            currentUri = Uri.EMPTY
                            contextpopup = false
                        })
                    }
                    AsyncImage(model = currentUri, contentDescription ="Currnt uri" , modifier = Modifier
                        .padding(10.dp)
                        .requiredSize(100.dp), contentScale = ContentScale.Crop, alignment = Alignment.Center)
                }
            }



        }
        Spacer(modifier = Modifier.weight(0.5f))
        Row(modifier = Modifier
            .fillMaxWidth()

            .background(DarkGray)) {
            IconButton(onClick = {
                activity.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }) {
                Icon(painter = painterResource(id = R.drawable.plus), contentDescription ="", tint = Color.White )
            }
        }
    }

}