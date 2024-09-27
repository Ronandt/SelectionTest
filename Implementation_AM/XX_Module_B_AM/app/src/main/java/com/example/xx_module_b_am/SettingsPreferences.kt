package com.example.xx_module_b_am

import android.content.Context
import android.content.SharedPreferences

class SettingsPreferences(private val context: Context) {
    private val sharedPref = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun insertListPreference(view: ViewMode) {
        with(sharedPref.edit()) {
            //this.putString(view.toString(), )
            this.commit()
        }
    }
}