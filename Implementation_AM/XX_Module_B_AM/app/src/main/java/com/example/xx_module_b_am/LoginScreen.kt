package com.example.xx_module_b_am

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.xx_module_b_am.ui.theme.BackgroundColor
import com.example.xx_module_b_am.ui.theme.ButtonColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {

    var userId by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}
    var showPassword by remember { mutableStateOf(false   )}
    Column(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundColor)
        .padding(horizontal = 20.dp, vertical = 40.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp    )  ) {
        Image(painter = painterResource(id = R.drawable.icon), contentDescription = "Icon", modifier = Modifier.size(250.dp ), contentScale = ContentScale.Crop)
        Text(text = "NOTEme", fontWeight = FontWeight.SemiBold)
        Text(text = "User id", modifier = Modifier.align(Alignment.Start))
        OutlinedTextField(value = userId    , onValueChange = {userId = it}, colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Password", modifier = Modifier.align(Alignment.Start))
        OutlinedTextField(value = password, onValueChange = {password = it  }, visualTransformation = if(showPassword) VisualTransformation.None else PasswordVisualTransformation(), trailingIcon = {
                                                                                              IconButton(
                                                                                                  onClick = { showPassword = !showPassword }) {
                                                                                                  if(showPassword) {
                                                                                                      Icon(
                                                                                                          painter = painterResource(
                                                                                                              id = R.drawable.eye_off_outline__1_
                                                                                                          ),
                                                                                                          contentDescription = "Eye"
                                                                                                      )
                                                                                                  } else {
                                                                                                      Icon(
                                                                                                          painter = painterResource(
                                                                                                              id = R.drawable.eye_outline__1_
                                                                                                          ),
                                                                                                          contentDescription = "Eye"
                                                                                                      )
                                                                                                  }

                                                                                              }
        }, modifier = Modifier.fillMaxWidth(),colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("notes")}, colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor), modifier = Modifier.width(200.dp  )) {
            Text(text = "LOGIN", color = Color.White)
        }
    }
    
}