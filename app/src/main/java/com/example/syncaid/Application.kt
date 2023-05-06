package com.example.syncaid

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

open class application : Application() {



    override fun onCreate() {
        super.onCreate()
        val userPrefs = this.getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
        val isdarkmode = userPrefs.getBoolean("DARKMODE",false)


        if (isdarkmode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}