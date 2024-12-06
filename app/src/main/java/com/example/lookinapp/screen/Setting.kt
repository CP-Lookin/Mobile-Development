package com.example.lookinapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.lookinapp.screen.pref.PreferencesHelper
import androidx.compose.material3.MaterialTheme

@Composable
fun Setting(
    darkModeState: MutableState<Boolean>
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (darkModeState.value) Color(0xFF474444) else Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Setting",
            style = MaterialTheme.typography.headlineMedium,
            color = if (darkModeState.value) Color.White else Color(0xFF474444),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Dark Theme",
                fontSize = 24.sp,
                color = if (darkModeState.value) Color.White else Color(0xFF474444)
            )

            Switch(
                checked = darkModeState.value,
                onCheckedChange = { isChecked ->
                    darkModeState.value = isChecked
                    PreferencesHelper.saveDarkModeState(context, isChecked)
                }
            )
        }
    }
}
