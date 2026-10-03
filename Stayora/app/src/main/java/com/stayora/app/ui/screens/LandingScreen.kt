package com.stayora.app.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.stayora.app.ui.theme.*

@Composable
fun LandingScreen(
    onGetStarted: () -> Unit,
    onExploreDirectly: () -> Unit
) {
    val context = LocalContext.current

    // --- Stitch Design Theme Colors (Deep Purple, Lavender, Coral) ---
    val purpleDark = Color(0xFF14032F)
    val purpleDeep = Color(0xFF250065)
    val purpleContainer = Color(0xFF3B1E7E)
    val violetAccent = Color(0xFF6B38D4)
    val lavenderPrimary = Color(0xFF8B5CF6)
    val lavenderLight = Color(0xFFEDE9FE)
    val lavenderBorder = Color(0xFFE8DCFC)
    val coralAccent = Color(0xFFFF6B4A)
    val coralLight = Color(0xFFFFE8E3)
    val surfaceBg = Color(0xFFFAF7FF)

    // --- Interactive Preferences State ---
    var budgetValue by remember { mutableFloatStateOf(7000f) }
    var selectedDistance by remember { mutableStateOf("under1km") }
    var selectedAmenities by remember {
        mutableStateOf(setOf("Fast Wi-Fi", "3-Time Food", "Power Backup"))
    }
    var sleepHabit by remember { mutableStateOf("Night Owl (2 AM)") }
    var studyHabit by remember { mutableStateOf("Study-Focused") }
    var likedRoommates by remember { mutableStateOf(mapOf<String, Boolean>()) }
    var selectedHostelTab by remember { mutableStateOf("all") } // "all", "boys", "girls"

    // Dynamic Live Compatibility Score Calculation
    val dynamicScore = remember(budgetValue, selectedDistance, selectedAmenities, sleepHabit, studyHabit) {
        var base = 76
        if (budgetValue in 6000f..8500f) base += 8
        if (selectedDistance == "under1km") base += 7
        if (selectedAmenities.size >= 3) base += 4
        if (sleepHabit.contains("Night")) base += 3
        base.coerceAtMost(99)
    }

    // Gentle pulse animation for verified badges
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Scaffold(
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 16.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = onGetStarted,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = purpleDeep),
                        shape = RoundedCornerShape(26.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Text(
                            text = "Find Your Vibe & Stay ✨",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = onExploreDirectly) {
                            Text(
                                text = "Browse Hostels as Guest",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = lavenderPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        TextButton(onClick = {
                            try {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("http://10.0.2.2:8085/")
                                )
                                context.startActivity(intent)
                            } catch (_: Exception) {}
                        }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Language,
                                    contentDescription = null,
                                    tint = coralAccent,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Web Showcase",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = coralAccent,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(surfaceBg)
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {

            // ==========================================
            // 1. TOP NAVBAR / BRAND HEADER BAR
            // ==========================================
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(purpleDeep, purpleContainer, violetAccent)
                                    )
                                )
                                .shadow(4.dp, RoundedCornerShape(14.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Stayora Logo",
                                tint = coralAccent,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Stay",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        color = purpleDeep,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 22.sp
                                    )
                                )
                                Text(
                                    text = "ora",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        color = coralAccent,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 22.sp
                                    )
                                )
                            }
                            Text(
                                text = "CAMPUS LIVING",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = violetAccent,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.5.sp,
                                    fontSize = 9.sp
                                )
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .scale(pulseScale)
                            .clip(RoundedCornerShape(20.dp))
                            .background(lavenderLight)
                            .border(1.dp, lavenderBorder, RoundedCornerShape(20.dp))
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF10B981))
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "Campus Verified",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = purpleDeep,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }

            // ==========================================
            // 2. SOCIAL PROOF PILL
            // ==========================================
            item {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .border(1.dp, lavenderBorder, RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 7.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFF59E0B),
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "4.9/5",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = purpleDark
                            )
                        )
                        Text(
                            text = "•",
                            color = Color.LightGray
                        )
                        Text(
                            text = "4,800+ Students across Maisammaguda Campuses",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = CharcoalLight,
                                fontWeight = FontWeight.Medium
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            // ==========================================
            // 3. HERO CATCHY HEADLINE
            // ==========================================
            item {
                Column {
                    Text(
                        text = "Find Your Space.\nFind Your People. ✨",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = purpleDark,
                            lineHeight = 38.sp,
                            fontSize = 30.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Discover verified student hostels, PGs, rooms, and compatible roommates near Malla Reddy campuses with zero brokerage and true vibe matching.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = CharcoalLight,
                            lineHeight = 22.sp
                        )
                    )
                }
            }

            // ==========================================
            // 4. ANIMATED SHOWCASE MOCKUP CARD
            // ==========================================
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                    shape = RoundedCornerShape(26.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        AsyncImage(
                            model = "https://images.unsplash.com/photo-1555854877-bab0e564b8d5?auto=format&fit=crop&w=800&q=80",
                            contentDescription = "Sri Sai Nilayam Luxury PG",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.Black.copy(alpha = 0.55f),
                                            Color.Black.copy(alpha = 0.35f),
                                            Color.Black.copy(alpha = 0.85f)
                                        )
                                    )
                                )
                                .padding(16.dp)
                        ) {
                        // Top Left: Vibe Match Pill
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color.White.copy(alpha = 0.95f))
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = coralAccent,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "98% Roommate Match",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = purpleDeep,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        // Top Right: 0% Brokerage Chip
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .clip(RoundedCornerShape(14.dp))
                                .background(coralAccent)
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "₹0 Brokerage",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 11.sp
                                )
                            )
                        }

                        // Center: Live Property Highlight
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(top = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Sri Sai Nilayam Luxury PG",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 17.sp
                                )
                            )
                            Text(
                                text = "₹6,800/mo • 0.4 km to MRU Gate",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }

                        // Bottom Trust Card
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White.copy(alpha = 0.96f))
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = null,
                                        tint = VerifiedGreen,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column {
                                        Text(
                                            text = "100% Physical Warden Verified",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = purpleDark
                                            )
                                        )
                                        Text(
                                            text = "Biometric • RO Water • CCTV Checked",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = CharcoalLight,
                                                fontSize = 10.sp
                                            )
                                        )
                                    }
                                }

                                Text(
                                    text = "★ 4.9",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = Color(0xFFD97706),
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

            // ==========================================
            // 5. FLOATING CAMPUS PROXIMITY BADGES
            // ==========================================
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        CampusChip(
                            icon = Icons.Default.School,
                            title = "MRU Campus",
                            subtitle = "0.4 km proximity",
                            tint = purpleDeep,
                            bg = lavenderLight
                        )
                    }
                    item {
                        CampusChip(
                            icon = Icons.Default.Place,
                            title = "Maisammaguda Hub",
                            subtitle = "45+ Hostels & Cafes",
                            tint = coralAccent,
                            bg = coralLight
                        )
                    }
                    item {
                        CampusChip(
                            icon = Icons.Default.DirectionsBus,
                            title = "Bus Depot",
                            subtitle = "0.3 km to TSRTC",
                            tint = Color(0xFF0369A1),
                            bg = Color(0xFFE0F2FE)
                        )
                    }
                    item {
                        CampusChip(
                            icon = Icons.Default.LocalHospital,
                            title = "Narayana Hospital",
                            subtitle = "0.5 km 24/7 Trauma",
                            tint = Color(0xFFE11D48),
                            bg = Color(0xFFFFE4E6)
                        )
                    }
                }
            }

            // ==========================================
            // 6. INTERACTIVE PREFERENCE & COMPATIBILITY ENGINE
            // ==========================================
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(lavenderBorder, lavenderLight)))
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Header with Live Harmony Badge
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Your Preferences",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Black,
                                        color = purpleDark
                                    )
                                )
                                Text(
                                    text = "Live Match Harmony Engine",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = violetAccent,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }

                            // Dynamic Harmony Pill
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(coralLight)
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Whatshot,
                                        contentDescription = null,
                                        tint = coralAccent,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "$dynamicScore% Match",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = coralAccent,
                                            fontWeight = FontWeight.Black
                                        )
                                    )
                                }
                            }
                        }

                        // Progress Meter
                        LinearProgressIndicator(
                            progress = { dynamicScore / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = coralAccent,
                            trackColor = lavenderLight
                        )

                        // 1. Budget Slider
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Max Monthly Budget",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = purpleDark
                                    )
                                )
                                Text(
                                    text = "₹${budgetValue.toInt().coerceIn(4000, 14000)}/mo",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Black,
                                        color = purpleDeep
                                    )
                                )
                            }
                            Slider(
                                value = budgetValue,
                                onValueChange = { budgetValue = it },
                                valueRange = 4000f..14000f,
                                steps = 20,
                                colors = SliderDefaults.colors(
                                    thumbColor = coralAccent,
                                    activeTrackColor = purpleDeep,
                                    inactiveTrackColor = lavenderLight
                                )
                            )
                        }

                        // 2. Distance Selector
                        Column {
                            Text(
                                text = "Proximity to Campus",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = purpleDark
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf(
                                    "under1km" to "< 1 km (5m walk)",
                                    "under3km" to "1-3 km (Auto)",
                                    "under5km" to "3-5 km (Bus)"
                                ).forEach { (id, label) ->
                                    val isSelected = selectedDistance == id
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(if (isSelected) purpleDeep else lavenderLight)
                                            .clickable { selectedDistance = id }
                                            .padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = label,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = if (isSelected) Color.White else purpleDeep,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 10.sp
                                            ),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }

                        // 3. Amenity Toggle Chips
                        Column {
                            Text(
                                text = "Essential Amenities",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = purpleDark
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                listOf(
                                    "Fast Wi-Fi",
                                    "3-Time Food",
                                    "AC Room",
                                    "Power Backup",
                                    "Biometric",
                                    "Laundry"
                                ).forEach { amenity ->
                                    val isSelected = selectedAmenities.contains(amenity)
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(if (isSelected) violetAccent else Color.White)
                                            .border(
                                                1.dp,
                                                if (isSelected) violetAccent else lavenderBorder,
                                                RoundedCornerShape(10.dp)
                                            )
                                            .clickable {
                                                selectedAmenities = if (isSelected) {
                                                    selectedAmenities - amenity
                                                } else {
                                                    selectedAmenities + amenity
                                                }
                                            }
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = (if (isSelected) "✓ " else "+ ") + amenity,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = if (isSelected) Color.White else purpleDark,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.sp
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        // 4. Roommate Habits Toggle
                        Column {
                            Text(
                                text = "Sleep & Routine Rhythm",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = purpleDark
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf(
                                    "Night Owl (2 AM)",
                                    "Early Riser (6 AM)"
                                ).forEach { habit ->
                                    val isSelected = sleepHabit == habit
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(if (isSelected) purpleDeep else lavenderLight)
                                            .clickable { sleepHabit = habit }
                                            .padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = habit,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = if (isSelected) Color.White else purpleDeep,
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
            }

            // ==========================================
            // 7. FEATURED HOSTELS & ROOM CARDS (BOYS & GIRLS TABS FOR JURY)
            // ==========================================
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Campus Hostels & PGs",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = purpleDark
                                )
                            )
                            Text(
                                text = "100% Verified with Lady/Gent Warden on premises",
                                style = MaterialTheme.typography.labelSmall.copy(color = CharcoalLight)
                            )
                        }

                        TextButton(onClick = onExploreDirectly) {
                            Text(
                                text = "View All (42)",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = lavenderPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    // Interactive Filter Chips: All, Boys Hostels, Girls Hostels
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            "all" to "✨ All Stays (10)",
                            "boys" to "👨 Boys Hostels (5)",
                            "girls" to "👩 Girls Hostels (5)"
                        ).forEach { (tabId, label) ->
                            val isSelected = selectedHostelTab == tabId
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSelected) purpleDeep else lavenderLight)
                                    .clickable { selectedHostelTab = tabId }
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isSelected) Color.White else purpleDeep,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            item {
                data class LandingHostelItem(
                    val name: String,
                    val subtitle: String,
                    val details: List<String>,
                    val imageUrl: String,
                    val isBoys: Boolean
                )

                val boysHostels = listOf(
                    LandingHostelItem(
                        "Stanza Living Greenfield Scholars PG",
                        "0.6 km to MRU Gate 2 • 4.8 ★",
                        listOf("₹6,800/mo", "Curfew: 10:30 PM", "3 Meals", "Biometric", "150 Mbps Wi-Fi", "Gym"),
                        "https://images.unsplash.com/photo-1555854877-bab0e564b8d5?auto=format&fit=crop&w=600&q=80",
                        true
                    ),
                    LandingHostelItem(
                        "Sri Sai Balaji Elite Residency & PG",
                        "1.1 km to Dulapally Junction • 4.6 ★",
                        listOf("₹5,500/mo", "Curfew: 10:00 PM", "Andhra Mess", "Gym", "Washing Machine"),
                        "https://images.unsplash.com/photo-1598928506311-c55ded91a20c?auto=format&fit=crop&w=600&q=80",
                        true
                    ),
                    LandingHostelItem(
                        "Sri Sai Nilayam Executive Boys PG",
                        "0.4 km to MRU Main Gate • 4.9 ★",
                        listOf("₹6,400/mo", "Curfew: 10:30 PM", "AC Rooms", "200 Mbps Fiber", "DG Backup"),
                        "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?auto=format&fit=crop&w=600&q=80",
                        true
                    ),
                    LandingHostelItem(
                        "Venkateshwara Royal Boys Hostel",
                        "0.7 km to Maisammaguda • 4.75 ★",
                        listOf("₹5,800/mo", "Curfew: 10:15 PM", "Homely Mess", "Bike Parking", "Solar Geyser"),
                        "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?auto=format&fit=crop&w=600&q=80",
                        true
                    ),
                    LandingHostelItem(
                        "Balaji Tech Scholars Boys Living",
                        "0.3 km to St. Martin's • 4.82 ★",
                        listOf("₹5,200/mo", "Curfew: 10:00 PM", "4-Time Food", "Study Reading Hall", "Fast Wi-Fi"),
                        "https://images.unsplash.com/photo-1513694203232-719a280e022f?auto=format&fit=crop&w=600&q=80",
                        true
                    )
                )

                val girlsHostels = listOf(
                    LandingHostelItem(
                        "Serene Oasis Luxury Girls PG & Hostel",
                        "0.8 km to MREC for Women • 4.9 ★",
                        listOf("₹7,200/mo", "Curfew: 9:30 PM Strict", "24/7 Lady Warden", "Biometric", "3 Meals + Snacks"),
                        "https://images.unsplash.com/photo-1595526114035-0d45ed16cfbf?auto=format&fit=crop&w=600&q=80",
                        false
                    ),
                    LandingHostelItem(
                        "Vaishnavi Elite Girls Residency",
                        "0.6 km to MLRIT & IARE • 4.95 ★",
                        listOf("₹7,900/mo", "Curfew: 9:30 PM Strict", "24/7 Lady Warden", "Full AC", "Attached Bath"),
                        "https://images.unsplash.com/photo-1616486338812-3dadae4b4ace?auto=format&fit=crop&w=600&q=80",
                        false
                    ),
                    LandingHostelItem(
                        "Sri Gayatri Grand Executive Girls PG",
                        "0.5 km to MRUH Main Gate • 4.88 ★",
                        listOf("₹7,500/mo", "Curfew: 9:00 PM", "24/7 Female Warden", "Biometric Gate", "Homely Mess"),
                        "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?auto=format&fit=crop&w=600&q=80",
                        false
                    ),
                    LandingHostelItem(
                        "Kompally Pearl Women's Living & PG",
                        "1.4 km to Cineplanet Kompally • 4.82 ★",
                        listOf("₹6,900/mo", "Curfew: 9:30 PM", "Elevator/Lift", "Solar Water", "24/7 Guard"),
                        "https://images.unsplash.com/photo-1586023492125-27b2c045efd7?auto=format&fit=crop&w=600&q=80",
                        false
                    ),
                    LandingHostelItem(
                        "Annapurna Divine Girls Hostel",
                        "0.4 km to MRCP Pharmacy Gate • 4.85 ★",
                        listOf("₹6,200/mo", "Curfew: 9:00 PM", "24/7 Lady Warden", "Hygienic Mess", "Study Balconies"),
                        "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?auto=format&fit=crop&w=600&q=80",
                        false
                    )
                )

                val displayedHostels = when (selectedHostelTab) {
                    "boys" -> boysHostels
                    "girls" -> girlsHostels
                    else -> (boysHostels + girlsHostels)
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(displayedHostels) { hostel ->
                        val rent = hostel.details[0]
                        val curfew = hostel.details[1]
                        val amenities = hostel.details.drop(2)

                        HostelShowcaseCard(
                            name = hostel.name,
                            tag = if (hostel.isBoys) "👨 Boys Hostel" else "👩 Girls Hostel (Lady Warden)",
                            rent = rent,
                            distance = hostel.subtitle,
                            rating = hostel.subtitle.substringAfterLast("• ").trim(),
                            curfew = curfew.substringAfter("Curfew: ").trim(),
                            imageUrl = hostel.imageUrl,
                            amenities = amenities,
                            badgeColor = if (hostel.isBoys) coralAccent else purpleDeep,
                            onClick = onExploreDirectly
                        )
                    }
                }
            }

            // ==========================================
            // 8. ROOMMATE-MATCH CARDS WITH MATCH %
            // ==========================================
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Roommate Compatibility",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = purpleDark
                            )
                        )
                        Text(
                            text = "Matched by sleep rhythm & study habits",
                            style = MaterialTheme.typography.labelSmall.copy(color = CharcoalLight)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(coralLight)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "AI Match",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = coralAccent,
                                fontWeight = FontWeight.Black,
                                fontSize = 10.sp
                            )
                        )
                    }
                }
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        RoommateShowcaseCard(
                            name = "Rahul Sharma",
                            branch = "3rd Yr CSE • MRU",
                            hometown = "Warangal",
                            score = 98,
                            routine = "Night Owl • Study-Focused",
                            tags = listOf("Night Coder", "Non-Smoker", "Valorant"),
                            isLiked = likedRoommates["rahul"] == true,
                            onLike = {
                                likedRoommates = likedRoommates + ("rahul" to !(likedRoommates["rahul"] ?: false))
                            }
                        )
                    }
                    item {
                        RoommateShowcaseCard(
                            name = "Ananya Reddy",
                            branch = "2nd Yr AI & DS • MLRIT",
                            hometown = "Hyderabad",
                            score = 94,
                            routine = "Early Riser • Bookworm",
                            tags = listOf("Pure Veg", "Quiet Study", "Neat Room"),
                            isLiked = likedRoommates["ananya"] == true,
                            onLike = {
                                likedRoommates = likedRoommates + ("ananya" to !(likedRoommates["ananya"] ?: false))
                            }
                        )
                    }
                    item {
                        RoommateShowcaseCard(
                            name = "Karthik Verma",
                            branch = "4th Yr ECE • CMRIT",
                            hometown = "Karimnagar",
                            score = 91,
                            routine = "Fitness & Travel Lover",
                            tags = listOf("Gym", "Foodie", "Weekend Biker"),
                            isLiked = likedRoommates["karthik"] == true,
                            onLike = {
                                likedRoommates = likedRoommates + ("karthik" to !(likedRoommates["karthik"] ?: false))
                            }
                        )
                    }
                }
            }

            // ==========================================
            // 9. HOW IT WORKS (3 STEPS)
            // ==========================================
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "How Stayora Works",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            color = purpleDark
                        )
                    )

                    HowItWorksStepCard(
                        step = "1",
                        title = "Set Your Vibe & Preferences",
                        desc = "Select college, monthly rent, room sharing type, and your daily sleeping habits in under 60 seconds."
                    )
                    HowItWorksStepCard(
                        step = "2",
                        title = "Browse Verified Stays & Matches",
                        desc = "Compare audited hostels with zero hidden electricity surcharges, and match with fellow college peers."
                    )
                    HowItWorksStepCard(
                        step = "3",
                        title = "Direct In-App Chat & Move In",
                        desc = "Chat directly with wardens and prospective roommates. Schedule free visits with 0 brokerage cut."
                    )
                }
            }

            // ==========================================
            // 10. CAMPUS SAFETY & EMERGENCY ESSENTIALS
            // ==========================================
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Nearby Safety & Essentials",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = purpleDark
                                )
                            )
                            Text(
                                text = "Emergency facilities within 500 meters",
                                style = MaterialTheme.typography.labelSmall.copy(color = CharcoalLight)
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = VerifiedGreen,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    EssentialSafetyCard(
                        icon = Icons.Default.LocalHospital,
                        title = "Malla Reddy Narayana Multi-Speciality",
                        category = "24/7 Emergency Casualty & Trauma Care",
                        distance = "0.5 km (4 mins walk)",
                        color = Color(0xFFE11D48)
                    )

                    EssentialSafetyCard(
                        icon = Icons.Default.DirectionsBus,
                        title = "Maisammaguda TSRTC Bus Terminal",
                        category = "Direct Routes 229, 227 to Secunderabad & JNTU",
                        distance = "0.3 km (3 mins walk)",
                        color = violetAccent
                    )

                    EssentialSafetyCard(
                        icon = Icons.Default.MedicalServices,
                        title = "Apollo 24/7 & MedPlus Pharmacy",
                        category = "Doorstep Student Delivery & Prescription Meds",
                        distance = "0.4 km (3 mins walk)",
                        color = Color(0xFF059669)
                    )

                    EssentialSafetyCard(
                        icon = Icons.Default.LocalPolice,
                        title = "Maisammaguda Police Outpost & SHE Team",
                        category = "Round-the-clock Student Patrols & Helpline",
                        distance = "0.6 km (5 mins walk)",
                        color = Color(0xFF2563EB)
                    )
                }
            }

            // ==========================================
            // 11. STUDENT TESTIMONIALS
            // ==========================================
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Student Reviews",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            color = purpleDark
                        )
                    )

                    TestimonialCard(
                        name = "Sneha Pabbathi",
                        role = "B.Tech CSE '26 • Malla Reddy Univ",
                        text = "Finding a reliable girls PG without broker aunties charging 1 month rent was impossible before Stayora. Found a 2-sharing room with high-speed Wi-Fi in 20 minutes!",
                        rating = 5
                    )

                    TestimonialCard(
                        name = "Vikram Kothari",
                        role = "Mechanical Engg '25 • MLRIT",
                        text = "The distance filter is a lifesaver. I literally walk 5 minutes to my morning lectures. Food is hygienic and warden is friendly.",
                        rating = 5
                    )
                }
            }

            // ==========================================
            // 12. DYNAMIC STATS BANNER
            // ==========================================
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = purpleDeep)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "Stayora Campus Impact",
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = coralAccent,
                                fontWeight = FontWeight.Black
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            StatItem(number = "4,850+", label = "Students Placed")
                            StatItem(number = "85+", label = "Verified Hostels")
                            StatItem(number = "98%", label = "Harmony Rate")
                            StatItem(number = "₹0", label = "Brokerage")
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

