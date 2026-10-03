package com.stayora.app.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stayora.app.data.model.FacilityCategory
import com.stayora.app.data.model.NearbyFacility
import com.stayora.app.data.model.Property
import com.stayora.app.ui.theme.*

@Composable
fun NearbyFacilitiesScreen(
    property: Property?,
    facilities: List<NearbyFacility>,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf<FacilityCategory?>(null) }

    val filteredList = remember(selectedCategory, facilities) {
        if (selectedCategory == null) facilities else facilities.filter { it.category == selectedCategory }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CharcoalDark)
                    }
                    Column {
                        Text(
                            text = "Nearby Facilities & Essentials",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Near ${property?.title ?: "Campus Hostels"}",
                            style = MaterialTheme.typography.bodySmall.copy(color = CharcoalLight),
                            maxLines = 1
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Category filter chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedCategory == null,
                            onClick = { selectedCategory = null },
                            label = { Text("All Essentials") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PurplePrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                    items(FacilityCategory.values()) { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat.title.substringBefore(" ")) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PurplePrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(GrayUltraLight)
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Emergency Quick Dial Notice
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = CoralBadgeBg)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Favorite, contentDescription = null, tint = CoralBadge)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Emergency Medical & Campus Helpline",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = CoralBadge
                                )
                            )
                            Text(
                                text = "Malla Reddy Narayana Hospital (24/7 Ambulance: 040-23783000)",
                                style = MaterialTheme.typography.bodySmall.copy(color = CharcoalDark)
                            )
                        }
                    }
                }
            }

            items(filteredList) { facility ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when (facility.category) {
                                                FacilityCategory.HOSPITAL, FacilityCategory.PHARMACY -> CoralBadgeBg
                                                FacilityCategory.BUS_STATION, FacilityCategory.METRO_STATION -> ElectricBlueBg
                                                else -> PurpleTint
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = when (facility.category) {
                                            FacilityCategory.HOSPITAL, FacilityCategory.PHARMACY -> CoralBadge
                                            FacilityCategory.BUS_STATION, FacilityCategory.METRO_STATION -> ElectricBlue
                                            else -> PurplePrimary
                                        },
                                        modifier = Modifier.size(22.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = facility.category.title,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = CharcoalLight,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    Text(
                                        text = facility.name,
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = CharcoalDark
                                        )
                                    )
                                }
                            }

                            // Distance Pill
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PurpleTint)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "${facility.distanceKm} km",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = PurplePrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Travel time snippet
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = ElectricBlue,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${facility.travelMinutes} mins by ${facility.mode}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = CharcoalMedium,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = facility.address,
                            style = MaterialTheme.typography.bodySmall.copy(color = CharcoalLight)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = facility.contactOrTiming,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = PurpleDark,
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Direction button
                        Button(
                            onClick = {
                                val mapUri = Uri.parse("geo:0,0?q=${Uri.encode(facility.name + " " + facility.address)}")
                                val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
                                context.startActivity(mapIntent)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = PurpleCardBg),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Navigation, contentDescription = null, tint = PurplePrimary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Get Directions", color = PurplePrimary, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
