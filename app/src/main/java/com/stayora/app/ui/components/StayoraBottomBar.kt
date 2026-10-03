package com.stayora.app.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stayora.app.ui.navigation.BottomNavItems
import com.stayora.app.ui.navigation.Screen
import com.stayora.app.ui.theme.CharcoalLight
import com.stayora.app.ui.theme.PurplePrimary
import com.stayora.app.ui.theme.PurpleTint

@Composable
fun StayoraBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    compareCount: Int = 0
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        BottomNavItems.forEach { screen ->
            val isSelected = currentRoute == screen.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(screen.route) },
                icon = {
                    BadgedBox(badge = {
                        if (screen == Screen.Compare && compareCount > 0) {
                            Badge(
                                containerColor = PurplePrimary,
                                contentColor = Color.White
                            ) {
                                Text("$compareCount")
                            }
                        }
                    }) {
                        screen.icon?.let { iconVector ->
                            Icon(
                                imageVector = iconVector,
                                contentDescription = screen.title,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                },
                label = {
                    Text(
                        text = screen.title,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PurplePrimary,
                    selectedTextColor = PurplePrimary,
                    unselectedIconColor = CharcoalLight,
                    unselectedTextColor = CharcoalLight,
                    indicatorColor = PurpleTint
                )
            )
        }
    }
}