// ==========================================
// REUSABLE SUB-COMPONENTS
// ==========================================

@Composable
private fun CampusChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    tint: Color,
    bg: Color
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE8DCFC), RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(bg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tint,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF14032F)
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CharcoalLight,
                        fontSize = 10.sp
                    )
                )
            }
        }
    }
}

@Composable
private fun HostelShowcaseCard(
    name: String,
    tag: String,
    rent: String,
    distance: String,
    rating: String,
    curfew: String,
    imageUrl: String,
    amenities: List<String>,
    badgeColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Real Room Photo with Tag Pill & Rating Pill overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(Color(0xFF250065))
            ) {
                if (imageUrl.isNotBlank()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Dark gradient scrim
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Black.copy(alpha = 0.40f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.65f)
                                )
                            )
                        )
                )

                // Top Tag Pill
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.95f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = tag,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = badgeColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    )
                }

                // Top Right Rating
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.95f))
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = rating,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFFD97706),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 10.sp
                        )
                    )
                }
            }

            Column(modifier = Modifier.padding(14.dp)) {

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF14032F)
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = distance,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = CharcoalLight,
                    fontSize = 11.sp
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                amenities.take(2).forEach {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFFAF7FF))
                            .border(1.dp, Color(0xFFE8DCFC), RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 9.sp,
                                color = Color(0xFF250065)
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = rent,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF250065)
                    )
                )

                Text(
                    text = "Gate: $curfew",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                )
            }
        }
    }
}
}

