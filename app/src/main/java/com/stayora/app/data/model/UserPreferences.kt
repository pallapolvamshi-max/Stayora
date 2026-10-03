package com.stayora.app.data.model

data class UserPreferences(
    val college: String = "Malla Reddy University",
    val branch: String = "B.Tech CSE",
    val roommateTypePreference: String = "Same College & Branch", // "Same College", "Same Branch", "Same Hometown/Village", "No Preference"
    val personalityPreferences: List<String> = listOf("Quiet / Study-focused", "Night Owl"), 
    // Options: Introvert, Extrovert, Travel Enthusiast, Early Riser, Night Owl, Quiet/Study-focused, Fitness Enthusiast, No Preference
    val minBudget: Int = 4500,
    val maxBudget: Int = 9000,
    val maxDistanceKm: Double = 3.0,
    val preferredSharing: Int = 2, // 2 or 4 sharing
    val requiredAmenities: List<String> = listOf("Wi-Fi", "Washing Machine", "24-Hour Water", "Security / CCTV", "Meals / Food"),
    val moveInDate: String = "Within 2 Weeks",
    val roomType: String = "Hostel / PG", // "Hostel / PG", "Bachelor Flat"
    val genderPreference: String = "Boys" // Boys, Girls, Co-ed
)
