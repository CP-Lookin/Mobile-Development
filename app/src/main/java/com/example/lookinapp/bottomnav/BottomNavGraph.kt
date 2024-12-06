package com.example.lookinapp.bottomnav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lookinapp.screen.Home
import com.example.lookinapp.screen.Maps
import com.example.lookinapp.screen.Setting

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    darkModeState: MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route,
    ) {
        composable(route = BottomBarScreen.Home.route) {
            Home()
        }
        composable(route = BottomBarScreen.Map.route) {
            Maps()
        }
        composable(route = BottomBarScreen.Setting.route) {
            Setting(darkModeState = darkModeState)
        }
    }
}
