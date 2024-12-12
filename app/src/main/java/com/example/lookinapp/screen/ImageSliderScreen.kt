package com.example.lookinapp.screen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberImagePainter
import com.example.lookinapp.screen.pref.PreferencesHelper

@Composable
fun ImageSliderScreen(imagesList: List<String>, textList: List<String>) {

    val context = LocalContext.current

    val isDarkMode = remember { PreferencesHelper.getDarkModeState(context) }
    val textColor = if (isDarkMode) Color.White else Color(0xFF000000)

    val pageCount = imagesList.size.coerceAtMost(textList.size)

    val pageState = rememberPagerState(pageCount = { pageCount })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp)
                .zIndex(1f)
        ) {
            Text(
                text = "Hair Style",
                color = textColor,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )
        }

        FloatingActionButton(
            onClick = {
                (context as? ComponentActivity)?.finish()
            },
            containerColor = Color.Transparent,
            elevation = FloatingActionButtonDefaults.elevation(0.dp),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .zIndex(1f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(30.dp),
                    tint = textColor
                )
            }
        }

        HorizontalPager(state = pageState) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 8.dp),
            ) {
                Image(
                    painter = rememberImagePainter(imagesList[page]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Text(
                    text = textList[page],
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                    color = textColor,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier

                        .padding(bottom = 120.dp)
                        .align(Alignment.BottomCenter)
                        .zIndex(2f)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .offset(y = (-60).dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageState.pageCount) { iteration ->
                val color = if (pageState.currentPage == iteration) Color.Black else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .background(color)
                        .size(16.dp)
                )
            }
        }
    }
}
