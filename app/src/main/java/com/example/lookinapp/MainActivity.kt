package com.example.lookinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.example.lookinapp.bottomnav.BottomNav
import com.example.lookinapp.screen.pref.PreferencesHelper
import com.example.lookinapp.ui.theme.LookinAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isDarkMode = PreferencesHelper.getDarkModeState(this)

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        setContent {
            LookinAppTheme {
                CompositionLocalProvider(LocalContext provides this) {
                    BottomNav()
                }
            }
        }
    }
}

