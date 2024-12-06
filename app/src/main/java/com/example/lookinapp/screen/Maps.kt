    package com.example.lookinapp.screen

    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.remember
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.unit.sp
    import com.example.lookinapp.screen.pref.PreferencesHelper


    @Composable
    fun Maps() {
        val context = LocalContext.current

        val isDarkMode = remember { PreferencesHelper.getDarkModeState(context) }

        val backgroundColor = if (isDarkMode) Color(0xFF474444) else Color.White
        val textColor = if (isDarkMode) Color.White else Color.Black

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Text(text = "Maps", fontSize = 20.sp, color = textColor)
        }
    }
