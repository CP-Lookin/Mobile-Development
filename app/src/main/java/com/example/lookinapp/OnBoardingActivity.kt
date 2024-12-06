package com.example.lookinapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lookinapp.ui.theme.LookinAppTheme

class OnBoardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPreferences = getSharedPreferences("appPreferences", MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        setContent {
            LookinAppTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF218BEF),
                                    Color(0xFF135089)
                                )
                            )
                        )
                        .padding(16.dp)
                ) {
                    val insets = WindowInsets.systemBars.asPaddingValues()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = insets.calculateTopPadding(), bottom = insets.calculateBottomPadding()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Welcome to ",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 45.sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "Lookin",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 45.sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                color = Color.White
                            ),
                            modifier = Modifier
                                .padding(bottom = 32.dp)
                        )

                        Text(
                            text = "Flatter Your Face,",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 38.sp,
                                color = Color.White
                            )
                        )

                        Text(
                            text = "Find Your Style",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 38.sp,
                                color = Color.White
                            )
                        )
                    }

                    if (isFirstRun) {
                        Button(
                            onClick = {
                                try {
                                    sharedPreferences.edit().putBoolean("isFirstRun", false).apply()
                                    val intent = Intent(this@OnBoardingActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } catch (e: Exception) {
                                    Log.e("OnBoardingActivity", "Error navigating: ${e.message}")
                                }
                            },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .width(300.dp)
                                .padding(start = 25.dp, end = 25.dp, top = 50.dp, bottom = 50.dp)
                                .align(Alignment.BottomCenter)
                                .padding(bottom = insets.calculateBottomPadding()),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(text = "Get Started", fontSize = 21.sp)
                        }
                    } else {
                        val intent = Intent(this@OnBoardingActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}
