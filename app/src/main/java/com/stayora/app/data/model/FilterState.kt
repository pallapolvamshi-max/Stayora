package com.stayora.app.data.model

data class FilterState(
    val searchQuery: String = "",
    val minRent: Int = 3000,
    val maxRent: Int = 15000,
    val maxDistanceKm: Double = 10.0,
    val selectedSharingTypes: Set<Int> = emptySet(), // 1, 2, 3, 4
    val selectedPropertyTypes: Set<PropertyType> = emptySet(),
    val selectedAmenities: Set<String> = emptySet(),
    val minRating: Double = 0.0,
    val onlyAvailable: Boolean = false,
    val verifiedOnly: Boolean = false,
    val sortBy: SortOption = SortOption.RECOMMENDED
)

enum class SortOption(val label: String) {
    RECOMMENDED("Best Match"),
    RENT_LOW_HIGH("Price: Low to High"),
    RENT_HIGH_LOW("Price: High to Low"),
    DISTANCE("Distance: Nearest First"),
    RATING("Highest Rated")
}
