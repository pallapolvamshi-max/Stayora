package com.stayora.app.data.model

enum class FacilityCategory(val title: String, val iconName: String) {
    HOSPITAL("Hospital / Medical", "LocalHospital"),
    BUS_STATION("Bus Station / Stop", "DirectionsBus"),
    METRO_STATION("Metro Station", "Train"),
    PHARMACY("24/7 Pharmacy", "MedicalServices"),
    SUPERMARKET("Supermarket & Stores", "ShoppingCart"),
    FOOD_COURT("Food Court & Mess", "Restaurant")
}

data class NearbyFacility(
    val id: String,
    val name: String,
    val category: FacilityCategory,
    val distanceKm: Double,
    val travelMinutes: Int,
    val mode: String = "walk", // "walk", "bus", "auto"
    val address: String,
    val contactOrTiming: String
)
