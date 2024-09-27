package com.example.xx_module_b_am

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.xx_module_b_am.ui.theme.BackgroundColor
import com.example.xx_module_b_am.ui.theme.DarkGray
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun EditScreen(navController: NavController, id: String?) {

    var keyboardManager = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val database = remember {
        CoreDatabase.getDb(context)
    }

    var data by remember {mutableStateOf<Note?    >(null)}

    var subject by remember {
        mutableStateOf("${data?.subject}"   )
    }
    val scope = rememberCoroutineScope()
    var focused by remember { mutableStateOf(false  ) }
    var description by remember { mutableStateOf(data?.description ) }
    var currentUri by remember { mutableStateOf<Uri?>(data?.imageUri?.toUri()) }
    var activity = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia() ) {
        if(it != null) {
            context.contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            currentUri = it

        }
    }
    LaunchedEffect(key1 = Unit ) {
        data =   id?.toInt()?.let { database.noteDao().select(it) }
        description = data?.description
        currentUri = data?.imageUri?.toUri()
        subject = data?.subject.toString()

    }
    var focusManager = LocalFocusManager.current

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
                        description?.let { data?.copy(description = description!!, subject =  subject, imageUri =currentUri.toString() ) }
                            ?.let { database.noteDao().update(it) }
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
            description?.let {
                TextField(value = it, onValueChange ={
                    if(description!!.length < 200) {
                        description = it
                    }
                } , modifier = Modifier
                    .fillMaxWidth()
                    .height(if (currentUri != Uri.EMPTY) 150.dp else 250.dp)
                )
            }
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