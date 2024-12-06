package com.example.lookinapp.screen.pref

import android.content.Context
import android.content.SharedPreferences

object PreferencesHelper {
    private const val PREF_NAME = "app_preferences"
    private const val DARK_MODE_KEY = "dark_mode"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveDarkModeState(context: Context, isDarkMode: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(DARK_MODE_KEY, isDarkMode)
        editor.apply()
    }

    fun getDarkModeState(context: Context): Boolean {
        return getPreferences(context).getBoolean(DARK_MODE_KEY, false)
    }
}
