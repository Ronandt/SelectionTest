package com.example.xx_module_b_pm

import android.content.Context
import com.google.gson.Gson
import org.json.JSONObject

class JsonExtractor(
    private val context: Context
) {
    private val gson = Gson()
    fun extractFoodItems(): List<Food>{
     return   gson.fromJson(JSONObject(context.resources.openRawResource(R.raw.fooditems).bufferedReader().readText()).getJSONArray("foodItems").toString(), Array<Food>::class.java).toList()

    }
}

data class Food(
   val imageName: String,
   val name: String,
   val price: Float,
   val description: String
)