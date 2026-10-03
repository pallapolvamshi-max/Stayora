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
    onConnectRoommate: (RoommateProfile) -> Unit
) {
    var connectedProfiles by remember { mutableStateOf(setOf<String>()) }

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
                            text = "Matches for ${preferences.college} • ${preferences.branch}",
                            style = MaterialTheme.typography.bodySmall.copy(color = CharcoalLight)
                        )
                    }

                    CompatibilityBadge(score = 96, label = "Top Match")
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
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = PurplePrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Compatibility scores are calculated using your sleep schedule, study discipline, hometown, and personality choices.",
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
                                Text("Sharing", style = MaterialTheme.typography.labelSmall.copy(color = CharcoalLight))
                                Text("${profile.preferredSharing}-Sharing", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            }
                            Column {
                                Text("Hometown", style = MaterialTheme.typography.labelSmall.copy(color = CharcoalLight))
                                Text(profile.hometown.substringBefore("/"), style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                connectedProfiles = if (isConnected) connectedProfiles - profile.id else connectedProfiles + profile.id
                                onConnectRoommate(profile)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isConnected) VerifiedGreen else PurplePrimary
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = if (isConnected) Icons.Default.Check else Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isConnected) "Request Sent • Connected" else "Connect as Roommate")
                        }
                    }
                }
            }
        }
    }
}
