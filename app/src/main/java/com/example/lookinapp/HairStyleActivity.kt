package com.example.lookinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.example.lookinapp.screen.ImageSliderScreen
import com.example.lookinapp.ui.theme.LookinAppTheme

class HairStyleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val imagesList = intent.getStringArrayListExtra("imagesList") ?: arrayListOf<String>()
        val textList = intent.getStringArrayListExtra("textList") ?: arrayListOf<String>()

        setContent {
            LookinAppTheme {
                ImageSliderScreen(imagesList, textList)
            }
        }
    }
}
