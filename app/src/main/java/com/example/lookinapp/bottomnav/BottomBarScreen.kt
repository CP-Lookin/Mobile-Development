package com.example.lookinapp.bottomnav

import com.example.lookinapp.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = R.drawable.ic_bottom_home
    )

    object Map : BottomBarScreen(
        route = "map",
        title = "Map",
        icon = R.drawable.ic_bottom_map
    )

    object Setting : BottomBarScreen(
        route = "setting",
        title = "Setting",
        icon = R.drawable.ic_bottom_setting
    )
}
