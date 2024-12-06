package com.example.lookinapp.bottomnav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lookinapp.screen.pref.PreferencesHelper
import com.example.lookinapp.ui.theme.Purple500

@Composable
fun BottomNav() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val darkModeState = remember { mutableStateOf(PreferencesHelper.getDarkModeState(context)) }

    LaunchedEffect(Unit) {
        darkModeState.value = PreferencesHelper.getDarkModeState(context)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().background(if (darkModeState.value) Color.Black else Color.White),
        bottomBar = {
            BottomBar(navController = navController, isDarkMode = darkModeState.value)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            BottomNavGraph(navController = navController, darkModeState = darkModeState)
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController, isDarkMode: Boolean) {
    val backgroundColor = if (isDarkMode) Color(0xFF474444) else Color.White

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(8.dp)
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val screens = listOf(
            BottomBarScreen.Home,
            BottomBarScreen.Map,
            BottomBarScreen.Setting
        )

        val navStackBackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navStackBackEntry?.destination

        screens.forEach { screen ->
            AddItem(screen, currentDestination, navController, isDarkMode)
        }
    }
}

@Composable
fun AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    isDarkMode: Boolean
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    val background = if (selected) Purple500.copy(alpha = 0.6f) else Color.Transparent

    val contentColor = when {
        selected -> if (isDarkMode) Color.Gray else Color.White
        else -> if (isDarkMode) Color.White else Color.Black
    }

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "icon",
                tint = contentColor,
                modifier = Modifier
                    .size(32.dp)
                    .fillMaxWidth()
            )
            AnimatedVisibility(visible = selected) {
                Text(
                    text = screen.title,
                    color = contentColor
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BottomNavPreview() {
    BottomNav()
}
