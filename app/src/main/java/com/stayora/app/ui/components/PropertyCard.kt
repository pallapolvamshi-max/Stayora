package com.stayora.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CompareArrows
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stayora.app.data.model.Property
import com.stayora.app.ui.theme.*

@Composable
fun PropertyCard(
    property: Property,
    onPropertyClick: (String) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    onCompareToggle: (Property) -> Unit,
    isCompared: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable { onPropertyClick(property.id) },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Visual Banner Header with Gradient & Badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                PurplePrimary,
                                PurpleDark
                            )
                        )
                    )
            ) {
                // Background decorative pattern
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.BottomStart)
                    ) {
                        Text(
                            text = property.type.label.uppercase(),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = PurpleTint,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                        Text(
                            text = property.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Top badges row: Match Score + Action buttons (Favorite & Compare)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Match score badge
                    CompatibilityBadge(score = property.matchScore)

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Compare icon button
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(if (isCompared) PurpleAccent else Color.White.copy(alpha = 0.85f))
                                .clickable { onCompareToggle(property) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Compare",
                                tint = if (isCompared) Color.White else PurpleDark,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        // Favorite heart icon button
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.85f))
                                .clickable { onFavoriteToggle(property.id) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (property.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (property.isFavorite) CoralBadge else CharcoalDark,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            // Card Body Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                // Rent and Deposit row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "₹${property.monthlyRent}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = PurplePrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                        Text(
                            text = " / month",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = CharcoalLight
                            ),
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }

                    // Security deposit badge
                    Text(
                        text = "Deposit: ₹${property.deposit}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = CharcoalMedium,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Distance & Commute info
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = ElectricBlue,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${property.distanceKm} km (${property.travelTimeMinutes} min walk to campus)",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = CharcoalMedium,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Address snippet
                Text(
                    text = property.address,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = CharcoalLight
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Sharing options chips (2 sharing / 4 sharing)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    property.sharingOptions.take(2).forEach { option ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(PurpleTint.copy(alpha = 0.5f))
                                .padding(horizontal = 7.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "${option.sharingType} Sharing: ₹${option.rentPerBed}",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontSize = 11.sp,
                                    color = PurpleDark,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Key amenities row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    property.amenities.take(3).forEach { amenity ->
                        AmenityChip(text = amenity)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Divider(color = BorderSubtle.copy(alpha = 0.6f))

                Spacer(modifier = Modifier.height(8.dp))

                // Bottom row: Rating + Owner badge + Availability
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = StarGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${property.rating}",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = CharcoalDark
                            )
                        )
                        Text(
                            text = " (${property.reviewCount})",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = CharcoalLight
                            )
                        )
                    }

                    if (property.ownerVerified) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(VerifiedGreenBg)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = VerifiedGreen,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "Verified",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = VerifiedGreen,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 10.5.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
