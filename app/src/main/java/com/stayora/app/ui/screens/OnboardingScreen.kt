package com.stayora.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stayora.app.data.model.UserPreferences
import com.stayora.app.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OnboardingScreen(
    currentPreferences: UserPreferences,
    onComplete: (UserPreferences) -> Unit,
    onSkip: () -> Unit
) {
    var step by remember { mutableStateOf(1) } // 3 steps

    // Step 1: College & Branch & Roommate Type
    var college by remember { mutableStateOf(currentPreferences.college) }
    var branch by remember { mutableStateOf(currentPreferences.branch) }
    var roommateType by remember { mutableStateOf(currentPreferences.roommateTypePreference) }

    // Step 2: Personality & Lifestyle
    var selectedPersonalities by remember { mutableStateOf(currentPreferences.personalityPreferences.toSet()) }

    // Step 3: Budget, Distance, Sharing & Amenities
    var budgetRange by remember { mutableStateOf(currentPreferences.minBudget.toFloat()..currentPreferences.maxBudget.toFloat()) }
    var maxDistance by remember { mutableFloatStateOf(currentPreferences.maxDistanceKm.toFloat()) }
    var preferredSharing by remember { mutableIntStateOf(currentPreferences.preferredSharing) }
    var selectedAmenities by remember { mutableStateOf(currentPreferences.requiredAmenities.toSet()) }
    var moveInDate by remember { mutableStateOf(currentPreferences.moveInDate) }

    val collegesList = listOf(
        "Malla Reddy University (MRUH)",
        "Malla Reddy Engg College (MREC)",
        "MREC for Women",
        "MLR Institute of Technology",
        "JNTU Hyderabad",
        "CMR Technical Campus"
    )

    val branchesList = listOf(
        "B.Tech CSE",
        "B.Tech AI & Data Science",
        "B.Tech ECE",
        "B.Tech IT",
        "B.Tech Mechanical",
        "MBA / Business",
        "B.Pharmacy"
    )

    val roommateTypeOptions = listOf(
        "Same College",
        "Same Branch",
        "Same Village / Hometown",
        "No Preference"
    )

    val personalityOptions = listOf(
        "Introvert",
        "Extrovert",
        "Travel Enthusiast",
        "Early Riser",
        "Night Owl",
        "Quiet / Study-focused",
        "Fitness Enthusiast",
        "No Preference"
    )

    val amenityOptions = listOf(
        "Wi-Fi",
        "Washing Machine",
        "24-Hour Water",
        "Security / CCTV",
        "Meals / Food",
        "Two-Wheeler Parking",
        "Air Conditioning (AC)",
        "TV Lounge",
        "Power Backup"
    )

    val moveInOptions = listOf(
        "Immediate (This Week)",
        "Within 2 Weeks",
        "Next Month"
    )

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "STAYORA",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = PurplePrimary,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 2.sp
                            )
                        )
                        Text(
                            text = "Step $step of 3 • Customise Your Stay",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = CharcoalLight
                            )
                        )
                    }

                    TextButton(onClick = onSkip) {
                        Text(
                            text = "Skip",
                            color = PurplePrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Step progress bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (i in 1..3) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(5.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(if (i <= step) PurplePrimary else PurpleTint)
                        )
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                color = Color.White,
                tonalElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (step > 1) {
                        OutlinedButton(
                            onClick = { step-- },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Back", color = PurpleDark)
                        }
                    } else {
                        Spacer(modifier = Modifier.width(1.dp))
                    }

                    Button(
                        onClick = {
                            if (step < 3) {
                                step++
                            } else {
                                onComplete(
                                    UserPreferences(
                                        college = college,
                                        branch = branch,
                                        roommateTypePreference = roommateType,
                                        personalityPreferences = selectedPersonalities.toList(),
                                        minBudget = budgetRange.start.toInt(),
                                        maxBudget = budgetRange.endInclusive.toInt(),
                                        maxDistanceKm = maxDistance.toDouble(),
                                        preferredSharing = preferredSharing,
                                        requiredAmenities = selectedAmenities.toList(),
                                        moveInDate = moveInDate
                                    )
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.defaultMinSize(minWidth = 140.dp)
                    ) {
                        Text(
                            text = if (step == 3) "Find My Stay ✨" else "Continue →",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
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
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            when (step) {
                1 -> {
                    item {
                        Text(
                            text = "Where are you studying?",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = CharcoalDark,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "We will find verified hostels with shortest commute times to your campus.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = CharcoalLight
                            )
                        )
                    }

                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Select College / University",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                collegesList.forEach { col ->
                                    val isSelected = college == col
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(if (isSelected) PurpleTint else Color(0xFFF8FAFC))
                                            .clickable { college = col }
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = isSelected,
                                            onClick = { college = col },
                                            colors = RadioButtonDefaults.colors(selectedColor = PurplePrimary)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = col,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                color = CharcoalDark
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Your Branch / Degree",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    branchesList.forEach { br ->
                                        val isSel = branch == br
                                        FilterChip(
                                            selected = isSel,
                                            onClick = { branch = br },
                                            label = { Text(br) },
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = PurplePrimary,
                                                selectedLabelColor = Color.White
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Preferred Roommate Connection",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                roommateTypeOptions.forEach { opt ->
                                    val isSelected = roommateType == opt
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(if (isSelected) PurpleTint else Color(0xFFF8FAFC))
                                            .clickable { roommateType = opt }
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = isSelected,
                                            onClick = { roommateType = opt },
                                            colors = RadioButtonDefaults.colors(selectedColor = PurplePrimary)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = opt)
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> {
                    item {
                        Text(
                            text = "Roommate Compatibility",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = CharcoalDark,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Select what personality traits and habits you vibe best with.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = CharcoalLight
                            )
                        )
                    }

                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Personality Preferences (Pick all that apply)",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    personalityOptions.forEach { trait ->
                                        val isSelected = selectedPersonalities.contains(trait)
                                        FilterChip(
                                            selected = isSelected,
                                            onClick = {
                                                selectedPersonalities = if (isSelected) {
                                                    selectedPersonalities - trait
                                                } else {
                                                    selectedPersonalities + trait
                                                }
                                            },
                                            label = { Text(trait) },
                                            leadingIcon = if (isSelected) {
                                                { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                                            } else null,
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = PurplePrimary,
                                                selectedLabelColor = Color.White
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = PurpleCardBg),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = PurplePrimary,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Stayora Smart Match AI",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            color = PurpleDark,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        text = "Our algorithm pairs you with students who have compatible study hours and sleep patterns.",
                                        style = MaterialTheme.typography.bodySmall.copy(color = CharcoalMedium)
                                    )
                                }
                            }
                        }
                    }
                }

                3 -> {
                    item {
                        Text(
                            text = "Budget & Accommodation",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = CharcoalDark,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Set your rent boundaries and mandatory hostel amenities.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = CharcoalLight
                            )
                        )
                    }

                    // Budget Slider
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Monthly Rent Budget",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = "₹${budgetRange.start.toInt()} - ₹${budgetRange.endInclusive.toInt()}",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            color = PurplePrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                                RangeSlider(
                                    value = budgetRange,
                                    onValueChange = { budgetRange = it },
                                    valueRange = 3000f..15000f,
                                    steps = 23,
                                    colors = SliderDefaults.colors(
                                        thumbColor = PurplePrimary,
                                        activeTrackColor = PurplePrimary,
                                        inactiveTrackColor = PurpleTint
                                    )
                                )
                            }
                        }
                    }

                    // Distance Slider
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Max Distance from Campus",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = "${String.format("%.1f", maxDistance)} km",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            color = PurplePrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                                Slider(
                                    value = maxDistance,
                                    onValueChange = { maxDistance = it },
                                    valueRange = 0.5f..8.0f,
                                    colors = SliderDefaults.colors(
                                        thumbColor = PurplePrimary,
                                        activeTrackColor = PurplePrimary,
                                        inactiveTrackColor = PurpleTint
                                    )
                                )
                            }
                        }
                    }

                    // Sharing Options (2 or 4 sharing)
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Preferred Sharing Type",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    listOf(2 to "2 Sharing (Double Bed)", 4 to "4 Sharing (Budget)").forEach { (sharingNum, label) ->
                                        val isSel = preferredSharing == sharingNum
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(if (isSel) PurpleTint else Color(0xFFF8FAFC))
                                                .border(
                                                    1.5.dp,
                                                    if (isSel) PurplePrimary else BorderSubtle,
                                                    RoundedCornerShape(12.dp)
                                                )
                                                .clickable { preferredSharing = sharingNum }
                                                .padding(14.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = label,
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                                    color = if (isSel) PurpleDark else CharcoalDark,
                                                    fontSize = 12.sp
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Required Amenities
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Mandatory Amenities",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    amenityOptions.forEach { amenity ->
                                        val isSel = selectedAmenities.contains(amenity)
                                        FilterChip(
                                            selected = isSel,
                                            onClick = {
                                                selectedAmenities = if (isSel) {
                                                    selectedAmenities - amenity
                                                } else {
                                                    selectedAmenities + amenity
                                                }
                                            },
                                            label = { Text(amenity) },
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = PurplePrimary,
                                                selectedLabelColor = Color.White
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Move-in Date
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Move-In Timeline",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    moveInOptions.forEach { opt ->
                                        val isSel = moveInDate == opt
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(if (isSel) PurplePrimary else Color(0xFFF8FAFC))
                                                .clickable { moveInDate = opt }
                                                .padding(vertical = 10.dp, horizontal = 4.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = opt,
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    color = if (isSel) Color.White else CharcoalDark,
                                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                                    fontSize = 11.sp
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
}
