# Stayora — Smart Student Accommodation & Roommate Discovery App
*Built for Malla Reddy Hackathon*

**Stayora** is a modern Android mobile app developed with **Kotlin** and **Jetpack Compose** tailored for university students and young professionals seeking hostels, PGs, bachelor rooms, and compatible roommates near their campus.

---

## 💜 Design System & Aesthetics
- **Theme**: Clean, student-friendly **Purple & White** aesthetic.
  - Primary Purple: `#6B21A8`
  - Royal Deep Purple: `#4C1D95`
  - Lavender Tint: `#EDE9FE`
  - Crisp White Surface: `#FFFFFF`
  - Verification & Safety Accents: Emerald Green (`#10B981`)
  - Rating Stars: Warm Amber (`#F59E0B`)
- **Framework**: Jetpack Compose (Material 3), Navigation Compose, StateFlow architecture.
- **Dataset**: Realistic dummy dataset covering colleges around **Malla Reddy University (MRUH)**, **Maisammaguda**, **Dulapally**, **Kompally**, and **JNTU**.

---

## 📱 Implemented Screens & Features

1. **Onboarding & Preference Form** (`OnboardingScreen.kt`):
   - University selection: *Malla Reddy University, MREC, MLRIT, etc.*
   - Branch / course: *B.Tech CSE, AI&ML, ECE, IT, etc.*
   - Roommate type preferences: *Same college, same branch, same hometown/village, or no preference.*
   - Personality preferences: *Introvert, extrovert, travel enthusiast, early riser, night owl, quiet/study-focused, fitness enthusiast.*
   - Interactive budget slider (₹3,000 – ₹15,000) & campus distance slider (0.5 km – 8.0 km).
   - Sharing preference: *2-sharing or 4-sharing*.
   - Mandatory amenities selection & move-in timeline.

2. **Home Screen** (`HomeScreen.kt`):
   - Personalized campus badge and custom recommendation engine.
   - Quick category pills: *All, Boys Hostel, Girls Hostel, Co-ed PG, Bachelor Flat*.
   - **Compatible Roommates Spotlight** with AI match percentages (e.g., 96% Match).
   - Recommended hostel cards with match score, walking distance, rent, deposit, and sharing options.

3. **Search & Multi-Parameter Filter Screen** (`SearchFilterScreen.kt`):
   - Real-time search query across hostel names, areas, and amenities.
   - Comprehensive Filter Sheet: budget range, distance, gender (Boys/Girls/Co-ed), sharing (1, 2, 3, 4), required amenities, minimum ratings (4.0+, 4.5+), and vacant bed toggle.
   - Live matching count and multi-attribute sorting (*Best Match, Price Low-to-High, Distance, Rating*).

4. **Property Detail Screen** (`PropertyDetailScreen.kt`):
   - High-resolution hero image header with verified & compatibility badges.
   - Transparent monthly rent and security deposit highlight.
   - Commute badge (*7 min walk / 4 min bike to campus*).
   - Detailed Room Sharing Breakdown with price per bed & attached washroom details.
   - Full amenities grid (Wi-Fi 150 Mbps, washing machines, 3 meals, power backup, parking).
   - Hostel rules, gate curfew timings (*e.g., 10:30 PM curfew, mess hours*).
   - Safety & security breakdown (*24/7 CCTV, biometric access, resident warden*).
   - Student reviews with star breakdown.
   - Property Manager / Warden contact card with direct **Call** and **Chat** buttons.

5. **Side-by-Side Property Comparison** (`CompareScreen.kt`):
   - Compare up to 3 hostels simultaneously.
   - Comparison matrix: Monthly rent, security deposit, campus distance, ratings, meals included, high-speed Wi-Fi, washing machine, 24/7 water, CCTV/biometric security, and gate curfew.

6. **Saved Shortlist** (`FavoritesScreen.kt`):
   - One-tap heart bookmarking on any hostel card.
   - Easy management, comparison launcher, and quick contact.

7. **Direct Student-to-Warden Chat UI** (`ChatScreen.kt`):
   - In-app chat interface between student and verified property manager.
   - Quick inquiry chips: *"Is 2-sharing bed vacant?", "Can I schedule a visit tomorrow?", "Is food included in rent?", "What is deposit policy?"*
   - Interactive message exchange with simulated warden responses.

8. **Nearby Facilities & Essentials** (`NearbyFacilitiesScreen.kt`):
   - Filter by categories: *Hospitals & Medical, TSRTC Bus Stops, Metro Stations, 24/7 Pharmacies, Student Mess/Food Courts, Supermarkets*.
   - Proximity indicators, walking minutes, contact details, and direct map navigation.

9. **Roommate Matching Discovery** (`RoommatesScreen.kt`):
   - Student profiles matching campus, branch, and hometown.
   - Sleep schedule habits, sharing preference, and lifestyle bio.
   - "Connect as Roommate" interactive connection requests.

---

## 🛠 Project Structure

```
mall reddy hackthon/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/stayora/app/
│       │   ├── MainActivity.kt
│       │   ├── StayoraApp.kt
│       │   ├── data/
│       │   │   ├── model/ (Property, RoommateProfile, UserPreferences, NearbyFacility, ChatMessage, FilterState)
│       │   │   └── repository/StayoraRepository.kt
│       │   └── ui/
│       │       ├── theme/ (Color.kt, Theme.kt, Type.kt)
│       │       ├── navigation/Screen.kt
│       │       ├── components/ (StayoraBottomBar, PropertyCard, AmenityChip, CompatibilityBadge)
│       │       └── screens/ (Onboarding, Home, Search, Detail, Compare, Favorites, Chat, Facilities, Roommates)
│       └── res/values/ (strings.xml, colors.xml, themes.xml)
├── build.gradle.kts
├── settings.gradle.kts
├── gradle/libs.versions.toml
├── preview/
│   ├── index.html          # Interactive mobile simulator for live jury demo
│   ├── styles.css          # Jetpack Compose purple & white styling
│   ├── app.js              # Fully interactive state engine & router
│   ├── start_server.ps1    # Lightweight local server
│   └── start_preview.bat   # One-click Windows demo launcher
└── README.md
```

---

## 🚀 How to Run

### Option 1: Live Interactive Demo (Instant Hackathon Presentation)
Double-click `preview/start_preview.bat` (or run `powershell -ExecutionPolicy Bypass -File preview/start_server.ps1 -Port 8085`) and navigate to:
```
http://localhost:8085/
```
You can test the entire app interactively with mobile frame or fullscreen toggle.

### Option 2: Android Studio
1. Open **Android Studio** (Hedgehog / Iguana / Jellyfish or later).
2. Choose **Open Project** and select this directory (`mall reddy hackthon`).
3. Sync Gradle and run on an Android Device or Emulator running Android API 26+.
