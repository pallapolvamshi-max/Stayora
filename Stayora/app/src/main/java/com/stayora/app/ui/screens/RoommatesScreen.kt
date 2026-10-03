package com.stayora.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stayora.app.data.model.RoommateProfile
import com.stayora.app.data.model.UserPreferences
import com.stayora.app.ui.components.CompatibilityBadge
import com.stayora.app.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RoommatesScreen(
    roommates: List<RoommateProfile>,
    preferences: UserPreferences,
    onConnectRoommate: (RoommateProfile) -> Unit,
    onChatRoommate: (RoommateProfile) -> Unit = {}
) {
    var connectedProfiles by remember { mutableStateOf(setOf<String>()) }
    var selectedProfileForComparison by remember { mutableStateOf<RoommateProfile?>(null) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Roommate Compatibility",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = CharcoalDark
                            )
                        )
                        Text(
                            text = "Matches for ${preferences.userName} • ${preferences.college}",
                            style = MaterialTheme.typography.bodySmall.copy(color = CharcoalLight)
                        )
                    }

                    CompatibilityBadge(score = roommates.firstOrNull()?.compatibilityScore ?: 96, label = "Top Match")
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
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = PurpleCardBg)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = PurplePrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Compatibility scores are calculated against your college (${preferences.college}), target area (${preferences.preferredLocation}), and sleep habits.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = PurpleDark,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }

            items(roommates) { profile ->
                val isConnected = connectedProfiles.contains(profile.id)

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
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(PurpleTint),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = profile.name.take(1),
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
                                            text = profile.name,
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                        )
                                        if (profile.isVerifiedStudent) {
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Icon(
                                                Icons.Default.CheckCircle,
                                                contentDescription = "Verified Student",
                                                tint = VerifiedGreen,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = "${profile.branch} • ${profile.college}",
                                        style = MaterialTheme.typography.bodySmall.copy(color = CharcoalLight)
                                    )
                                }
                            }

                            CompatibilityBadge(score = profile.compatibilityScore)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "\"${profile.bio}\"",
                            style = MaterialTheme.typography.bodySmall.copy(color = CharcoalDark)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Personality Tags
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            profile.personalityTraits.forEach { trait ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(PurpleTint.copy(alpha = 0.6f))
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                    ) {
                                    Text(
                                        text = trait,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = PurpleDark,
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Habits Details Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFF8FAFC))
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Sleep Timing", style = MaterialTheme.typography.labelSmall.copy(color = CharcoalLight))
                                Text(profile.sleepSchedule.substringBefore("("), style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            }
                            Column {
                                Text("Location", style = MaterialTheme.typography.labelSmall.copy(color = CharcoalLight))
                                Text(profile.targetLocation, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            }
                            Column {
                                Text("Hometown", style = MaterialTheme.typography.labelSmall.copy(color = CharcoalLight))
                                Text(profile.hometown.substringBefore("/"), style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Action Buttons: Compare, Chat, Connect
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = { selectedProfileForComparison = profile },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(vertical = 8.dp)
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp), tint = PurplePrimary)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Compare", style = MaterialTheme.typography.bodySmall.copy(color = PurplePrimary, fontWeight = FontWeight.SemiBold))
                            }

                            Button(
                                onClick = { onChatRoommate(profile) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(vertical = 8.dp)
                            ) {
                                Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Chat", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            }

                            IconButton(
                                onClick = {
                                    connectedProfiles = if (isConnected) connectedProfiles - profile.id else connectedProfiles + profile.id
                                    onConnectRoommate(profile)
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isConnected) VerifiedGreen else PurpleTint)
                            ) {
                                Icon(
                                    imageVector = if (isConnected) Icons.Default.Check else Icons.Default.Add,
                                    contentDescription = "Connect",
                                    tint = if (isConnected) Color.White else PurplePrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Compare with Me Dialog
    if (selectedProfileForComparison != null) {
        val target = selectedProfileForComparison!!
        AlertDialog(
            onDismissRequest = { selectedProfileForComparison = null },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Compatibility Analysis",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    CompatibilityBadge(score = target.compatibilityScore)
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Comparing You (${preferences.userName}) with ${target.name}",
                        style = MaterialTheme.typography.bodySmall.copy(color = CharcoalLight)
                    )

                    HorizontalDivider()

                    ComparisonRowItem(
                        label = "College",
                        youVal = preferences.college.take(18) + "..",
                        themVal = target.college.take(18) + "..",
                        isMatch = target.college.contains("Malla Reddy", ignoreCase = true)
                    )

                    ComparisonRowItem(
                        label = "Preferred Area",
                        youVal = preferences.preferredLocation,
                        themVal = target.targetLocation,
                        isMatch = target.targetLocation.equals(preferences.preferredLocation, ignoreCase = true)
                    )

                    ComparisonRowItem(
                        label = "Branch",
                        youVal = preferences.branch.take(14),
                        themVal = target.branch.take(14),
                        isMatch = target.branch.contains(preferences.branch, ignoreCase = true) || preferences.branch.contains(target.branch, ignoreCase = true)
                    )

                    ComparisonRowItem(
                        label = "Year of Study",
                        youVal = preferences.yearOfStudy,
                        themVal = target.yearOfStudy,
                        isMatch = preferences.yearOfStudy == target.yearOfStudy
                    )

                    ComparisonRowItem(
                        label = "Hometown",
                        youVal = preferences.hometown,
                        themVal = target.hometown.substringBefore("/").trim(),
                        isMatch = target.hometown.contains(preferences.hometown, ignoreCase = true)
                    )

                    ComparisonRowItem(
                        label = "Sharing",
                        youVal = "${preferences.preferredSharing}-Sharing",
                        themVal = "${target.preferredSharing}-Sharing",
                        isMatch = preferences.preferredSharing == target.preferredSharing
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val p = selectedProfileForComparison
                        selectedProfileForComparison = null
                        if (p != null) onChatRoommate(p)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Chat with ${target.name.substringBefore(" ")}")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { selectedProfileForComparison = null },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
private fun ComparisonRowItem(
    label: String,
    youVal: String,
    themVal: String,
    isMatch: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (isMatch) PurpleTint.copy(alpha = 0.5f) else Color(0xFFF8FAFC))
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.labelSmall.copy(color = CharcoalLight, fontSize = 11.sp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(youVal, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium))
                Text(" ↔ ", color = GrayMuted)
                Text(
                    themVal,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (isMatch) PurpleDark else CharcoalDark
                    )
                )
            }
        }
        if (isMatch) {
            Icon(Icons.Default.CheckCircle, contentDescription = "Matched", tint = VerifiedGreen, modifier = Modifier.size(16.dp))
        }
    }
}
