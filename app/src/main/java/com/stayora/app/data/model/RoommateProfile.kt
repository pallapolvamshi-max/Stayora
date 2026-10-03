package com.stayora.app.data.model

data class RoommateProfile(
    val id: String,
    val name: String,
    val age: Int,
    val college: String,
    val branch: String,
    val yearOfStudy: String,
    val hometown: String,
    val personalityTraits: List<String>, // "Quiet / Study-focused", "Night Owl", "Fitness Enthusiast"
    val budgetRange: String,             // "₹5,000 - ₹7,500"
    val preferredSharing: Int,           // 2 or 4
    val compatibilityScore: Int,         // e.g. 96%
    val bio: String,
    val cleanHabitRating: Int,           // 1 to 5
    val sleepSchedule: String,           // "Night Owl (sleeps at 1:30 AM)"
    val dietPreference: String,          // "Vegetarian" or "Non-Veg Friendly"
    val avatarUrl: String,
    val isVerifiedStudent: Boolean = true
)
