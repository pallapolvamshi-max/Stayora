package com.stayora.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Landing : Screen("landing", "Welcome")
    object Onboarding : Screen("onboarding", "Preferences")
    object Home : Screen("home", "Explore", Icons.Default.Home)
    object Search : Screen("search", "Search", Icons.Default.Search)
    object Roommates : Screen("roommates", "Roommates", Icons.Default.Person)
    object Favorites : Screen("favorites", "Saved", Icons.Default.Favorite)
    object Compare : Screen("compare", "Compare", Icons.Default.Refresh)
    
    // Screens with arguments
    object PropertyDetail : Screen("property_detail/{propertyId}", "Details") {
        fun createRoute(propertyId: String) = "property_detail/$propertyId"
    }
    object Chat : Screen("chat/{propertyId}", "Chat with Owner") {
        fun createRoute(propertyId: String) = "chat/$propertyId"
    }
    object NearbyFacilities : Screen("facilities/{propertyId}", "Nearby Facilities") {
        fun createRoute(propertyId: String) = "facilities/$propertyId"
    }
}

val BottomNavItems = listOf(
    Screen.Home,
    Screen.Search,
    Screen.Roommates,
    Screen.Favorites,
    Screen.Compare
)