@Composable
private fun RoommateShowcaseCard(
    name: String,
    branch: String,
    hometown: String,
    score: Int,
    routine: String,
    tags: List<String>,
    isLiked: Boolean,
    onLike: () -> Unit
) {
    Card(
        modifier = Modifier.width(260.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEDE9FE)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = name.first().toString(),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF250065)
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF14032F)
                            )
                        )
                        Text(
                            text = branch,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF8B5CF6),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                IconButton(
                    onClick = onLike,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Connect",
                        tint = if (isLiked) Color(0xFFFF6B4A) else Color.LightGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFFFE8E3))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Match Compatibility",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF250065),
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    )
                    Text(
                        text = "$score% Match",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFFFF6B4A),
                            fontWeight = FontWeight.Black,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Habit: $routine",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.DarkGray,
                    fontSize = 11.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                tags.take(2).forEach {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFFAF7FF))
                            .border(1.dp, Color(0xFFE8DCFC), RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "#$it",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 9.sp,
                                color = Color(0xFF6B38D4)
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HowItWorksStepCard(
    step: String,
    title: String,
    desc: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF250065)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = step,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF14032F)
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = CharcoalLight,
                        lineHeight = 18.sp
                    )
                )
            }
        }
    }
}

@Composable
private fun EssentialSafetyCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    category: String,
    distance: String,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF14032F)
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = CharcoalLight,
                        fontSize = 10.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFFAF7FF))
                    .border(1.dp, Color(0xFFE8DCFC), RoundedCornerShape(8.dp))
                    .padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
                Text(
                    text = distance,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF250065)
                    )
                )
            }
        }
    }
}

@Composable
private fun TestimonialCard(
    name: String,
    role: String,
    text: String,
    rating: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF14032F)
                        )
                    )
                    Text(
                        text = role,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF8B5CF6),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    repeat(rating) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFF59E0B),
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "\"$text\"",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.DarkGray,
                    lineHeight = 18.sp
                )
            )
        }
    }
}

@Composable
private fun StatItem(number: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = number,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                color = Color(0xFFFF6B4A)
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 10.sp
            )
        )
    }
}
