package com.stayora.app.data.repository

import com.stayora.app.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StayoraRepository {

    private val _preferences = MutableStateFlow(UserPreferences())
    val preferences: StateFlow<UserPreferences> = _preferences.asStateFlow()

    private val _properties = MutableStateFlow<List<Property>>(initialProperties)
    val properties: StateFlow<List<Property>> = _properties.asStateFlow()

    private val _compareList = MutableStateFlow<List<Property>>(emptyList())
    val compareList: StateFlow<List<Property>> = _compareList.asStateFlow()

    private val _conversations = MutableStateFlow<Map<String, List<ChatMessage>>>(initialChats)
    val conversations: StateFlow<Map<String, List<ChatMessage>>> = _conversations.asStateFlow()

    fun updatePreferences(newPrefs: UserPreferences) {
        _preferences.value = newPrefs
        // Re-score properties according to preferences
        _properties.update { list ->
            list.map { prop ->
                prop.copy(matchScore = calculateMatchScore(prop, newPrefs))
            }
        }
    }

    private fun calculateMatchScore(property: Property, prefs: UserPreferences): Int {
        var score = 70
        // Budget match
        if (property.monthlyRent in prefs.minBudget..prefs.maxBudget) score += 12
        // Distance match
        if (property.distanceKm <= prefs.maxDistanceKm) score += 10
        // Sharing match
        if (property.sharingOptions.any { it.sharingType == prefs.preferredSharing }) score += 8
        return score.coerceAtMost(99)
    }

    fun getPropertyById(id: String): Property? {
        return _properties.value.find { it.id == id }
    }

    fun toggleFavorite(propertyId: String) {
        _properties.update { list ->
            list.map {
                if (it.id == propertyId) it.copy(isFavorite = !it.isFavorite) else it
            }
        }
    }

    fun toggleCompare(property: Property): Boolean {
        val current = _compareList.value.toMutableList()
        val exists = current.any { it.id == property.id }
        if (exists) {
            current.removeAll { it.id == property.id }
            _compareList.value = current
            return false
        } else {
            if (current.size < 3) {
                current.add(property)
                _compareList.value = current
                return true
            }
            return false // Max 3
        }
    }

    fun removeFromCompare(propertyId: String) {
        _compareList.update { list -> list.filter { it.id != propertyId } }
    }

    fun sendMessage(propertyId: String, text: String) {
        val currentMessages = _conversations.value[propertyId]?.toMutableList() ?: mutableListOf()
        val studentMsg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            senderId = "student",
            senderName = "You",
            text = text,
            timestamp = "Just now",
            isFromStudent = true
        )
        currentMessages.add(studentMsg)

        // Mock quick automatic reply from warden/owner
        val replyText = when {
            text.contains("vacant", ignoreCase = true) -> 
                "Yes! We currently have 2 vacant beds in our 2-sharing AC room on the 2nd floor."
            text.contains("visit", ignoreCase = true) -> 
                "Sure! You can visit tomorrow between 10:00 AM and 7:00 PM. Our warden will show you the rooms."
            text.contains("deposit", ignoreCase = true) -> 
                "Deposit is strictly refundable with 1 month prior notice. No hidden maintenance cuts."
            text.contains("food", ignoreCase = true) || text.contains("mess", ignoreCase = true) -> 
                "Yes, breakfast, lunch, and dinner are included. We serve North & South Indian home-style food."
            else -> 
                "Hello! Thanks for reaching out through Stayora. I'm happy to assist you with room allotment and booking."
        }

        val ownerReply = ChatMessage(
            id = "msg_${System.currentTimeMillis() + 100}",
            senderId = "owner",
            senderName = "Property Manager",
            text = replyText,
            timestamp = "Just now",
            isFromStudent = false
        )
        currentMessages.add(ownerReply)

        _conversations.update { map ->
            map.toMutableMap().apply { put(propertyId, currentMessages) }
        }
    }

    fun getRoommateProfiles(): List<RoommateProfile> = mockRoommates

    fun getNearbyFacilities(propertyId: String): List<NearbyFacility> = mockFacilities

    companion object {
        val initialProperties = listOf(
            Property(
                id = "prop_1",
                title = "Stanza Living Greenfield Scholars PG",
                type = PropertyType.BOYS_HOSTEL,
                monthlyRent = 6800,
                deposit = 10000,
                distanceKm = 0.6,
                travelTimeMinutes = 7,
                travelMode = "walk",
                address = "Plot 42, Maisammaguda, Near Malla Reddy Engg College Gate 2",
                nearbyUniversity = "Malla Reddy University (MRUH)",
                rating = 4.8,
                reviewCount = 124,
                availabilityStatus = "2 Beds Available in 2-Sharing",
                sharingOptions = listOf(
                    SharingOption(1, "Private 1-Sharing Room", 12500, 15000, true, 1),
                    SharingOption(2, "Spacious 2-Sharing AC", 6800, 10000, true, 2),
                    SharingOption(4, "Budget 4-Sharing Non-AC", 4800, 7000, false, 3)
                ),
                amenities = listOf(
                    "High-Speed Wi-Fi (150 Mbps)",
                    "Automatic Washing Machine",
                    "24/7 Water & Hot Geyser",
                    "3 Home-Style Meals (Unlimited)",
                    "Biometric & CCTV Security",
                    "Daily Room Housekeeping",
                    "Silent Study Desks & Power Sockets",
                    "Covered Two-Wheeler Parking",
                    "100% Inverter Power Backup"
                ),
                rules = listOf(
                    "Main gate curfew: 10:30 PM strictly",
                    "Visitors allowed in lounge area until 7:30 PM",
                    "No smoking, vaping or alcohol inside premises",
                    "Study hour quiet time after 11:00 PM"
                ),
                timings = "Curfew: 10:30 PM • Mess: 7:30-9:30 AM, 12:30-2:30 PM, 7:30-9:45 PM",
                securityDetails = listOf(
                    "24/7 Monitored CCTV with 30-day backup",
                    "Resident male warden living on ground floor",
                    "Biometric fingerprint turnstile gate",
                    "First aid and tie-up with Malla Reddy Narayana Hospital"
                ),
                ownerName = "Rajeshwar Rao",
                ownerRole = "General Manager",
                ownerPhone = "+91 98490 23411",
                ownerVerified = true,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1555854877-bab0e564b8d5?auto=format&fit=crop&w=800&q=80",
                    "https://images.unsplash.com/photo-1595526114035-0d45ed16cfbf?auto=format&fit=crop&w=800&q=80",
                    "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?auto=format&fit=crop&w=800&q=80"
                ),
                isFavorite = true,
                matchScore = 96,
                reviews = listOf(
                    Review("rev_1", "Sai Krishna (CSE 3rd Yr)", "Malla Reddy University", 5.0, "2 weeks ago", "Super close to campus. Wi-Fi is blazing fast and food tastes like homemade curries."),
                    Review("rev_2", "Aditya Varma (ECE 2nd Yr)", "MRCE", 4.5, "1 month ago", "Very peaceful for coding and exam prep. Washing machines are easily available.")
                )
            ),
            Property(
                id = "prop_2",
                title = "Sri Sai Balaji Elite Residency & PG",
                type = PropertyType.BOYS_HOSTEL,
                monthlyRent = 5500,
                deposit = 7000,
                distanceKm = 1.1,
                travelTimeMinutes = 12,
                travelMode = "walk",
                address = "Road No. 3, Kompally Bypass, Near Dulapally Junction",
                nearbyUniversity = "Malla Reddy University & MLRIT",
                rating = 4.6,
                reviewCount = 89,
                availabilityStatus = "Filling Fast • 3 Beds Left",
                sharingOptions = listOf(
                    SharingOption(2, "Deluxe 2-Sharing", 7000, 9000, true, 1),
                    SharingOption(3, "Comfort 3-Sharing", 5800, 7500, true, 2),
                    SharingOption(4, "Standard 4-Sharing", 5200, 6500, false, 4)
                ),
                amenities = listOf(
                    "Wi-Fi with Dual Router Backup",
                    "24-Hour Hot & Cold Water",
                    "Washing Machine on Every Floor",
                    "South Indian & North Indian Meals",
                    "Gym & Fitness Zone",
                    "TV Lounge & Table Tennis",
                    "24/7 Security Guard"
                ),
                rules = listOf(
                    "Curfew 10:00 PM for fresher students",
                    "Room cleaning alternate days",
                    "No outside overnight guests without gate pass"
                ),
                timings = "Curfew: 10:00 PM • Food timings strictly followed",
                securityDetails = listOf(
                    "Physical security guard at entrance 24/7",
                    "Visitor logbook & parent phone verification",
                    "Fire extinguishers and emergency evacuation map"
                ),
                ownerName = "K. Venkat Reddy",
                ownerRole = "Owner",
                ownerPhone = "+91 94401 88921",
                ownerVerified = true,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1595526114035-0d45ed16cfbf?auto=format&fit=crop&w=800&q=80",
                    "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=800&q=80"
                ),
                isFavorite = false,
                matchScore = 91,
                reviews = listOf(
                    Review("rev_3", "Praveen Kumar", "Malla Reddy Pharmacy", 4.5, "3 weeks ago", "Affordable rent for students. Owner Venkat Reddy sir is very supportive.")
                )
            ),
            Property(
                id = "prop_3",
                title = "Serene Oasis Luxury Girls PG & Hostel",
                type = PropertyType.GIRLS_HOSTEL,
                monthlyRent = 7200,
                deposit = 12000,
                distanceKm = 0.8,
                travelTimeMinutes = 9,
                travelMode = "walk",
                address = "Behind Malla Reddy Women's College, Maisammaguda Main Rd",
                nearbyUniversity = "Malla Reddy University & MREC for Women",
                rating = 4.9,
                reviewCount = 142,
                availabilityStatus = "Only 1 Vacant Bed in 2-Sharing",
                sharingOptions = listOf(
                    SharingOption(2, "Executive 2-Sharing AC", 7800, 12000, true, 1),
                    SharingOption(3, "Spacious 3-Sharing", 6600, 10000, true, 2),
                    SharingOption(4, "Cozy 4-Sharing", 5600, 8000, true, 3)
                ),
                amenities = listOf(
                    "Ultra-Secure Biometric Access",
                    "Female Security Guards 24/7",
                    "Hygienic 3 Meals + Evening Snacks",
                    "Automatic Washing Machines & Dryers",
                    "RO Mineral Drinking Water 24/7",
                    "Air Conditioning in all rooms",
                    "High-Speed Fiber Wi-Fi",
                    "Study Library Room & Balconies"
                ),
                rules = listOf(
                    "Strict curfew at 9:30 PM (extension on parent SMS approval)",
                    "Parents and female guardians only allowed in lobby",
                    "Zero ragging and zero tolerance conduct policy"
                ),
                timings = "Curfew: 9:30 PM • Dining: Breakfast 7:30AM, Snacks 5:00PM, Dinner 8:00PM",
                securityDetails = listOf(
                    "Full perimeter CCTV and gate registration system",
                    "Two resident female wardens on premises",
                    "Direct emergency siren connection to nearest police chowki"
                ),
                ownerName = "Mrs. Sunitha Devi & Ramesh",
                ownerRole = "Chief Warden & Admin",
                ownerPhone = "+91 97011 55432",
                ownerVerified = true,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?auto=format&fit=crop&w=800&q=80",
                    "https://images.unsplash.com/photo-1555854877-bab0e564b8d5?auto=format&fit=crop&w=800&q=80"
                ),
                isFavorite = false,
                matchScore = 88,
                reviews = listOf(
                    Review("rev_4", "Meghana R.", "B.Tech IT (Final Year)", 5.0, "1 month ago", "Safest hostel near campus. Wardens take care just like family members.")
                )
            ),
            Property(
                id = "prop_4",
                title = "CampusEdge Student Living Studio & 2BHK",
                type = PropertyType.BACHELOR_ROOM,
                monthlyRent = 8500,
                deposit = 15000,
                distanceKm = 1.8,
                travelTimeMinutes = 6,
                travelMode = "bike",
                address = "Kompally Green Valley, Near Cineplanet & Suchitra",
                nearbyUniversity = "Malla Reddy University & BITS Pilani Hyderabad",
                rating = 4.7,
                reviewCount = 67,
                availabilityStatus = "Available Immediately",
                sharingOptions = listOf(
                    SharingOption(2, "Master Bedroom (2 Sharing)", 8500, 15000, true, 2),
                    SharingOption(1, "Private Single Room", 14000, 20000, true, 1)
                ),
                amenities = listOf(
                    "Modular Kitchen with Gas & Fridge",
                    "Fully Furnished Living Room & TV",
                    "Automatic Washing Machine",
                    "Dedicated High-Speed Wi-Fi",
                    "Gated Community with 24h Guard",
                    "Covered Car & Bike Parking",
                    "Swimming Pool & Clubhouse Access"
                ),
                rules = listOf(
                    "No loud parties after 11:30 PM",
                    "Guests allowed with prior community registration",
                    "Individual electricity meter billing"
                ),
                timings = "No curfew • 24/7 Gated Entry with Security Guard",
                securityDetails = listOf(
                    "Gated society entrance with MyGate app approval",
                    "CCTV monitoring on all floor lobbies and parking",
                    "Intercom facility to security desk"
                ),
                ownerName = "Vikram Teja",
                ownerRole = "Owner / Flat Partner",
                ownerPhone = "+91 91210 99876",
                ownerVerified = true,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?auto=format&fit=crop&w=800&q=80",
                    "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?auto=format&fit=crop&w=800&q=80"
                ),
                isFavorite = false,
                matchScore = 85,
                reviews = listOf(
                    Review("rev_5", "Karthik N.", "AI & ML Student", 4.5, "2 months ago", "Best flat for coders and senior students. Complete freedom with great amenities.")
                )
            ),
            Property(
                id = "prop_5",
                title = "Nexus Co-Living & Tech PG",
                type = PropertyType.COED_PG,
                monthlyRent = 7500,
                deposit = 12000,
                distanceKm = 1.4,
                travelTimeMinutes = 5,
                travelMode = "bike",
                address = "Medchal Highway, Opp CMR & Malla Reddy Campuses",
                nearbyUniversity = "Malla Reddy University & CMR College",
                rating = 4.8,
                reviewCount = 95,
                availabilityStatus = "3 Beds Vacant",
                sharingOptions = listOf(
                    SharingOption(2, "Co-living 2-Sharing AC", 7500, 12000, true, 2),
                    SharingOption(3, "Co-living 3-Sharing Non-AC", 6200, 9000, false, 1)
                ),
                amenities = listOf(
                    "Co-working Space with High-Speed LAN",
                    "Gaming Zone (PS5 & Pool Table)",
                    "Chef-Curated Buffet Meals",
                    "Daily Housekeeping & Laundry Service",
                    "24-Hour Power Backup",
                    "Rooftop Cafe & Open Air Cinema"
                ),
                rules = listOf(
                    "Curfew 11:30 PM on weekdays, 12:30 AM weekends",
                    "Access card required for entry at all times"
                ),
                timings = "Flexible curfew 11:30 PM • 24-Hour Lounge & Cafe Access",
                securityDetails = listOf(
                    "Smart RFID keycard door locks",
                    "Professional security staff on duty around the clock",
                    "Smart SOS alert button in mobile app"
                ),
                ownerName = "Suresh Verma",
                ownerRole = "Community Lead",
                ownerPhone = "+91 93902 44321",
                ownerVerified = true,
                imageUrls = listOf(
                    "https://images.unsplash.com/photo-1595526114035-0d45ed16cfbf?auto=format&fit=crop&w=800&q=80",
                    "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?auto=format&fit=crop&w=800&q=80"
                ),
                isFavorite = false,
                matchScore = 93,
                reviews = listOf(
                    Review("rev_6", "Ananya Sharma", "Malla Reddy MBA", 5.0, "3 weeks ago", "Feels like a mini tech company campus! The rooftop lounge and coffee are amazing.")
                )
            )
        )

        val mockRoommates = listOf(
            RoommateProfile(
                id = "roommate_1",
                name = "Tarun Goud",
                age = 20,
                college = "Malla Reddy University",
                branch = "B.Tech CSE (AI & ML)",
                yearOfStudy = "3rd Year",
                hometown = "Nizamabad / Karimnagar",
                personalityTraits = listOf("Quiet / Study-focused", "Night Owl", "Tech Geek"),
                budgetRange = "₹5,500 - ₹7,500",
                preferredSharing = 2,
                compatibilityScore = 96,
                bio = "Competitive coder and hackathon enthusiast. Mostly study in the evenings, clean room habits, non-smoker.",
                cleanHabitRating = 5,
                sleepSchedule = "Night Owl (1:30 AM - 8:30 AM)",
                dietPreference = "Non-Veg Friendly",
                avatarUrl = "https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?auto=format&fit=crop&w=300&q=80"
            ),
            RoommateProfile(
                id = "roommate_2",
                name = "Rohit Kumar Sharma",
                age = 19,
                college = "Malla Reddy University",
                branch = "B.Tech CSE (Core)",
                yearOfStudy = "2nd Year",
                hometown = "Warangal / Hanamkonda",
                personalityTraits = listOf("Fitness Enthusiast", "Early Riser", "Introvert"),
                budgetRange = "₹5,000 - ₹7,000",
                preferredSharing = 2,
                compatibilityScore = 92,
                bio = "Gym in the morning, classes till 4 PM, quiet study hours afterwards. Looking for a chilled-out roommate.",
                cleanHabitRating = 5,
                sleepSchedule = "Early Riser (6:00 AM - 11:00 PM)",
                dietPreference = "Vegetarian",
                avatarUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=300&q=80"
            ),
            RoommateProfile(
                id = "roommate_3",
                name = "Harshita Reddy",
                age = 20,
                college = "Malla Reddy Women's College",
                branch = "B.Tech ECE",
                yearOfStudy = "3rd Year",
                hometown = "Hyderabad (East)",
                personalityTraits = listOf("Travel Enthusiast", "Extrovert", "Study-focused"),
                budgetRange = "₹6,000 - ₹8,500",
                preferredSharing = 2,
                compatibilityScore = 89,
                bio = "Looking for a friendly roommate who values clean space, good food, and occasional weekend outings.",
                cleanHabitRating = 4,
                sleepSchedule = "Flexible (12:00 AM - 7:30 AM)",
                dietPreference = "Vegetarian",
                avatarUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=300&q=80"
            ),
            RoommateProfile(
                id = "roommate_4",
                name = "Abhishek Rao",
                age = 21,
                college = "Malla Reddy Engg College (Autonomous)",
                branch = "B.Tech Mechanical",
                yearOfStudy = "4th Year",
                hometown = "Khammam",
                personalityTraits = listOf("Night Owl", "Sports Lover", "Extrovert"),
                budgetRange = "₹4,500 - ₹6,000",
                preferredSharing = 4,
                compatibilityScore = 84,
                bio = "Cricket player, chill roommate, preparing for campus placements. Easy going and respectful.",
                cleanHabitRating = 4,
                sleepSchedule = "Night Owl (1:00 AM - 8:00 AM)",
                dietPreference = "Non-Veg Friendly",
                avatarUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&w=300&q=80"
            )
        )

        val mockFacilities = listOf(
            NearbyFacility(
                id = "fac_1",
                name = "Malla Reddy Narayana Multi-Speciality Hospital",
                category = FacilityCategory.HOSPITAL,
                distanceKm = 0.5,
                travelMinutes = 6,
                mode = "walk",
                address = "Suraram Main Road, Jeedimetla, Hyderabad",
                contactOrTiming = "24/7 Emergency: 040-23783000"
            ),
            NearbyFacility(
                id = "fac_2",
                name = "Maisammaguda Bus Depot & TSRTC Stop",
                category = FacilityCategory.BUS_STATION,
                distanceKm = 0.3,
                travelMinutes = 4,
                mode = "walk",
                address = "Opposite Gate 1, Maisammaguda Junction",
                contactOrTiming = "Buses every 10 mins towards Secunderabad & Medchal"
            ),
            NearbyFacility(
                id = "fac_3",
                name = "Balanagar / JNTU Metro Station",
                category = FacilityCategory.METRO_STATION,
                distanceKm = 4.2,
                travelMinutes = 14,
                mode = "bus",
                address = "Red Line Metro Corridor",
                contactOrTiming = "6:00 AM to 11:00 PM • Direct feeder autos available"
            ),
            NearbyFacility(
                id = "fac_4",
                name = "Apollo Pharmacy 24/7",
                category = FacilityCategory.PHARMACY,
                distanceKm = 0.4,
                travelMinutes = 5,
                mode = "walk",
                address = "Maisammaguda Main Market",
                contactOrTiming = "Open 24 Hours • Free doorstep delivery"
            ),
            NearbyFacility(
                id = "fac_5",
                name = "Student Tiffin Center & North/South Mess",
                category = FacilityCategory.FOOD_COURT,
                distanceKm = 0.2,
                travelMinutes = 2,
                mode = "walk",
                address = "Near MRUH Library Lane",
                contactOrTiming = "7:00 AM - 11:00 PM • Daily student discounts"
            ),
            NearbyFacility(
                id = "fac_6",
                name = "Reliance Smart Supermarket & Stationery",
                category = FacilityCategory.SUPERMARKET,
                distanceKm = 1.0,
                travelMinutes = 10,
                mode = "walk",
                address = "Kompally Junction Road",
                contactOrTiming = "8:00 AM - 10:00 PM"
            )
        )

        val initialChats = mapOf(
            "prop_1" to listOf(
                ChatMessage("c1", "student", "You", "Hi! Is 2-sharing bed vacant for move-in next week?", "Yesterday 4:30 PM", true),
                ChatMessage("c2", "owner", "Rajeshwar Rao (Manager)", "Hello! Yes, we have 2 vacant beds in the second floor AC room. Both existing roommates are 3rd year CSE students.", "Yesterday 4:45 PM", false),
                ChatMessage("c3", "student", "You", "Great! Does the ₹6,800 rent include 3 times food and Wi-Fi?", "Yesterday 4:50 PM", true),
                ChatMessage("c4", "owner", "Rajeshwar Rao (Manager)", "Yes, completely all-inclusive! 3 unlimited home-cooked meals, 150 Mbps Wi-Fi, daily housekeeping, and power backup are all included.", "Yesterday 5:02 PM", false)
            ),
            "prop_2" to listOf(
                ChatMessage("c5", "student", "You", "Hello sir, can I visit tomorrow evening around 5 PM?", "Today 11:00 AM", true),
                ChatMessage("c6", "owner", "K. Venkat Reddy", "Namaste! Yes definitely, call me when you reach the Kompally arch. I will arrange the warden to show you room 204.", "Today 11:15 AM", false)
            )
        )
    }
}
