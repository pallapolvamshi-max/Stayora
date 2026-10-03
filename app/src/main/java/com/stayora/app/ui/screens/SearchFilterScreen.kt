package com.stayora.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.stayora.app.data.model.*
import com.stayora.app.ui.components.PropertyCard
import com.stayora.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchFilterScreen(
    properties: List<Property>,
    compareList: List<Property>,
    onPropertyClick: (String) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    onCompareToggle: (Property) -> Unit,
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var showFilterSheet by remember { mutableStateOf(false) }

    // Filter states
    var budgetRange by remember { mutableStateOf(3000f..15000f) }
    var maxDistance by remember { mutableFloatStateOf(6.0f) }
    var selectedGender by remember { mutableStateOf("All") } // All, Boys, Girls, Co-ed
    var selectedSharing by remember { mutableStateOf(setOf<Int>()) } // 2, 4
    var selectedAmenities by remember { mutableStateOf(setOf<String>()) }
    var minRating by remember { mutableFloatStateOf(0.0f) }
    var onlyAvailable by remember { mutableStateOf(false) }
    var sortBy by remember { mutableStateOf(SortOption.RECOMMENDED) }

    val amenityOptions = listOf(
        "Wi-Fi",
        "Washing Machine",
        "24/7 Water",
        "Meals",
        "Security",
        "Parking",
        "AC",
        "Power Backup"
    )

    // Compute filtered list
    val filteredProperties = remember(
        properties, searchQuery, budgetRange, maxDistance,
        selectedGender, selectedSharing, selectedAmenities, minRating, onlyAvailable, sortBy
    ) {
        properties.filter { p ->
            // Search text
            val matchesText = searchQuery.isBlank() ||
                    p.title.contains(searchQuery, ignoreCase = true) ||
                    p.address.contains(searchQuery, ignoreCase = true) ||
                    p.amenities.any { it.contains(searchQuery, ignoreCase = true) }

            // Budget
            val matchesBudget = p.monthlyRent in budgetRange.start.toInt()..budgetRange.endInclusive.toInt()

            // Distance
            val matchesDistance = p.distanceKm <= maxDistance

            // Gender
            val matchesGender = when (selectedGender) {
                "Boys" -> p.type == PropertyType.BOYS_HOSTEL
                "Girls" -> p.type == PropertyType.GIRLS_HOSTEL
                "Co-ed" -> p.type == PropertyType.COED_PG
                else -> true
            }

            // Sharing
            val matchesSharing = selectedSharing.isEmpty() ||
                    p.sharingOptions.any { opt -> selectedSharing.contains(opt.sharingType) }

            // Amenities
            val matchesAmenities = selectedAmenities.isEmpty() ||
                    selectedAmenities.all { req -> p.amenities.any { it.contains(req, ignoreCase = true) } }

            // Rating
            val matchesRating = p.rating >= minRating

            // Availability
            val matchesAvailability = !onlyAvailable || p.availabilityStatus.contains("Available", ignoreCase = true)

            matchesText && matchesBudget && matchesDistance && matchesGender &&
                    matchesSharing && matchesAmenities && matchesRating && matchesAvailability
        }.let { list ->
            when (sortBy) {
                SortOption.RECOMMENDED -> list.sortedByDescending { it.matchScore }
                SortOption.RENT_LOW_HIGH -> list.sortedBy { it.monthlyRent }
                SortOption.RENT_HIGH_LOW -> list.sortedByDescending { it.monthlyRent }
                SortOption.DISTANCE -> list.sortedBy { it.distanceKm }
                SortOption.RATING -> list.sortedByDescending { it.rating }
            }
        }
    }

    val activeFiltersCount = (if (selectedGender != "All") 1 else 0) +
            (if (selectedSharing.isNotEmpty()) 1 else 0) +
            selectedAmenities.size +
            (if (minRating > 0f) 1 else 0) +
            (if (onlyAvailable) 1 else 0) +
            (if (budgetRange.start > 3000f || budgetRange.endInclusive < 15000f) 1 else 0)

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

                    // Search text field
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        placeholder = { Text("Hostel name, area, facility...", fontSize = 13.sp) },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = PurplePrimary, modifier = Modifier.size(20.dp))
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Close, contentDescription = "Clear", modifier = Modifier.size(18.dp))
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PurplePrimary,
                            unfocusedBorderColor = BorderSubtle
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Filter sheet trigger with badge
                    BadgedBox(badge = {
                        if (activeFiltersCount > 0) {
                            Badge(
                                containerColor = PurplePrimary,
                                contentColor = Color.White
                            ) {
                                Text("$activeFiltersCount")
                            }
                        }
                    }) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (activeFiltersCount > 0) PurplePrimary else PurpleTint)
                                .clickable { showFilterSheet = true }
                                .padding(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = "Filters",
                                tint = if (activeFiltersCount > 0) Color.White else PurpleDark,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Sort and Results Count Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${filteredProperties.size} properties match",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = CharcoalLight,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    // Sort selector chip
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(PurpleTint.copy(alpha = 0.5f))
                            .clickable {
                                sortBy = when (sortBy) {
                                    SortOption.RECOMMENDED -> SortOption.RENT_LOW_HIGH
                                    SortOption.RENT_LOW_HIGH -> SortOption.DISTANCE
                                    SortOption.DISTANCE -> SortOption.RATING
                                    SortOption.RATING -> SortOption.RENT_HIGH_LOW
                                    SortOption.RENT_HIGH_LOW -> SortOption.RECOMMENDED
                                }
                            }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            tint = PurplePrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = sortBy.label,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = PurpleDark,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(GrayUltraLight)
                .padding(paddingValues)
        ) {
            if (filteredProperties.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = GrayMuted,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No hostels found matching your criteria",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Try increasing your budget range or removing some amenity filters.",
                        style = MaterialTheme.typography.bodySmall.copy(color = CharcoalLight)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            searchQuery = ""
                            budgetRange = 3000f..15000f
                            maxDistance = 6.0f
                            selectedGender = "All"
                            selectedSharing = emptySet()
                            selectedAmenities = emptySet()
                            minRating = 0f
                            onlyAvailable = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
                    ) {
                        Text("Reset All Filters")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(filteredProperties) { property ->
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

            // Filter Bottom Sheet Dialog
            if (showFilterSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showFilterSheet = false },
                    containerColor = Color.White
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 32.dp),
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Filter Accommodations",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = CharcoalDark
                                    )
                                )
                                TextButton(onClick = {
                                    budgetRange = 3000f..15000f
                                    maxDistance = 6.0f
                                    selectedGender = "All"
                                    selectedSharing = emptySet()
                                    selectedAmenities = emptySet()
                                    minRating = 0f
                                    onlyAvailable = false
                                }) {
                                    Text("Reset", color = PurplePrimary)
                                }
                            }
                        }

                        // Gender filter
                        item {
                            Text("Resident Gender", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf("All", "Boys", "Girls", "Co-ed").forEach { g ->
                                    val isSel = selectedGender == g
                                    FilterChip(
                                        selected = isSel,
                                        onClick = { selectedGender = g },
                                        label = { Text(g) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = PurplePrimary,
                                            selectedLabelColor = Color.White
                                        )
                                    )
                                }
                            }
                        }

                        // Sharing Type (2 or 4 sharing)
                        item {
                            Text("Room Sharing Type", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf(1 to "Single", 2 to "2-Sharing", 3 to "3-Sharing", 4 to "4-Sharing").forEach { (num, label) ->
                                    val isSel = selectedSharing.contains(num)
                                    FilterChip(
                                        selected = isSel,
                                        onClick = {
                                            selectedSharing = if (isSel) selectedSharing - num else selectedSharing + num
                                        },
                                        label = { Text(label) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = PurplePrimary,
                                            selectedLabelColor = Color.White
                                        )
                                    )
                                }
                            }
                        }

                        // Budget Range
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Monthly Rent Range", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                                Text(
                                    "₹${budgetRange.start.toInt()} - ₹${budgetRange.endInclusive.toInt()}",
                                    style = MaterialTheme.typography.titleSmall.copy(color = PurplePrimary, fontWeight = FontWeight.Bold)
                                )
                            }
                            RangeSlider(
                                value = budgetRange,
                                onValueChange = { budgetRange = it },
                                valueRange = 3000f..15000f,
                                colors = SliderDefaults.colors(
                                    thumbColor = PurplePrimary,
                                    activeTrackColor = PurplePrimary
                                )
                            )
                        }

                        // Distance
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Max Distance from College", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                                Text(
                                    "${String.format("%.1f", maxDistance)} km",
                                    style = MaterialTheme.typography.titleSmall.copy(color = PurplePrimary, fontWeight = FontWeight.Bold)
                                )
                            }
                            Slider(
                                value = maxDistance,
                                onValueChange = { maxDistance = it },
                                valueRange = 0.5f..10.0f,
                                colors = SliderDefaults.colors(
                                    thumbColor = PurplePrimary,
                                    activeTrackColor = PurplePrimary
                                )
                            )
                        }

                        // Amenities
                        item {
                            Text("Amenities Required", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                            Spacer(modifier = Modifier.height(8.dp))
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                amenityOptions.forEach { am ->
                                    val isSel = selectedAmenities.contains(am)
                                    FilterChip(
                                        selected = isSel,
                                        onClick = {
                                            selectedAmenities = if (isSel) selectedAmenities - am else selectedAmenities + am
                                        },
                                        label = { Text(am) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = PurplePrimary,
                                            selectedLabelColor = Color.White
                                        )
                                    )
                                }
                            }
                        }

                        // Minimum Rating
                        item {
                            Text("Minimum Rating", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf(0.0f to "Any", 4.0f to "4.0+ ⭐", 4.5f to "4.5+ ⭐", 4.8f to "4.8+ ⭐").forEach { (r, label) ->
                                    val isSel = minRating == r
                                    FilterChip(
                                        selected = isSel,
                                        onClick = { minRating = r },
                                        label = { Text(label) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = PurplePrimary,
                                            selectedLabelColor = Color.White
                                        )
                                    )
                                }
                            }
                        }

                        // Vacant only toggle
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Only show verified vacant beds", style = MaterialTheme.typography.bodyMedium)
                                Switch(
                                    checked = onlyAvailable,
                                    onCheckedChange = { onlyAvailable = it },
                                    colors = SwitchDefaults.colors(checkedThumbColor = PurplePrimary)
                                )
                            }
                        }

                        // Apply Button
                        item {
                            Button(
                                onClick = { showFilterSheet = false },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    "Show ${filteredProperties.size} Matching Hostels",
                                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White, fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
