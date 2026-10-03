package com.stayora.app.data.model

enum class PropertyType(val label: String) {
    BOYS_HOSTEL("Boys Hostel"),
    GIRLS_HOSTEL("Girls Hostel"),
    COED_PG("Co-ed Luxury PG"),
    BACHELOR_ROOM("Bachelor Flat / 1BHK")
}

data class SharingOption(
    val sharingType: Int, // 1, 2, 3, 4 sharing
    val title: String,    // "2 Sharing Room"
    val rentPerBed: Int,  // ₹6,500
    val deposit: Int,     // ₹10,000
    val hasAttachedWashroom: Boolean,
    val vacantBeds: Int
)

data class Review(
    val id: String,
    val studentName: String,
    val studentCollege: String,
    val rating: Double,
    val date: String,
    val comment: String
)

data class Property(
    val id: String,
    val title: String,
    val type: PropertyType,
    val monthlyRent: Int, // Base rent or preferred sharing rent
    val deposit: Int,
    val distanceKm: Double,
    val travelTimeMinutes: Int,
    val travelMode: String = "walk", // walk, bike, bus
    val address: String,
    val nearbyUniversity: String,
    val rating: Double,
    val reviewCount: Int,
    val availabilityStatus: String,
    val sharingOptions: List<SharingOption>,
    val amenities: List<String>,
    val rules: List<String>,
    val timings: String,
    val securityDetails: List<String>,
    val ownerName: String,
    val ownerRole: String,
    val ownerPhone: String,
    val ownerVerified: Boolean,
    val imageUrls: List<String>,
    val isFavorite: Boolean = false,
    val matchScore: Int = 92, // Computed from student preferences
    val reviews: List<Review> = emptyList()
)
