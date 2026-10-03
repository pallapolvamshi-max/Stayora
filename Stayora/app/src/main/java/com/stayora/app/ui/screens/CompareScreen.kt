package com.stayora.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stayora.app.data.model.Property
import com.stayora.app.ui.theme.*

@Composable
fun CompareScreen(
    compareList: List<Property>,
    onRemoveFromCompare: (String) -> Unit,
    onPropertyClick: (String) -> Unit,
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
                    text = "Compare Properties",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = CharcoalDark
                    )
                )
                Text(
                    text = if (compareList.isNotEmpty()) "Comparing ${compareList.size} properties side-by-side" else "No properties added for comparison",
                    style = MaterialTheme.typography.bodySmall.copy(color = CharcoalLight)
                )
            }
        }
    ) { paddingValues ->
        if (compareList.isEmpty()) {
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
                        imageVector = Icons.Default.CompareArrows,
                        contentDescription = null,
                        tint = PurpleAccent,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No Properties to Compare",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Tap the compare button (⇄) on any hostel card to compare rent, deposit, distance, amenities and rules.",
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
                        Text("Browse Hostels")
                    }
                }
            }
        } else {
            val scrollState = rememberScrollState()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GrayUltraLight)
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header row with property titles and remove buttons
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(scrollState),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        compareList.forEach { property ->
                            Card(
                                modifier = Modifier
                                    .width(220.dp),
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
                                        Text(
                                            text = property.type.label,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = PurplePrimary,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                        IconButton(
                                            onClick = { onRemoveFromCompare(property.id) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(Icons.Default.Close, contentDescription = "Remove", tint = CharcoalLight, modifier = Modifier.size(16.dp))
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = property.title,
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        minLines = 2,
                                        maxLines = 2
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Button(
                                        onClick = { onPropertyClick(property.id) },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = PurpleTint)
                                    ) {
                                        Text("View Details", color = PurpleDark, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }

                // Comparison Metrics Matrix
                val metrics = listOf(
                    "Monthly Rent" to compareList.map { "₹${it.monthlyRent} / mo" },
                    "Security Deposit" to compareList.map { "₹${it.deposit}" },
                    "Campus Distance" to compareList.map { "${it.distanceKm} km (${it.travelTimeMinutes} min)" },
                    "Rating & Reviews" to compareList.map { "⭐ ${it.rating} (${it.reviewCount})" },
                    "Curfew Timings" to compareList.map { it.timings.substringBefore("•") },
                    "Meals / Food" to compareList.map { if (it.amenities.any { a -> a.contains("Meal", ignoreCase = true) || a.contains("Food", ignoreCase = true) }) "Included (3 Meals)" else "Self-Cooking / Extra" },
                    "High-Speed Wi-Fi" to compareList.map { if (it.amenities.any { a -> a.contains("Wi-Fi", ignoreCase = true) }) "Yes (Included)" else "No" },
                    "Washing Machine" to compareList.map { if (it.amenities.any { a -> a.contains("Washing", ignoreCase = true) }) "Available" else "No" },
                    "24/7 Water & Geyser" to compareList.map { if (it.amenities.any { a -> a.contains("Water", ignoreCase = true) }) "Yes (24h)" else "Limited" },
                    "Biometric / CCTV" to compareList.map { if (it.securityDetails.any { s -> s.contains("CCTV", ignoreCase = true) || s.contains("Biometric", ignoreCase = true) }) "Yes (Full)" else "Basic" },
                    "Owner Verification" to compareList.map { if (it.ownerVerified) "Verified Owner" else "Unverified" }
                )

                items(metrics) { (metricTitle, values) ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = metricTitle,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = PurpleDark
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(scrollState),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                values.forEach { value ->
                                    Box(
                                        modifier = Modifier
                                            .width(220.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(
                                                when {
                                                    value.contains("Yes") || value.contains("Included") || value.contains("Verified") -> VerifiedGreenBg
                                                    value.contains("No") || value.contains("Unverified") -> CoralBadgeBg
                                                    else -> Color(0xFFF8FAFC)
                                                }
                                            )
                                            .padding(10.dp)
                                    ) {
                                        Text(
                                            text = value,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                color = when {
                                                    value.contains("Yes") || value.contains("Included") || value.contains("Verified") -> VerifiedGreen
                                                    value.contains("No") || value.contains("Unverified") -> CoralBadge
                                                    else -> CharcoalDark
                                                }
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
}
