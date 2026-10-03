package com.stayora.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stayora.app.data.repository.StayoraRepository
import com.stayora.app.ui.components.StayoraBottomBar
import com.stayora.app.ui.navigation.Screen
import com.stayora.app.ui.screens.*
import kotlinx.coroutines.launch

@Composable
fun StayoraApp(
    repository: StayoraRepository = remember { StayoraRepository() }
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val preferences by repository.preferences.collectAsState()
    val properties by repository.properties.collectAsState()
    val compareList by repository.compareList.collectAsState()
    val conversations by repository.conversations.collectAsState()
    val roommates = remember { repository.getRoommateProfiles() }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Search.route,
        Screen.Roommates.route,
        Screen.Favorites.route,
        Screen.Compare.route
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (showBottomBar) {
                StayoraBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    compareCount = compareList.size
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route
            ) {
                // Onboarding Screen
                composable(Screen.Onboarding.route) {
                    OnboardingScreen(
                        currentPreferences = preferences,
                        onComplete = { newPrefs ->
                            repository.updatePreferences(newPrefs)
                            scope.launch {
                                snackbarHostState.showSnackbar("Preferences updated! Showing best matched hostels.")
                            }
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Onboarding.route) { inclusive = true }
                            }
                        },
                        onSkip = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Onboarding.route) { inclusive = true }
                            }
                        }
                    )
                }

                // Home Screen
                composable(Screen.Home.route) {
                    HomeScreen(
                        preferences = preferences,
                        properties = properties,
                        roommates = roommates,
                        compareList = compareList,
                        onPropertyClick = { id ->
                            navController.navigate(Screen.PropertyDetail.createRoute(id))
                        },
                        onFavoriteToggle = { id ->
                            repository.toggleFavorite(id)
                        },
                        onCompareToggle = { property ->
                            val added = repository.toggleCompare(property)
                            scope.launch {
                                if (added) {
                                    snackbarHostState.showSnackbar("Added to comparison matrix (${compareList.size + 1}/3)")
                                } else {
                                    snackbarHostState.showSnackbar("Removed from comparison")
                                }
                            }
                        },
                        onSearchClick = {
                            navController.navigate(Screen.Search.route)
                        },
                        onRoommatesClick = {
                            navController.navigate(Screen.Roommates.route)
                        },
                        onPreferencesEdit = {
                            navController.navigate(Screen.Onboarding.route)
                        }
                    )
                }

                // Search Screen
                composable(Screen.Search.route) {
                    SearchFilterScreen(
                        properties = properties,
                        compareList = compareList,
                        onPropertyClick = { id ->
                            navController.navigate(Screen.PropertyDetail.createRoute(id))
                        },
                        onFavoriteToggle = { id -> repository.toggleFavorite(id) },
                        onCompareToggle = { property ->
                            val added = repository.toggleCompare(property)
                            scope.launch {
                                snackbarHostState.showSnackbar(if (added) "Added to comparison" else "Removed from comparison")
                            }
                        },
                        onBack = { navController.popBackStack() }
                    )
                }

                // Roommates Screen
                composable(Screen.Roommates.route) {
                    RoommatesScreen(
                        roommates = roommates,
                        preferences = preferences,
                        onConnectRoommate = { profile ->
                            scope.launch {
                                snackbarHostState.showSnackbar("Connection request sent to ${profile.name}!")
                            }
                        }
                    )
                }

                // Saved / Favorites Screen
                composable(Screen.Favorites.route) {
                    FavoritesScreen(
                        favoriteProperties = properties.filter { it.isFavorite },
                        compareList = compareList,
                        onPropertyClick = { id ->
                            navController.navigate(Screen.PropertyDetail.createRoute(id))
                        },
                        onFavoriteToggle = { id -> repository.toggleFavorite(id) },
                        onCompareToggle = { property ->
                            repository.toggleCompare(property)
                        },
                        onExploreClick = {
                            navController.navigate(Screen.Home.route)
                        }
                    )
                }

                // Compare Screen
                composable(Screen.Compare.route) {
                    CompareScreen(
                        compareList = compareList,
                        onRemoveFromCompare = { id -> repository.removeFromCompare(id) },
                        onPropertyClick = { id ->
                            navController.navigate(Screen.PropertyDetail.createRoute(id))
                        },
                        onExploreClick = {
                            navController.navigate(Screen.Home.route)
                        }
                    )
                }

                // Property Detail Screen
                composable(
                    route = Screen.PropertyDetail.route,
                    arguments = listOf(navArgument("propertyId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val propertyId = backStackEntry.arguments?.getString("propertyId") ?: ""
                    val property = repository.getPropertyById(propertyId)

                    PropertyDetailScreen(
                        property = property,
                        isFavorite = property?.isFavorite ?: false,
                        isCompared = compareList.any { it.id == propertyId },
                        onBack = { navController.popBackStack() },
                        onFavoriteToggle = { repository.toggleFavorite(propertyId) },
                        onCompareToggle = {
                            property?.let {
                                val added = repository.toggleCompare(it)
                                scope.launch {
                                    snackbarHostState.showSnackbar(if (added) "Added to comparison" else "Removed from comparison")
                                }
                            }
                        },
                        onChatClick = { id ->
                            navController.navigate(Screen.Chat.createRoute(id))
                        },
                        onNearbyFacilitiesClick = { id ->
                            navController.navigate(Screen.NearbyFacilities.createRoute(id))
                        }
                    )
                }

                // Chat with Owner Screen
                composable(
                    route = Screen.Chat.route,
                    arguments = listOf(navArgument("propertyId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val propertyId = backStackEntry.arguments?.getString("propertyId") ?: ""
                    val property = repository.getPropertyById(propertyId)
                    val chatMessages = conversations[propertyId] ?: emptyList()

                    ChatScreen(
                        property = property,
                        messages = chatMessages,
                        onSendMessage = { text ->
                            repository.sendMessage(propertyId, text)
                        },
                        onBack = { navController.popBackStack() }
                    )
                }

                // Nearby Facilities Screen
                composable(
                    route = Screen.NearbyFacilities.route,
                    arguments = listOf(navArgument("propertyId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val propertyId = backStackEntry.arguments?.getString("propertyId") ?: ""
                    val property = repository.getPropertyById(propertyId)
                    val facilities = repository.getNearbyFacilities(propertyId)

                    NearbyFacilitiesScreen(
                        property = property,
                        facilities = facilities,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
