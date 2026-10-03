package com.stayora.app.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.stayora.app.data.model.Property
import com.stayora.app.ui.components.AmenityChip
import com.stayora.app.ui.components.CompatibilityBadge
import com.stayora.app.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PropertyDetailScreen(
    property: Property?,
    isFavorite: Boolean,
    isCompared: Boolean,
    onBack: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onCompareToggle: () -> Unit,
    onChatClick: (String) -> Unit,
    onNearbyFacilitiesClick: (String) -> Unit
) {
    if (property == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Property not found")
        }
        return
    }

    val context = LocalContext.current
    var selectedSharingTab by remember { mutableIntStateOf(property.sharingOptions.firstOrNull()?.sharingType ?: 2) }

    Scaffold(
        bottomBar = {
            Surface(
                color = Color.White,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "₹${property.monthlyRent}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = PurplePrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            )
                        )
                        Text(
                            text = "Deposit: ₹${property.deposit}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = CharcoalLight
                            )
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Chat Button
                        OutlinedButton(
                            onClick = { onChatClick(property.id) },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = PurplePrimary
                            )
                        ) {
                            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Chat")
                        }

                        // Call Owner Button
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${property.ownerPhone}"))
                                context.startActivity(intent)
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Call")
                        }
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Visual Hero Header with Action buttons
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(PurpleDark, PurplePrimary)
                            )
                        )
                ) {
                    val heroImage = property.imageUrls.firstOrNull()
                    if (!heroImage.isNullOrBlank()) {
                        AsyncImage(
                            model = heroImage,
                            contentDescription = property.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Scrim overlay for contrast
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.45f),
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.85f)
                                    )
                                )
                            )
                    )
                    // Floating Top Navigation Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.9f))
                                .clickable { onBack() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CharcoalDark)
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            // Compare button
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(if (isCompared) PurpleAccent else Color.White.copy(alpha = 0.9f))
                                    .clickable { onCompareToggle() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Refresh,
                                    contentDescription = "Compare",
                                    tint = if (isCompared) Color.White else PurpleDark
                                )
                            }

                            // Favorite button
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.9f))
                                    .clickable { onFavoriteToggle() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = if (isFavorite) CoralBadge else CharcoalDark
                                )
                            }
                        }
                    }

                    // Property title & badges in hero
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(20.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(PurpleAccent)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = property.type.label,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }

                            CompatibilityBadge(score = property.matchScore)
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = property.title,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Text(
                            text = property.availabilityStatus,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = PurpleTint,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }

            // Commute & Distance Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = ElectricBlue, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${property.travelTimeMinutes} min ${property.travelMode} to campus",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                            Text(
                                text = "${property.distanceKm} km from ${property.nearbyUniversity}",
                                style = MaterialTheme.typography.bodySmall.copy(color = CharcoalLight)
                            )
                        }

                        // Nearby facilities button
                        OutlinedButton(
                            onClick = { onNearbyFacilitiesClick(property.id) },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Nearby Facilities 🏥", fontSize = 12.sp, color = PurplePrimary)
                        }
                    }
                }
            }

            // Sharing Options breakdown
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Room Sharing Options & Pricing",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        property.sharingOptions.forEach { opt ->
                            val isSel = selectedSharingTab == opt.sharingType
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSel) PurpleTint else Color(0xFFF8FAFC))
                                    .border(
                                        1.dp,
                                        if (isSel) PurplePrimary else BorderSubtle,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable { selectedSharingTab = opt.sharingType }
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = opt.title,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSel) PurpleDark else CharcoalDark
                                        )
                                    )
                                    Text(
                                        text = if (opt.hasAttachedWashroom) "Attached Washroom • ${opt.vacantBeds} beds vacant" else "Common Washroom • ${opt.vacantBeds} beds vacant",
                                        style = MaterialTheme.typography.bodySmall.copy(color = CharcoalLight)
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "₹${opt.rentPerBed}/mo",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = PurplePrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        text = "Deposit: ₹${opt.deposit}",
                                        style = MaterialTheme.typography.labelSmall.copy(color = CharcoalLight)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Amenities Full Grid
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Amenities & Facilities Included",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            property.amenities.forEach { am ->
                                AmenityChip(text = am, isSelected = true)
                            }
                        }
                    }
                }
            }

            // Timings & Rules
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Hostel Rules & Curfew Timings",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Curfew banner
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(PurpleTint.copy(alpha = 0.5f))
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = PurplePrimary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = property.timings,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = PurpleDark,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        property.rules.forEach { rule ->
                            Row(
                                modifier = Modifier.padding(vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = PurpleAccent, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = rule, style = MaterialTheme.typography.bodySmall.copy(color = CharcoalDark))
                            }
                        }
                    }
                }
            }

            // Security Information
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = VerifiedGreen, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Security & Safety Standards",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        property.securityDetails.forEach { sec ->
                            Row(
                                modifier = Modifier.padding(vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = VerifiedGreen, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = sec, style = MaterialTheme.typography.bodySmall.copy(color = CharcoalDark))
                            }
                        }
                    }
                }
            }

            // Owner / Manager Contact Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Managed By",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(CircleShape)
                                        .background(PurpleTint),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = property.ownerName.take(1),
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = PurplePrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = property.ownerName,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                        )
                                        if (property.ownerVerified) {
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Icon(Icons.Default.Verified, contentDescription = null, tint = VerifiedGreen, modifier = Modifier.size(14.dp))
                                        }
                                    }
                                    Text(
                                        text = "${property.ownerRole} • ${property.ownerPhone}",
                                        style = MaterialTheme.typography.bodySmall.copy(color = CharcoalLight)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // In-app chat trigger
                        Button(
                            onClick = { onChatClick(property.id) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = PurpleCardBg),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Send, contentDescription = null, tint = PurplePrimary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Message Owner on Stayora",
                                color = PurplePrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Student Reviews Section
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Student Reviews",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = StarGold, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${property.rating} (${property.reviewCount})",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        property.reviews.forEach { rev ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFF8FAFC))
                                    .padding(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(text = rev.studentName, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                                        Text(text = rev.studentCollege, style = MaterialTheme.typography.labelSmall.copy(color = CharcoalLight))
                                    }
                                    Text(text = "⭐ ${rev.rating}", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = StarGold))
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = "\"${rev.comment}\"", style = MaterialTheme.typography.bodySmall.copy(color = CharcoalDark))
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
