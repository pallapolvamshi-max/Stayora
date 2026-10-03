package com.stayora.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.stayora.app.data.model.Property
import com.stayora.app.ui.components.PropertyCard
import com.stayora.app.ui.theme.*

@Composable
fun FavoritesScreen(
    favoriteProperties: List<Property>,
    compareList: List<Property>,
    onPropertyClick: (String) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    onCompareToggle: (Property) -> Unit,
    onExploreClick: () -> Unit
) {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Saved Hostels",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = CharcoalDark
                    )
                )
                Text(
                    text = "${favoriteProperties.size} hostels shortlisted by you",
                    style = MaterialTheme.typography.bodySmall.copy(color = CharcoalLight)
                )
            }
        }
    ) { paddingValues ->
        if (favoriteProperties.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GrayUltraLight)
                    .padding(paddingValues)
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = CoralBadge.copy(alpha = 0.6f),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Your Shortlist is Empty",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Tap the heart icon on any hostel or PG to bookmark it for later review and price comparison.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = CharcoalLight,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = onExploreClick,
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Explore Recommendations")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GrayUltraLight)
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(favoriteProperties) { property ->
                    PropertyCard(
                        property = property,
                        onPropertyClick = onPropertyClick,
                        onFavoriteToggle = onFavoriteToggle,
                        onCompareToggle = onCompareToggle,
                        isCompared = compareList.any { it.id == property.id }
                    )
                }
            }
        }
    }
}
