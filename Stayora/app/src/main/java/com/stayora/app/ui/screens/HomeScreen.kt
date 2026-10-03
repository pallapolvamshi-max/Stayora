package com.stayora.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stayora.app.data.model.Property
import com.stayora.app.data.model.PropertyType
import com.stayora.app.data.model.RoommateProfile
import com.stayora.app.data.model.UserPreferences
import com.stayora.app.ui.components.CompatibilityBadge
import com.stayora.app.ui.components.PropertyCard
import com.stayora.app.ui.theme.*

@Composable
fun HomeScreen(
    preferences: UserPreferences,
    properties: List<Property>,
    roommates: List<RoommateProfile>,
    compareList: List<Property>,
    onPropertyClick: (String) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    onCompareToggle: (Property) -> Unit,
    onSearchClick: () -> Unit,
    onRoommatesClick: () -> Unit,
    onPreferencesEdit: () -> Unit
) {
    var selectedCategoryId by remember { mutableStateOf("all") }

    val categoryList = remember(properties) {
        listOf(
            Triple("all", "✨ All", properties.size),
            Triple("boys", "👨 Boys Hostels", properties.count { it.type == PropertyType.BOYS_HOSTEL }),
            Triple("girls", "👩 Girls Hostels", properties.count { it.type == PropertyType.GIRLS_HOSTEL }),
            Triple("coed", "🤝 Co-ed PG", properties.count { it.type == PropertyType.COED_PG }),
            Triple("flat", "🏠 Bachelor Flat", properties.count { it.type == PropertyType.BACHELOR_ROOM })
        )
    }

    val filteredProperties = remember(selectedCategoryId, properties) {
        when (selectedCategoryId) {
            "boys" -> properties.filter { it.type == PropertyType.BOYS_HOSTEL }
            "girls" -> properties.filter { it.type == PropertyType.GIRLS_HOSTEL }
            "coed" -> properties.filter { it.type == PropertyType.COED_PG }
            "flat" -> properties.filter { it.type == PropertyType.BACHELOR_ROOM }
            else -> properties
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayUltraLight),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Hero Header with Campus badge & Search trigger
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(PurpleDark, PurplePrimary)
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Column {
                    // Location pill & Preferences edit button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White.copy(alpha = 0.2f))
                                .clickable { onPreferencesEdit() }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = PurpleTint,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${preferences.preferredLocation} • ${preferences.college.substringBefore("(").trim()}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = "Edit Preferences",
                                tint = PurpleTint,
                                modifier = Modifier.size(14.dp)
                            )
                        }

                        // App Logo Mark
                        Text(
                            text = "STAYORA",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 2.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Hi, ${preferences.userName} 👋",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = PurpleTint,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Find Your Ideal Hostel\n& Compatible Roommates",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 30.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Personalised for ${preferences.branch} • ${preferences.yearOfStudy} • ${preferences.preferredSharing}-Sharing",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = PurpleTint.copy(alpha = 0.9f)
                        )
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Fake Search Box that opens SearchFilterScreen
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { onSearchClick() },
                        color = Color.White,
                        shadowElevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 13.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = PurplePrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Search hostels, PG, amenities...",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = GrayMuted
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PurpleTint)
                                    .padding(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FilterList,
                                    contentDescription = "Filters",
                                    tint = PurplePrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Quick Category Chips
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categoryList) { (catId, catLabel, count) ->
                    val isSel = selectedCategoryId == catId
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSel) PurplePrimary else Color.White,
                        shadowElevation = if (isSel) 4.dp else 1.dp,
                        modifier = Modifier.clickable { selectedCategoryId = catId }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = catLabel,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = if (isSel) Color.White else CharcoalDark,
                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(if (isSel) Color.White.copy(alpha = 0.25f) else PurpleTint.copy(alpha = 0.5f))
                                    .padding(horizontal = 6.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "$count",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isSel) Color.White else PurpleDark,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section: Compatible Roommates Spotlight
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Compatible Roommates",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = CharcoalDark
                            )
                        )
                        Text(
                            text = "Students looking for roommates in your area",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = CharcoalLight
                            )
                        )
                    }

                    TextButton(onClick = onRoommatesClick) {
                        Text("View All", color = PurplePrimary, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(roommates) { profile ->
                        Card(
                            modifier = Modifier
                                .width(250.dp)
                                .clickable { onRoommatesClick() },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CompatibilityBadge(score = profile.compatibilityScore)
                                    Text(
                                        text = "${profile.preferredSharing} Sharing",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = CharcoalLight
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = profile.name,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = CharcoalDark
                                    )
                                )
                                Text(
                                    text = "${profile.branch} • ${profile.yearOfStudy}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = CharcoalLight
                                    )
                                )
                                Text(
                                    text = "From ${profile.hometown}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = PurplePrimary,
                                        fontWeight = FontWeight.Medium
                                    )
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    profile.personalityTraits.take(2).forEach { trait ->
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(PurpleTint.copy(alpha = 0.6f))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = trait,
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = PurpleDark,
                                                    fontSize = 10.sp
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Section: Recommended Hostels & PGs Near You
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Recommended for You",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = CharcoalDark
                            )
                        )
                        Text(
                            text = "Filtered by distance, verified safety & budget",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = CharcoalLight
                            )
                        )
                    }

                    Text(
                        text = "${filteredProperties.size} found",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = PurplePrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

        items(filteredProperties) { property ->
            Box(modifier = Modifier.padding(horizontal = 20.dp)) {
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
