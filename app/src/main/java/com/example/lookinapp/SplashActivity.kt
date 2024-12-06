package com.example.lookinapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lookinapp.screen.pref.PreferencesHelper
import com.example.lookinapp.ui.theme.LookinAppTheme
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LookinAppTheme {
                SplashScreen(onTimeout = {
                    startActivity(Intent(this, OnBoardingActivity::class.java))
                    finish()
                })
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val alpha = remember { Animatable(0f) }

    val context = LocalContext.current

    val isDarkMode = remember { PreferencesHelper.getDarkModeState(context) }

    val backgroundColor = if (isDarkMode) Color(0xFF474444) else Color.White
    val textColor = if (isDarkMode) Color.White else Color.Black


    LaunchedEffect(key1 = true) {
        alpha.animateTo(1f, animationSpec = tween(1500))

        delay(3000)

        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_image),
                contentDescription = null,
                modifier = Modifier
                    .size(170.dp)
                    .alpha(alpha.value)
            )

            Text(
                text = "Lookin",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 50.sp

                ),
                color = textColor,
                modifier = Modifier.alpha(alpha.value)
            )
        }
    }
}
