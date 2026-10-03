// Stayora Interactive Mobile Simulator Engine
// Matches the Kotlin Jetpack Compose Stayora Architecture

const STATE = {
  currentView: 'home', // 'onboarding', 'home', 'search', 'detail', 'compare', 'favorites', 'chat', 'facilities', 'roommates'
  selectedPropertyId: 'prop_1',
  userPreferences: {
    college: 'Malla Reddy University (MRUH)',
    branch: 'B.Tech CSE',
    roommateType: 'Same College & Branch',
    personalities: ['Quiet / Study-focused', 'Night Owl'],
    minBudget: 4500,
    maxBudget: 9000,
    maxDistanceKm: 3.0,
    preferredSharing: 2,
    requiredAmenities: ['Wi-Fi', 'Washing Machine', '24-Hour Water', 'Security / CCTV', 'Meals / Food'],
    moveInDate: 'Within 2 Weeks'
  },
  filters: {
    searchQuery: '',
    minBudget: 3000,
    maxBudget: 15000,
    maxDistance: 6.0,
    gender: 'All',
    sharing: [],
    amenities: [],
    minRating: 0,
    onlyAvailable: false,
    sortBy: 'recommended'
  },
  properties: [
    {
      id: "prop_1",
      title: "Stanza Living Greenfield Scholars PG",
      type: "Boys Hostel",
      monthlyRent: 6800,
      deposit: 10000,
      distanceKm: 0.6,
      travelTimeMinutes: 7,
      travelMode: "walk",
      address: "Plot 42, Maisammaguda, Near Malla Reddy Engg College Gate 2",
      nearbyUniversity: "Malla Reddy University (MRUH)",
      rating: 4.8,
      reviewCount: 124,
      availabilityStatus: "2 Beds Vacant in 2-Sharing",
      sharingOptions: [
        { sharingType: 1, title: "Private 1-Sharing Room", rentPerBed: 12500, deposit: 15000, attached: true, vacant: 1 },
        { sharingType: 2, title: "Spacious 2-Sharing AC", rentPerBed: 6800, deposit: 10000, attached: true, vacant: 2 },
        { sharingType: 4, title: "Budget 4-Sharing Non-AC", rentPerBed: 4800, deposit: 7000, attached: false, vacant: 3 }
      ],
      amenities: [
        "High-Speed Wi-Fi (150 Mbps)",
        "Automatic Washing Machine",
        "24/7 Water & Hot Geyser",
        "3 Home-Style Meals (Unlimited)",
        "Biometric & CCTV Security",
        "Daily Room Housekeeping",
        "Silent Study Desks",
        "Covered Two-Wheeler Parking"
      ],
      rules: [
        "Main gate curfew: 10:30 PM strictly",
        "Visitors allowed in lounge area until 7:30 PM",
        "No smoking or alcohol on premises",
        "Silent study hours after 11:00 PM"
      ],
      timings: "Curfew 10:30 PM • Mess: 7:30-9:30 AM, 12:30-2:30 PM, 7:30-9:45 PM",
      securityDetails: [
        "24/7 Monitored CCTV with 30-day backup",
        "Resident male warden on ground floor",
        "Biometric turnstile access"
      ],
      ownerName: "Rajeshwar Rao",
      ownerRole: "General Manager",
      ownerPhone: "+91 98490 23411",
      ownerVerified: true,
      imageUrl: "https://images.unsplash.com/photo-1555854877-bab0e564b8d5?auto=format&fit=crop&w=800&q=80",
      isFavorite: true,
      matchScore: 96,
      reviews: [
        { name: "Sai Krishna (CSE 3rd Yr)", college: "Malla Reddy University", rating: 5.0, date: "2 weeks ago", comment: "Super close to campus. Wi-Fi is blazing fast and food tastes like homemade curries." },
        { name: "Aditya Varma (ECE 2nd Yr)", college: "MRCE", rating: 4.5, date: "1 month ago", comment: "Very peaceful for coding and exam prep. Washing machines are easily accessible." }
      ]
    },
    {
      id: "prop_2",
      title: "Sri Sai Balaji Elite Residency & PG",
      type: "Boys Hostel",
      monthlyRent: 5500,
      deposit: 7000,
      distanceKm: 1.1,
      travelTimeMinutes: 12,
      travelMode: "walk",
      address: "Road No. 3, Kompally Bypass, Near Dulapally Junction",
      nearbyUniversity: "Malla Reddy University & MLRIT",
      rating: 4.6,
      reviewCount: 89,
      availabilityStatus: "Filling Fast • 3 Beds Left",
      sharingOptions: [
        { sharingType: 2, title: "Deluxe 2-Sharing", rentPerBed: 7000, deposit: 9000, attached: true, vacant: 1 },
        { sharingType: 3, title: "Comfort 3-Sharing", rentPerBed: 5800, deposit: 7500, attached: true, vacant: 2 },
        { sharingType: 4, title: "Standard 4-Sharing", rentPerBed: 5200, deposit: 6500, attached: false, vacant: 4 }
      ],
      amenities: [
        "Wi-Fi with Dual Router Backup",
        "24-Hour Hot & Cold Water",
        "Washing Machine on Every Floor",
        "South Indian & North Indian Meals",
        "Gym & Fitness Zone",
        "24/7 Security Guard"
      ],
      rules: [
        "Curfew 10:00 PM for fresher students",
        "Room cleaning alternate days",
        "No outside overnight guests without gate pass"
      ],
      timings: "Curfew: 10:00 PM • Food timings strictly followed",
      securityDetails: [
        "Physical security guard at entrance 24/7",
        "Visitor logbook & parent phone verification"
      ],
      ownerName: "K. Venkat Reddy",
      ownerRole: "Owner",
      ownerPhone: "+91 94401 88921",
      ownerVerified: true,
      imageUrl: "https://images.unsplash.com/photo-1595526114035-0d45ed16cfbf?auto=format&fit=crop&w=800&q=80",
      isFavorite: false,
      matchScore: 91,
      reviews: [
        { name: "Praveen Kumar", college: "Malla Reddy Pharmacy", rating: 4.5, date: "3 weeks ago", comment: "Affordable rent for students. Owner Venkat Reddy sir is very supportive." }
      ]
    },
    {
      id: "prop_3",
      title: "Serene Oasis Luxury Girls PG & Hostel",
      type: "Girls Hostel",
      monthlyRent: 7200,
      deposit: 12000,
      distanceKm: 0.8,
      travelTimeMinutes: 9,
      travelMode: "walk",
      address: "Behind Malla Reddy Women's College, Maisammaguda Main Rd",
      nearbyUniversity: "Malla Reddy University & MREC for Women",
      rating: 4.9,
      reviewCount: 142,
      availabilityStatus: "Only 1 Bed Left in 2-Sharing",
      sharingOptions: [
        { sharingType: 2, title: "Executive 2-Sharing AC", rentPerBed: 7800, deposit: 12000, attached: true, vacant: 1 },
        { sharingType: 3, title: "Spacious 3-Sharing", rentPerBed: 6600, deposit: 10000, attached: true, vacant: 2 },
        { sharingType: 4, title: "Cozy 4-Sharing", rentPerBed: 5600, deposit: 8000, attached: true, vacant: 3 }
      ],
      amenities: [
        "Ultra-Secure Biometric Access",
        "Female Security Guards 24/7",
        "Hygienic 3 Meals + Evening Snacks",
        "Automatic Washing Machines",
        "Air Conditioning in all rooms",
        "High-Speed Fiber Wi-Fi",
        "Study Library Room"
      ],
      rules: [
        "Strict curfew at 9:30 PM",
        "Parents and female guardians only allowed in lobby",
        "Zero ragging policy"
      ],
      timings: "Curfew: 9:30 PM • Dining: Breakfast 7:30AM, Snacks 5PM, Dinner 8PM",
      securityDetails: [
        "Full perimeter CCTV & gate registration",
        "Two resident female wardens",
        "Emergency SOS siren"
      ],
      ownerName: "Mrs. Sunitha Devi",
      ownerRole: "Chief Warden",
      ownerPhone: "+91 97011 55432",
      ownerVerified: true,
      imageUrl: "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?auto=format&fit=crop&w=800&q=80",
      isFavorite: false,
      matchScore: 88,
      reviews: [
        { name: "Meghana R.", college: "B.Tech IT (Final Year)", rating: 5.0, date: "1 month ago", comment: "Safest hostel near campus. Wardens take care just like family members." }
      ]
    },
    {
      id: "prop_4",
      title: "CampusEdge Student Living Studio & 2BHK",
      type: "Bachelor Flat",
      monthlyRent: 8500,
      deposit: 15000,
      distanceKm: 1.8,
      travelTimeMinutes: 6,
      travelMode: "bike",
      address: "Kompally Green Valley, Near Cineplanet & Suchitra",
      nearbyUniversity: "Malla Reddy University & BITS Pilani",
      rating: 4.7,
      reviewCount: 67,
      availabilityStatus: "Available Immediately",
      sharingOptions: [
        { sharingType: 2, title: "Master Bedroom (2 Sharing)", rentPerBed: 8500, deposit: 15000, attached: true, vacant: 2 },
        { sharingType: 1, title: "Private Single Room", rentPerBed: 14000, deposit: 20000, attached: true, vacant: 1 }
      ],
      amenities: [
        "Modular Kitchen with Gas & Fridge",
        "Fully Furnished Living Room & TV",
        "Automatic Washing Machine",
        "High-Speed Wi-Fi",
        "Gated Community with 24h Guard",
        "Covered Car & Bike Parking"
      ],
      rules: [
        "No loud parties after 11:30 PM",
        "Guests allowed with gate pass",
        "Individual electricity meter billing"
      ],
      timings: "No curfew • 24/7 Gated Entry",
      securityDetails: [
        "Gated society entrance with MyGate app",
        "CCTV monitoring on all floors"
      ],
      ownerName: "Vikram Teja",
      ownerRole: "Flat Partner / Owner",
      ownerPhone: "+91 91210 99876",
      ownerVerified: true,
      imageUrl: "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?auto=format&fit=crop&w=800&q=80",
      isFavorite: false,
      matchScore: 85,
      reviews: [
        { name: "Karthik N.", college: "AI & ML Student", rating: 4.5, date: "2 months ago", comment: "Best flat for coders and senior students. Complete freedom with great amenities." }
      ]
    },
    {
      id: "prop_5",
      title: "Nexus Co-Living & Tech PG",
      type: "Co-ed PG",
      monthlyRent: 7500,
      deposit: 12000,
      distanceKm: 1.4,
      travelTimeMinutes: 5,
      travelMode: "bike",
      address: "Medchal Highway, Opp CMR & Malla Reddy Campuses",
      nearbyUniversity: "Malla Reddy University & CMR College",
      rating: 4.8,
      reviewCount: 95,
      availabilityStatus: "3 Beds Vacant",
      sharingOptions: [
        { sharingType: 2, title: "Co-living 2-Sharing AC", rentPerBed: 7500, deposit: 12000, attached: true, vacant: 2 },
        { sharingType: 3, title: "Co-living 3-Sharing", rentPerBed: 6200, deposit: 9000, attached: false, vacant: 1 }
      ],
      amenities: [
        "Co-working Space with High-Speed LAN",
        "Gaming Zone (PS5 & Pool Table)",
        "Chef-Curated Buffet Meals",
        "Daily Housekeeping & Laundry",
        "24-Hour Power Backup",
        "Rooftop Cafe"
      ],
      rules: [
        "Curfew 11:30 PM weekdays, 12:30 AM weekends",
        "Access card required for entry at all times"
      ],
      timings: "Curfew 11:30 PM • 24/7 Lounge Access",
      securityDetails: [
        "Smart RFID keycard door locks",
        "Professional security team 24/7"
      ],
      ownerName: "Suresh Verma",
      ownerRole: "Community Lead",
      ownerPhone: "+91 93902 44321",
      ownerVerified: true,
      imageUrl: "https://images.unsplash.com/photo-1595526114035-0d45ed16cfbf?auto=format&fit=crop&w=800&q=80",
      isFavorite: false,
      matchScore: 93,
      reviews: [
        { name: "Ananya Sharma", college: "Malla Reddy MBA", rating: 5.0, date: "3 weeks ago", comment: "Feels like a mini tech company campus! The rooftop lounge and coffee are amazing." }
      ]
    }
  ],
  roommates: [
    {
      id: "rm_1",
      name: "Tarun Goud",
      college: "Malla Reddy University",
      branch: "B.Tech CSE (AI & ML)",
      year: "3rd Year",
      hometown: "Nizamabad / Karimnagar",
      traits: ["Quiet / Study-focused", "Night Owl", "Tech Geek"],
      budget: "₹5,500 - ₹7,500",
      sharing: 2,
      compat: 96,
      bio: "Competitive coder and hackathon enthusiast. Mostly study in the evenings, clean room habits, non-smoker.",
      sleep: "Night Owl (1:30 AM)",
      connected: false
    },
    {
      id: "rm_2",
      name: "Rohit Kumar Sharma",
      college: "Malla Reddy University",
      branch: "B.Tech CSE (Core)",
      year: "2nd Year",
      hometown: "Warangal",
      traits: ["Fitness Enthusiast", "Early Riser", "Introvert"],
      budget: "₹5,000 - ₹7,000",
      sharing: 2,
      compat: 92,
      bio: "Gym in the morning, classes till 4 PM, quiet study hours afterwards. Looking for a chilled-out roommate.",
      sleep: "Early Riser (6:00 AM)",
      connected: false
    },
    {
      id: "rm_3",
      name: "Harshita Reddy",
      college: "Malla Reddy Women's College",
      branch: "B.Tech ECE",
      year: "3rd Year",
      hometown: "Hyderabad (East)",
      traits: ["Travel Enthusiast", "Extrovert", "Study-focused"],
      budget: "₹6,000 - ₹8,500",
      sharing: 2,
      compat: 89,
      bio: "Looking for a friendly roommate who values clean space, good food, and occasional weekend outings.",
      sleep: "Flexible (12:00 AM)",
      connected: false
    },
    {
      id: "rm_4",
      name: "Abhishek Rao",
      college: "Malla Reddy Engg College",
      branch: "B.Tech Mechanical",
      year: "4th Year",
      hometown: "Khammam",
      traits: ["Night Owl", "Sports Lover", "Extrovert"],
      budget: "₹4,500 - ₹6,000",
      sharing: 4,
      compat: 84,
      bio: "Cricket player, chill roommate, preparing for campus placements. Easy going and respectful.",
      sleep: "Night Owl (1:00 AM)",
      connected: false
    }
  ],
  facilities: [
    {
      id: "f_1",
      name: "Malla Reddy Narayana Multi-Speciality Hospital",
      category: "Hospital",
      icon: "fa-hospital",
      distanceKm: 0.5,
      travelMinutes: 6,
      mode: "walk",
      address: "Suraram Main Road, Jeedimetla, Hyderabad",
      timing: "24/7 Emergency: 040-23783000"
    },
    {
      id: "f_2",
      name: "Maisammaguda Bus Depot & TSRTC Stop",
      category: "Bus Stop",
      icon: "fa-bus",
      distanceKm: 0.3,
      travelMinutes: 4,
      mode: "walk",
      address: "Opposite Gate 1, Maisammaguda Junction",
      timing: "Buses every 10 mins towards Secunderabad & Medchal"
    },
    {
      id: "f_3",
      name: "Balanagar / JNTU Metro Station",
      category: "Metro Station",
      icon: "fa-train",
      distanceKm: 4.2,
      travelMinutes: 14,
      mode: "bus",
      address: "Red Line Metro Corridor",
      timing: "6:00 AM to 11:00 PM • Direct feeder autos available"
    },
    {
      id: "f_4",
      name: "Apollo Pharmacy 24/7",
      category: "Pharmacy",
      icon: "fa-prescription-bottle-medical",
      distanceKm: 0.4,
      travelMinutes: 5,
      mode: "walk",
      address: "Maisammaguda Main Market",
      timing: "Open 24 Hours • Free doorstep delivery"
    },
    {
      id: "f_5",
      name: "Student Tiffin Center & North/South Mess",
      category: "Food & Mess",
      icon: "fa-utensils",
      distanceKm: 0.2,
      travelMinutes: 2,
      mode: "walk",
      address: "Near MRUH Library Lane",
      timing: "7:00 AM - 11:00 PM • Daily student discounts"
    },
    {
      id: "f_6",
      name: "Reliance Smart Supermarket & Stationery",
      category: "Supermarket",
      icon: "fa-cart-shopping",
      distanceKm: 1.0,
      travelMinutes: 10,
      mode: "walk",
      address: "Kompally Junction Road",
      timing: "8:00 AM - 10:00 PM"
    }
  ],
  compareList: ["prop_1", "prop_2"],
  chats: {
    prop_1: [
      { sender: 'student', text: 'Hi! Is 2-sharing bed vacant for move-in next week?', time: 'Yesterday 4:30 PM' },
      { sender: 'owner', text: 'Hello! Yes, we have 2 vacant beds in the second floor AC room. Both existing roommates are 3rd year CSE students.', time: 'Yesterday 4:45 PM' },
      { sender: 'student', text: 'Great! Does the ₹6,800 rent include 3 times food and Wi-Fi?', time: 'Yesterday 4:50 PM' },
      { sender: 'owner', text: 'Yes, completely all-inclusive! 3 unlimited home-cooked meals, 150 Mbps Wi-Fi, daily housekeeping, and power backup are all included.', time: 'Yesterday 5:02 PM' }
    ],
    prop_2: [
      { sender: 'student', text: 'Hello sir, can I visit tomorrow evening around 5 PM?', time: 'Today 11:00 AM' },
      { sender: 'owner', text: 'Namaste! Yes definitely, call me when you reach the Kompally arch. I will arrange the warden to show you room 204.', time: 'Today 11:15 AM' }
    ]
  }
};

// DOM References
const viewport = document.getElementById('app-viewport');
const bottomNav = document.getElementById('bottom-nav-bar');
const compareBadge = document.getElementById('compare-badge');
const clockElem = document.getElementById('status-clock');
const toggleFrameBtn = document.getElementById('toggle-frame-btn');
const deviceContainer = document.getElementById('device-container');

// Real-time clock
function updateClock() {
  const now = new Date();
  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');
  if (clockElem) clockElem.textContent = `${hours}:${minutes}`;
}
setInterval(updateClock, 1000);
updateClock();

// Toggle Phone Frame for Desktop
if (toggleFrameBtn) {
  toggleFrameBtn.addEventListener('click', () => {
    deviceContainer.classList.toggle('expanded');
    showToast(deviceContainer.classList.contains('expanded') ? 'Expanded View' : 'Phone Frame View');
  });
}

// Show Toast
function showToast(message) {
  const container = document.getElementById('toast-container');
  const toast = document.createElement('div');
  toast.className = 'toast';
  toast.innerHTML = `<i class="fa-solid fa-circle-check" style="color: #A855F7;"></i> ${message}`;
  container.appendChild(toast);
  setTimeout(() => {
    toast.style.opacity = '0';
    setTimeout(() => toast.remove(), 300);
  }, 2200);
}

// Update Compare Badge Count
function updateCompareBadge() {
  const count = STATE.compareList.length;
  if (count > 0) {
    compareBadge.textContent = count;
    compareBadge.style.display = 'block';
  } else {
    compareBadge.style.display = 'none';
  }
}

// Router
function navigate(view, propertyId = null) {
  STATE.currentView = view;
  if (propertyId) STATE.selectedPropertyId = propertyId;

  // Update Bottom Nav active state
  document.querySelectorAll('.nav-tab').forEach(tab => {
    tab.classList.toggle('active', tab.dataset.tab === view);
  });

  // Show/Hide Bottom Nav on specific views
  const noBottomNavViews = ['onboarding', 'chat'];
  bottomNav.style.display = noBottomNavViews.includes(view) ? 'none' : 'flex';

  // Render View
  switch (view) {
    case 'onboarding':
      renderOnboardingView();
      break;
    case 'home':
      renderHomeView();
      break;
    case 'search':
      renderSearchView();
      break;
    case 'detail':
      renderDetailView();
      break;
    case 'compare':
      renderCompareView();
      break;
    case 'favorites':
      renderFavoritesView();
      break;
    case 'chat':
      renderChatView();
      break;
    case 'facilities':
      renderFacilitiesView();
      break;
    case 'roommates':
      renderRoommatesView();
      break;
    default:
      renderHomeView();
  }

  viewport.scrollTop = 0;
  updateCompareBadge();
}

// Bottom Nav click listener
document.querySelectorAll('.nav-tab').forEach(btn => {
  btn.addEventListener('click', () => {
    navigate(btn.dataset.tab);
  });
});

// =========================================================
// 1. ONBOARDING & PREFERENCE WIZARD
// =========================================================
function renderOnboardingView() {
  viewport.innerHTML = `
    <div class="wizard-container">
      <div class="wizard-header">
        <div style="display:flex; justify-content:space-between; align-items:center;">
          <div>
            <h2 style="font-size:22px; font-weight:800; color:var(--charcoal-dark);">Customise Stayora</h2>
            <p style="font-size:12px; color:var(--charcoal-light);">Tell us your preferences to match you with ideal hostels & roommates</p>
          </div>
          <button class="secondary-btn" style="color:var(--purple-primary); border-color:var(--purple-tint); background:none; padding:4px 10px;" onclick="navigate('home')">Skip</button>
        </div>
        <div class="wizard-stepper">
          <div class="stepper-segment active"></div>
          <div class="stepper-segment active"></div>
          <div class="stepper-segment active"></div>
        </div>
      </div>

      <!-- College & Branch -->
      <div class="form-group-card">
        <label><i class="fa-solid fa-graduation-cap" style="color:var(--purple-primary);"></i> Select Your College</label>
        <div class="radio-option-item ${STATE.userPreferences.college.includes('MRUH') ? 'selected' : ''}" onclick="selectPref('college', 'Malla Reddy University (MRUH)')">
          <i class="fa-solid ${STATE.userPreferences.college.includes('MRUH') ? 'fa-circle-dot' : 'fa-circle'}" style="color:var(--purple-primary);"></i>
          Malla Reddy University (MRUH)
        </div>
        <div class="radio-option-item ${STATE.userPreferences.college.includes('MREC') ? 'selected' : ''}" onclick="selectPref('college', 'Malla Reddy Engg College (MREC)')">
          <i class="fa-solid ${STATE.userPreferences.college.includes('MREC') ? 'fa-circle-dot' : 'fa-circle'}" style="color:var(--purple-primary);"></i>
          Malla Reddy Engg College (MREC)
        </div>
        <div class="radio-option-item ${STATE.userPreferences.college.includes('MLRIT') ? 'selected' : ''}" onclick="selectPref('college', 'MLR Institute of Technology')">
          <i class="fa-solid ${STATE.userPreferences.college.includes('MLRIT') ? 'fa-circle-dot' : 'fa-circle'}" style="color:var(--purple-primary);"></i>
          MLR Institute of Technology
        </div>
      </div>

      <!-- Roommate Preference -->
      <div class="form-group-card">
        <label><i class="fa-solid fa-people-arrows" style="color:var(--purple-primary);"></i> Preferred Roommate Type</label>
        <div class="radio-option-item ${STATE.userPreferences.roommateType === 'Same College & Branch' ? 'selected' : ''}" onclick="selectPref('roommateType', 'Same College & Branch')">
          <i class="fa-solid ${STATE.userPreferences.roommateType === 'Same College & Branch' ? 'fa-circle-dot' : 'fa-circle'}" style="color:var(--purple-primary);"></i>
          Same College & Branch
        </div>
        <div class="radio-option-item ${STATE.userPreferences.roommateType === 'Same Village / Hometown' ? 'selected' : ''}" onclick="selectPref('roommateType', 'Same Village / Hometown')">
          <i class="fa-solid ${STATE.userPreferences.roommateType === 'Same Village / Hometown' ? 'fa-circle-dot' : 'fa-circle'}" style="color:var(--purple-primary);"></i>
          Same Village / Hometown
        </div>
        <div class="radio-option-item ${STATE.userPreferences.roommateType === 'No Preference' ? 'selected' : ''}" onclick="selectPref('roommateType', 'No Preference')">
          <i class="fa-solid ${STATE.userPreferences.roommateType === 'No Preference' ? 'fa-circle-dot' : 'fa-circle'}" style="color:var(--purple-primary);"></i>
          No Preference (Open to all students)
        </div>
      </div>

      <!-- Budget Range -->
      <div class="form-group-card">
        <label><i class="fa-solid fa-indian-rupee-sign" style="color:var(--purple-primary);"></i> Monthly Budget Ceiling</label>
        <div class="slider-container">
          <div class="slider-val-display">
            <span>Budget:</span>
            <span class="val" id="wizard-budget-val">Up to ₹${STATE.userPreferences.maxBudget} / mo</span>
          </div>
          <input type="range" min="4000" max="15000" step="500" value="${STATE.userPreferences.maxBudget}" oninput="updateWizardBudget(this.value)">
        </div>
      </div>

      <!-- Max Distance -->
      <div class="form-group-card">
        <label><i class="fa-solid fa-person-walking" style="color:var(--purple-primary);"></i> Max Campus Distance</label>
        <div class="slider-container">
          <div class="slider-val-display">
            <span>Distance:</span>
            <span class="val" id="wizard-dist-val">${STATE.userPreferences.maxDistanceKm} km (${Math.round(STATE.userPreferences.maxDistanceKm * 10)} min walk)</span>
          </div>
          <input type="range" min="0.5" max="6.0" step="0.5" value="${STATE.userPreferences.maxDistanceKm}" oninput="updateWizardDist(this.value)">
        </div>
      </div>

      <!-- Sharing Type -->
      <div class="form-group-card">
        <label><i class="fa-solid fa-bed" style="color:var(--purple-primary);"></i> Room Sharing Preference</label>
        <div style="display:flex; gap:10px;">
          <div class="radio-option-item ${STATE.userPreferences.preferredSharing === 2 ? 'selected' : ''}" style="flex:1; justify-content:center;" onclick="selectPref('preferredSharing', 2)">
            2-Sharing (Double)
          </div>
          <div class="radio-option-item ${STATE.userPreferences.preferredSharing === 4 ? 'selected' : ''}" style="flex:1; justify-content:center;" onclick="selectPref('preferredSharing', 4)">
            4-Sharing (Budget)
          </div>
        </div>
      </div>

      <!-- Submit -->
      <div class="wizard-footer-buttons">
        <button class="btn-primary" style="width:100%; justify-content:center;" onclick="savePreferencesAndFind()">
          Save Preferences & Find My Stay ✨
        </button>
      </div>
    </div>
  `;
}

window.selectPref = function(key, val) {
  STATE.userPreferences[key] = val;
  renderOnboardingView();
};

window.updateWizardBudget = function(val) {
  STATE.userPreferences.maxBudget = parseInt(val);
  document.getElementById('wizard-budget-val').textContent = `Up to ₹${val} / mo`;
};

window.updateWizardDist = function(val) {
  STATE.userPreferences.maxDistanceKm = parseFloat(val);
  document.getElementById('wizard-dist-val').textContent = `${val} km (${Math.round(val * 10)} min walk)`;
};

window.savePreferencesAndFind = function() {
  showToast('Preferences updated! Re-scoring matched hostels.');
  navigate('home');
};

// =========================================================
// 2. HOME SCREEN
// =========================================================
function renderHomeView() {
  const prefs = STATE.userPreferences;
  const props = STATE.properties;

  viewport.innerHTML = `
    <!-- Hero Top Bar -->
    <div class="app-header-hero">
      <div class="hero-top-row">
        <div class="campus-pill" onclick="navigate('onboarding')">
          <i class="fa-solid fa-location-dot"></i>
          <span>${prefs.college.split(' ')[0]} ${prefs.college.split(' ')[1] || ''}</span>
          <i class="fa-solid fa-sliders" style="font-size:10px; margin-left:2px;"></i>
        </div>
        <div class="app-brand-title">STAYORA</div>
      </div>

      <h2 class="hero-headline">Find Your Ideal Hostel<br>& Compatible Roommates</h2>
      <p class="hero-subheadline">Matched for ${prefs.branch} • ${prefs.preferredSharing}-Sharing • Under ₹${prefs.maxBudget}</p>

      <div class="search-trigger-box" onclick="navigate('search')">
        <i class="fa-solid fa-magnifying-glass"></i>
        <span class="placeholder-text">Search hostels near Maisammaguda, Kompally...</span>
        <div class="filter-icon-btn"><i class="fa-solid fa-filter"></i></div>
      </div>
    </div>

    <!-- Quick Category Chips -->
    <div class="category-chips-row">
      <div class="cat-chip active" onclick="filterCategory(this, 'All')">All Accommodations</div>
      <div class="cat-chip" onclick="filterCategory(this, 'Boys Hostel')">Boys Hostel</div>
      <div class="cat-chip" onclick="filterCategory(this, 'Girls Hostel')">Girls Hostel</div>
      <div class="cat-chip" onclick="filterCategory(this, 'Co-ed PG')">Co-ed PG</div>
      <div class="cat-chip" onclick="filterCategory(this, 'Bachelor Flat')">Bachelor Flat</div>
    </div>

    <!-- Roommates Spotlight -->
    <div class="section-header">
      <div>
        <h3>Compatible Roommates</h3>
        <p>Students looking for flatmates in your college</p>
      </div>
      <a class="section-link" onclick="navigate('roommates')">View All</a>
    </div>

    <div class="roommate-carousel">
      ${STATE.roommates.map(rm => `
        <div class="roommate-card" onclick="navigate('roommates')">
          <div class="roommate-top">
            <div class="compat-badge"><i class="fa-solid fa-wand-magic-sparkles"></i> ${rm.compat}% Match</div>
            <span style="font-size:11px; color:var(--charcoal-light);">${rm.sharing} Sharing</span>
          </div>
          <div class="roommate-name">${rm.name}</div>
          <div class="roommate-meta">${rm.branch} • ${rm.year}</div>
          <div class="roommate-origin">From ${rm.hometown.split('/')[0]}</div>
          <div class="roommate-traits">
            ${rm.traits.slice(0, 2).map(t => `<span class="trait-pill">${t}</span>`).join('')}
          </div>
        </div>
      `).join('')}
    </div>

    <!-- Recommended Hostels Section -->
    <div class="section-header">
      <div>
        <h3>Recommended for You</h3>
        <p>Verified security, accurate deposits & close commute</p>
      </div>
      <span style="font-size:12px; font-weight:700; color:var(--purple-primary);">${props.length} Found</span>
    </div>

    <div class="property-list" id="home-property-list">
      ${props.map(p => renderPropertyCardHtml(p)).join('')}
    </div>
  `;
}

window.filterCategory = function(elem, cat) {
  document.querySelectorAll('.cat-chip').forEach(c => c.classList.remove('active'));
  elem.classList.add('active');

  const container = document.getElementById('home-property-list');
  const filtered = cat === 'All' ? STATE.properties : STATE.properties.filter(p => p.type.toLowerCase().includes(cat.toLowerCase()));
  container.innerHTML = filtered.map(p => renderPropertyCardHtml(p)).join('');
};

// Helper: Card HTML
function renderPropertyCardHtml(p) {
  const isCompared = STATE.compareList.includes(p.id);
  return `
    <div class="property-card" onclick="navigate('detail', '${p.id}')">
      <div class="card-hero-banner" style="background-image: url('${p.imageUrl}');">
        <div class="card-hero-overlay"></div>
        <div class="card-hero-top">
          <div class="compat-badge"><i class="fa-solid fa-wand-magic-sparkles"></i> ${p.matchScore}% Match</div>
          <div style="display:flex; gap:6px;">
            <button class="action-circle-btn compare ${isCompared ? 'active' : ''}" onclick="event.stopPropagation(); toggleCompareItem('${p.id}')" title="Compare">
              <i class="fa-solid fa-arrow-right-arrow-left"></i>
            </button>
            <button class="action-circle-btn favorite ${p.isFavorite ? 'active' : ''}" onclick="event.stopPropagation(); toggleFavItem('${p.id}')" title="Save">
              <i class="fa-${p.isFavorite ? 'solid' : 'regular'} fa-heart"></i>
            </button>
          </div>
        </div>
        <div class="card-hero-bottom">
          <span class="property-type-tag">${p.type}</span>
          <div class="property-title-text">${p.title}</div>
        </div>
      </div>

      <div class="card-content-body">
        <div class="card-rent-row">
          <div class="rent-highlight">₹${p.monthlyRent} <span>/ month</span></div>
          <div class="deposit-snippet">Deposit: ₹${p.deposit}</div>
        </div>

        <div class="commute-snippet">
          <i class="fa-solid fa-person-walking"></i>
          <span>${p.distanceKm} km (${p.travelTimeMinutes} min walk to campus)</span>
        </div>

        <div class="address-snippet">${p.address}</div>

        <div class="sharing-chips-row">
          ${p.sharingOptions.slice(0, 2).map(opt => `<span class="sharing-pill">${opt.sharingType} Sharing: ₹${opt.rentPerBed}</span>`).join('')}
        </div>

        <div class="amenity-chips-row">
          ${p.amenities.slice(0, 3).map(am => `<span class="amenity-chip"><i class="fa-solid fa-circle-check"></i> ${am}</span>`).join('')}
        </div>

        <div class="card-footer-row">
          <div class="rating-badge">
            <i class="fa-solid fa-star"></i> ${p.rating} <span>(${p.reviewCount})</span>
          </div>
          ${p.ownerVerified ? `<div class="verified-badge"><i class="fa-solid fa-circle-check"></i> Verified Owner</div>` : ''}
        </div>
      </div>
    </div>
  `;
}

window.toggleFavItem = function(id) {
  const p = STATE.properties.find(x => x.id === id);
  if (p) {
    p.isFavorite = !p.isFavorite;
    showToast(p.isFavorite ? 'Added to Saved Hostels ❤️' : 'Removed from Saved');
    navigate(STATE.currentView, STATE.selectedPropertyId);
  }
};

window.toggleCompareItem = function(id) {
  const idx = STATE.compareList.indexOf(id);
  if (idx >= 0) {
    STATE.compareList.splice(idx, 1);
    showToast('Removed from comparison');
  } else {
    if (STATE.compareList.length >= 3) {
      showToast('Maximum 3 properties can be compared');
      return;
    }
    STATE.compareList.push(id);
    showToast(`Added to comparison matrix (${STATE.compareList.length}/3)`);
  }
  updateCompareBadge();
  navigate(STATE.currentView, STATE.selectedPropertyId);
};

// =========================================================
// 3. SEARCH & FILTERS SCREEN
// =========================================================
function renderSearchView() {
  const f = STATE.filters;
  const filtered = STATE.properties.filter(p => {
    const matchesQuery = !f.searchQuery || p.title.toLowerCase().includes(f.searchQuery.toLowerCase()) || p.address.toLowerCase().includes(f.searchQuery.toLowerCase());
    const matchesRent = p.monthlyRent >= f.minBudget && p.monthlyRent <= f.maxBudget;
    const matchesDist = p.distanceKm <= f.maxDistance;
    const matchesGender = f.gender === 'All' || (f.gender === 'Boys' && p.type.includes('Boys')) || (f.gender === 'Girls' && p.type.includes('Girls')) || (f.gender === 'Co-ed' && p.type.includes('Co-ed'));
    const matchesRating = p.rating >= f.minRating;
    return matchesQuery && matchesRent && matchesDist && matchesGender && matchesRating;
  });

  viewport.innerHTML = `
    <div class="search-header-bar">
      <button class="back-btn" onclick="navigate('home')"><i class="fa-solid fa-arrow-left"></i></button>
      <input type="text" class="search-input-field" placeholder="Search hostel, area, facility..." value="${f.searchQuery}" oninput="searchQueryChange(this.value)">
      <button class="filter-trigger-btn" onclick="openFilterSheet()">
        <i class="fa-solid fa-sliders"></i>
      </button>
    </div>

    <div style="padding:10px 16px; display:flex; justify-content:space-between; align-items:center;">
      <span style="font-size:12px; font-weight:600; color:var(--charcoal-light);">${filtered.length} Accommodations Match</span>
      <span style="font-size:11.5px; font-weight:700; color:var(--purple-primary); cursor:pointer;" onclick="resetSearchFilters()">Reset Filters</span>
    </div>

    <div class="property-list">
      ${filtered.length > 0 ? filtered.map(p => renderPropertyCardHtml(p)).join('') : `
        <div style="text-align:center; padding:50px 20px; color:var(--charcoal-light);">
          <i class="fa-solid fa-search" style="font-size:40px; margin-bottom:12px; color:var(--gray-muted);"></i>
          <h4 style="color:var(--charcoal-dark); margin-bottom:4px;">No matching hostels found</h4>
          <p style="font-size:12px;">Try adjusting your budget or distance slider in filters.</p>
        </div>
      `}
    </div>

    <div id="filter-sheet-container"></div>
  `;
}

window.searchQueryChange = function(query) {
  STATE.filters.searchQuery = query;
  renderSearchView();
};

window.resetSearchFilters = function() {
  STATE.filters = {
    searchQuery: '',
    minBudget: 3000,
    maxBudget: 15000,
    maxDistance: 6.0,
    gender: 'All',
    sharing: [],
    amenities: [],
    minRating: 0,
    onlyAvailable: false,
    sortBy: 'recommended'
  };
  showToast('Filters reset to default');
  renderSearchView();
};

window.openFilterSheet = function() {
  const f = STATE.filters;
  const container = document.getElementById('filter-sheet-container');
  container.innerHTML = `
    <div class="filter-modal-backdrop" onclick="closeFilterSheet()">
      <div class="filter-modal-sheet" onclick="event.stopPropagation()">
        <div style="display:flex; justify-content:space-between; align-items:center;">
          <h3 style="font-size:17px; font-weight:800; color:var(--charcoal-dark);">Filter Accommodations</h3>
          <button style="border:none; background:none; font-size:18px; cursor:pointer;" onclick="closeFilterSheet()"><i class="fa-solid fa-xmark"></i></button>
        </div>

        <div>
          <label style="font-size:13px; font-weight:700;">Resident Gender</label>
          <div style="display:flex; gap:8px; margin-top:8px;">
            ${['All', 'Boys', 'Girls', 'Co-ed'].map(g => `
              <div class="cat-chip ${f.gender === g ? 'active' : ''}" onclick="setFilterGender('${g}')">${g}</div>
            `).join('')}
          </div>
        </div>

        <div>
          <div style="display:flex; justify-content:space-between; font-size:13px; font-weight:700;">
            <span>Max Monthly Rent</span>
            <span style="color:var(--purple-primary);">Up to ₹${f.maxBudget}</span>
          </div>
          <input type="range" min="4000" max="15000" step="500" value="${f.maxBudget}" oninput="setFilterBudget(this.value)" style="margin-top:8px;">
        </div>

        <div>
          <div style="display:flex; justify-content:space-between; font-size:13px; font-weight:700;">
            <span>Max Distance to Campus</span>
            <span style="color:var(--purple-primary);">${f.maxDistance} km</span>
          </div>
          <input type="range" min="0.5" max="6.0" step="0.5" value="${f.maxDistance}" oninput="setFilterDist(this.value)" style="margin-top:8px;">
        </div>

        <div>
          <label style="font-size:13px; font-weight:700;">Minimum Rating</label>
          <div style="display:flex; gap:8px; margin-top:8px;">
            ${[0, 4.0, 4.5, 4.8].map(r => `
              <div class="cat-chip ${f.minRating === r ? 'active' : ''}" onclick="setFilterRating(${r})">${r === 0 ? 'Any' : r + '+ ⭐'}</div>
            `).join('')}
          </div>
        </div>

        <button class="btn-primary" style="justify-content:center; width:100%;" onclick="closeFilterSheet()">
          Apply Filters
        </button>
      </div>
    </div>
  `;
};

window.closeFilterSheet = function() {
  document.getElementById('filter-sheet-container').innerHTML = '';
  renderSearchView();
};

window.setFilterGender = function(g) {
  STATE.filters.gender = g;
  document.querySelectorAll('.filter-modal-sheet .cat-chip').forEach(c => c.classList.remove('active'));
  openFilterSheet();
};

window.setFilterBudget = function(val) {
  STATE.filters.maxBudget = parseInt(val);
  openFilterSheet();
};

window.setFilterDist = function(val) {
  STATE.filters.maxDistance = parseFloat(val);
  openFilterSheet();
};

window.setFilterRating = function(r) {
  STATE.filters.minRating = r;
  openFilterSheet();
};

// =========================================================
// 4. PROPERTY DETAIL SCREEN
// =========================================================
function renderDetailView() {
  const p = STATE.properties.find(x => x.id === STATE.selectedPropertyId) || STATE.properties[0];
  const isCompared = STATE.compareList.includes(p.id);

  viewport.innerHTML = `
    <!-- Top Hero Banner -->
    <div class="detail-hero-box" style="background-image: url('${p.imageUrl}');">
      <div class="detail-nav-actions">
        <button class="circle-nav-btn" onclick="navigate('home')"><i class="fa-solid fa-arrow-left"></i></button>
        <div style="display:flex; gap:8px;">
          <button class="circle-nav-btn ${isCompared ? 'active' : ''}" onclick="toggleCompareItem('${p.id}')" title="Compare">
            <i class="fa-solid fa-arrow-right-arrow-left" style="color: ${isCompared ? 'var(--purple-primary)' : 'inherit'};"></i>
          </button>
          <button class="circle-nav-btn" onclick="toggleFavItem('${p.id}')" title="Save">
            <i class="fa-${p.isFavorite ? 'solid' : 'regular'} fa-heart" style="color: ${p.isFavorite ? 'var(--coral-badge)' : 'inherit'};"></i>
          </button>
        </div>
      </div>

      <div class="detail-hero-info">
        <div style="display:flex; gap:8px; margin-bottom:4px;">
          <span class="sharing-pill" style="background:var(--purple-primary); color:#FFFFFF;">${p.type}</span>
          <div class="compat-badge"><i class="fa-solid fa-wand-magic-sparkles"></i> ${p.matchScore}% Match</div>
        </div>
        <h2 style="font-size:20px; font-weight:800; color:#FFFFFF;">${p.title}</h2>
        <p style="font-size:11.5px; color:var(--purple-tint);"><i class="fa-solid fa-circle-check" style="color:var(--verified-green);"></i> ${p.availabilityStatus}</p>
      </div>
    </div>

    <!-- Body Information Cards -->
    <div class="detail-body-container">
      <!-- Distance & Nearby Facilities Link -->
      <div class="detail-card" style="display:flex; justify-content:space-between; align-items:center;">
        <div>
          <div style="font-size:13.5px; font-weight:700; color:var(--charcoal-dark);">
            <i class="fa-solid fa-person-walking" style="color:var(--electric-blue);"></i> ${p.travelTimeMinutes} min ${p.travelMode} to campus
          </div>
          <div style="font-size:11.5px; color:var(--charcoal-light); margin-top:2px;">
            ${p.distanceKm} km from ${p.nearbyUniversity}
          </div>
        </div>
        <button class="btn-secondary" style="padding:6px 12px; font-size:12px;" onclick="navigate('facilities', '${p.id}')">
          Nearby Facilities 🏥
        </button>
      </div>

      <!-- Sharing Options Breakdown -->
      <div class="detail-card">
        <h4>Room Sharing Options & Pricing</h4>
        ${p.sharingOptions.map(opt => `
          <div class="sharing-table-row">
            <div>
              <div style="font-size:13px; font-weight:700; color:var(--charcoal-dark);">${opt.title}</div>
              <div style="font-size:11px; color:var(--charcoal-light);">${opt.attached ? 'Attached Washroom' : 'Common Washroom'} • ${opt.vacant} beds vacant</div>
            </div>
            <div style="text-align:right;">
              <div style="font-size:15px; font-weight:800; color:var(--purple-primary);">₹${opt.rentPerBed}<span style="font-size:10px; font-weight:500;">/mo</span></div>
              <div style="font-size:10.5px; color:var(--charcoal-medium);">Deposit: ₹${opt.deposit}</div>
            </div>
          </div>
        `).join('')}
      </div>

      <!-- Amenities Grid -->
      <div class="detail-card">
        <h4>Included Amenities & Facilities</h4>
        <div style="display:flex; gap:8px; flex-wrap:wrap;">
          ${p.amenities.map(am => `
            <div class="amenity-chip" style="background:var(--purple-tint); border-color:var(--purple-tint); color:var(--purple-dark); padding:6px 10px;">
              <i class="fa-solid fa-circle-check"></i> ${am}
            </div>
          `).join('')}
        </div>
      </div>

      <!-- Rules & Timings -->
      <div class="detail-card">
        <h4>Hostel Rules & Curfew Timings</h4>
        <div class="curfew-timing-banner">
          <i class="fa-regular fa-clock"></i>
          <span>${p.timings}</span>
        </div>
        ${p.rules.map(rule => `
          <div class="bullet-rule-item">
            <i class="fa-solid fa-check"></i>
            <span>${rule}</span>
          </div>
        `).join('')}
      </div>

      <!-- Security Details -->
      <div class="detail-card">
        <h4><i class="fa-solid fa-shield-halved" style="color:var(--verified-green);"></i> Safety & Security Standards</h4>
        ${p.securityDetails.map(sec => `
          <div class="bullet-rule-item">
            <i class="fa-solid fa-shield" style="color:var(--verified-green);"></i>
            <span>${sec}</span>
          </div>
        `).join('')}
      </div>

      <!-- Manager / Owner Card -->
      <div class="detail-card">
        <h4>Property Management</h4>
        <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:12px;">
          <div style="display:flex; align-items:center; gap:10px;">
            <div class="owner-chat-avatar">${p.ownerName.charAt(0)}</div>
            <div>
              <div style="font-size:13.5px; font-weight:700;">${p.ownerName} <i class="fa-solid fa-circle-check" style="color:var(--verified-green); font-size:12px;"></i></div>
              <div style="font-size:11.5px; color:var(--charcoal-light);">${p.ownerRole} • ${p.ownerPhone}</div>
            </div>
          </div>
        </div>

        <button class="btn-primary" style="width:100%; justify-content:center; background:var(--purple-card-bg); color:var(--purple-primary); border:1px solid var(--purple-tint);" onclick="navigate('chat', '${p.id}')">
          <i class="fa-regular fa-comment-dots"></i> Message Warden on Stayora
        </button>
      </div>

      <!-- Reviews -->
      <div class="detail-card">
        <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:10px;">
          <h4>Student Reviews</h4>
          <div class="rating-badge"><i class="fa-solid fa-star"></i> ${p.rating} (${p.reviewCount})</div>
        </div>
        ${p.reviews.map(rev => `
          <div style="background:#F8FAFC; border-radius:10px; padding:10px; margin-bottom:8px;">
            <div style="display:flex; justify-content:space-between; font-size:12px; font-weight:700;">
              <span>${rev.name}</span>
              <span style="color:var(--star-gold);">⭐ ${rev.rating}</span>
            </div>
            <div style="font-size:11px; color:var(--charcoal-light); margin-bottom:4px;">${rev.college} • ${rev.date}</div>
            <div style="font-size:12px; color:var(--charcoal-dark);">"${rev.comment}"</div>
          </div>
        `).join('')}
      </div>
    </div>

    <!-- Bottom Sticky Booking & Contact Bar -->
    <div class="detail-bottom-bar">
      <div>
        <div style="font-size:20px; font-weight:800; color:var(--purple-primary);">₹${p.monthlyRent}</div>
        <div style="font-size:11px; color:var(--charcoal-light);">Deposit: ₹${p.deposit}</div>
      </div>
      <div style="display:flex; gap:8px;">
        <button class="btn-secondary" style="padding:10px 14px;" onclick="navigate('chat', '${p.id}')">
          <i class="fa-regular fa-comment-dots"></i> Chat
        </button>
        <button class="btn-primary" style="padding:10px 18px;" onclick="window.open('tel:${p.ownerPhone}')">
          <i class="fa-solid fa-phone"></i> Direct Call
        </button>
      </div>
    </div>
  `;
}

// =========================================================
// 5. COMPARE PROPERTIES SCREEN
// =========================================================
function renderCompareView() {
  const compProps = STATE.properties.filter(p => STATE.compareList.includes(p.id));

  if (compProps.length === 0) {
    viewport.innerHTML = `
      <div style="padding:20px 20px 0;">
        <h2 style="font-size:22px; font-weight:800; color:var(--charcoal-dark);">Compare Properties</h2>
        <p style="font-size:12px; color:var(--charcoal-light);">Side-by-side comparison of rent, deposit, distance & amenities</p>
      </div>
      <div style="text-align:center; padding:80px 20px; color:var(--charcoal-light);">
        <i class="fa-solid fa-arrow-right-arrow-left" style="font-size:46px; margin-bottom:14px; color:var(--purple-accent);"></i>
        <h3 style="color:var(--charcoal-dark); margin-bottom:6px;">No Hostels in Comparison</h3>
        <p style="font-size:12.5px; max-width:280px; margin:0 auto 20px;">
          Tap the compare icon (⇄) on any property card to compare rent, deposit, curfews, and amenities.
        </p>
        <button class="btn-primary" onclick="navigate('home')">Browse Hostels</button>
      </div>
    `;
    return;
  }

  const metrics = [
    { title: 'Monthly Rent', getter: p => `₹${p.monthlyRent} / mo` },
    { title: 'Security Deposit', getter: p => `₹${p.deposit}` },
    { title: 'Campus Distance', getter: p => `${p.distanceKm} km (${p.travelTimeMinutes} min walk)` },
    { title: 'Rating & Reviews', getter: p => `⭐ ${p.rating} (${p.reviewCount})` },
    { title: 'Curfew Timings', getter: p => p.timings.split('•')[0] },
    { title: 'Meals / Food Included', getter: p => p.amenities.some(a => a.includes('Meal') || a.includes('Food')) ? 'Included (3 Meals)' : 'Self / Extra' },
    { title: 'High-Speed Wi-Fi', getter: p => p.amenities.some(a => a.includes('Wi-Fi')) ? 'Yes (Included)' : 'No' },
    { title: 'Washing Machine', getter: p => p.amenities.some(a => a.includes('Washing')) ? 'Available' : 'No' },
    { title: '24/7 Water & Geyser', getter: p => p.amenities.some(a => a.includes('Water')) ? 'Yes (24h)' : 'Limited' },
    { title: 'Biometric / CCTV', getter: p => p.securityDetails.some(s => s.includes('CCTV') || s.includes('Biometric')) ? 'Yes (Full)' : 'Basic' },
    { title: 'Owner Verification', getter: p => p.ownerVerified ? 'Verified Owner' : 'Unverified' }
  ];

  viewport.innerHTML = `
    <div style="padding:20px 20px 8px;">
      <h2 style="font-size:22px; font-weight:800; color:var(--charcoal-dark);">Compare Hostels</h2>
      <p style="font-size:12px; color:var(--charcoal-light);">Comparing ${compProps.length} properties side-by-side</p>
    </div>

    <div class="compare-matrix-table">
      <!-- Headers -->
      <div class="compare-cards-header-row">
        ${compProps.map(p => `
          <div class="compare-header-item">
            <button class="compare-remove-btn" onclick="toggleCompareItem('${p.id}')"><i class="fa-solid fa-xmark"></i></button>
            <span style="font-size:10px; font-weight:700; color:var(--purple-primary);">${p.type}</span>
            <div style="font-size:13px; font-weight:700; margin:4px 0 8px; line-height:1.2;">${p.title}</div>
            <button class="btn-secondary" style="padding:4px 8px; font-size:11px; width:100%;" onclick="navigate('detail', '${p.id}')">View Details</button>
          </div>
        `).join('')}
      </div>

      <!-- Matrix Rows -->
      ${metrics.map(m => `
        <div class="matrix-row-card">
          <div class="matrix-title">${m.title}</div>
          <div class="matrix-values-row">
            ${compProps.map(p => {
              const val = m.getter(p);
              const isGood = val.includes('Yes') || val.includes('Included') || val.includes('Verified');
              return `<div class="matrix-cell ${isGood ? 'good' : ''}">${val}</div>`;
            }).join('')}
          </div>
        </div>
      `).join('')}
    </div>
  `;
}

// =========================================================
// 6. FAVORITES / SAVED SCREEN
// =========================================================
function renderFavoritesView() {
  const favs = STATE.properties.filter(p => p.isFavorite);

  if (favs.length === 0) {
    viewport.innerHTML = `
      <div style="padding:20px 20px 0;">
        <h2 style="font-size:22px; font-weight:800; color:var(--charcoal-dark);">Saved Hostels</h2>
        <p style="font-size:12px; color:var(--charcoal-light);">Your shortlisted properties</p>
      </div>
      <div style="text-align:center; padding:80px 20px; color:var(--charcoal-light);">
        <i class="fa-solid fa-heart" style="font-size:46px; margin-bottom:14px; color:var(--coral-badge); opacity:0.6;"></i>
        <h3 style="color:var(--charcoal-dark); margin-bottom:6px;">Your Shortlist is Empty</h3>
        <p style="font-size:12.5px; max-width:280px; margin:0 auto 20px;">
          Tap the heart icon on any hostel or PG to bookmark it for quick access and comparison.
        </p>
        <button class="btn-primary" onclick="navigate('home')">Explore Recommendations</button>
      </div>
    `;
    return;
  }

  viewport.innerHTML = `
    <div style="padding:20px 20px 10px;">
      <h2 style="font-size:22px; font-weight:800; color:var(--charcoal-dark);">Saved Hostels</h2>
      <p style="font-size:12px; color:var(--charcoal-light);">${favs.length} hostels shortlisted by you</p>
    </div>

    <div class="property-list">
      ${favs.map(p => renderPropertyCardHtml(p)).join('')}
    </div>
  `;
}

// =========================================================
// 7. CHAT SCREEN (STUDENT TO OWNER)
// =========================================================
function renderChatView() {
  const p = STATE.properties.find(x => x.id === STATE.selectedPropertyId) || STATE.properties[0];
  const msgList = STATE.chats[p.id] || [
    { sender: 'owner', text: `Hello! I am ${p.ownerName}, manager of ${p.title}. How can I assist you with room allotment?`, time: 'Just now' }
  ];

  viewport.innerHTML = `
    <div class="chat-viewport">
      <div class="chat-header-bar">
        <button class="back-btn" onclick="navigate('detail', '${p.id}')"><i class="fa-solid fa-arrow-left"></i></button>
        <div class="owner-chat-avatar">
          ${p.ownerName.charAt(0)}
          <div class="online-dot"></div>
        </div>
        <div style="flex:1;">
          <div style="font-size:13.5px; font-weight:700;">${p.ownerName} <i class="fa-solid fa-circle-check" style="color:var(--verified-green); font-size:12px;"></i></div>
          <div style="font-size:11px; color:var(--charcoal-light);">${p.ownerRole} • ${p.title}</div>
        </div>
        <button class="circle-nav-btn" onclick="window.open('tel:${p.ownerPhone}')"><i class="fa-solid fa-phone" style="color:var(--purple-primary);"></i></button>
      </div>

      <div class="messages-scroll-area" id="chat-messages-area">
        <div style="background:var(--purple-card-bg); border-radius:10px; padding:10px 14px; font-size:11.5px; color:var(--purple-dark); display:flex; align-items:center; gap:8px;">
          <i class="fa-solid fa-shield-halved" style="color:var(--purple-primary);"></i>
          Stayora Verified Contact: Direct conversation with property warden. No brokerage.
        </div>

        ${msgList.map(m => `
          <div class="chat-bubble ${m.sender}">
            <div>${m.text}</div>
            <span class="bubble-time">${m.time}</span>
          </div>
        `).join('')}
      </div>

      <!-- Quick Inquiry Chips -->
      <div class="quick-inquiry-bar">
        <div class="quick-chip" onclick="sendQuickMessage('${p.id}', 'Is 2-sharing bed vacant from next week?')">Vacant Beds?</div>
        <div class="quick-chip" onclick="sendQuickMessage('${p.id}', 'Can I schedule a visit tomorrow at 5 PM?')">Schedule Visit</div>
        <div class="quick-chip" onclick="sendQuickMessage('${p.id}', 'Is food / mess included in the rent?')">Food Included?</div>
        <div class="quick-chip" onclick="sendQuickMessage('${p.id}', 'What is the deposit refund policy?')">Deposit Policy</div>
      </div>

      <!-- Message Input -->
      <div class="chat-input-bar">
        <input type="text" id="chat-text-input" placeholder="Type your inquiry..." onkeydown="if(event.key==='Enter') sendChatMessage('${p.id}')">
        <button class="send-msg-btn" onclick="sendChatMessage('${p.id}')"><i class="fa-solid fa-paper-plane"></i></button>
      </div>
    </div>
  `;

  setTimeout(() => {
    const area = document.getElementById('chat-messages-area');
    if (area) area.scrollTop = area.scrollHeight;
  }, 50);
}

window.sendQuickMessage = function(propertyId, text) {
  handleUserMessage(propertyId, text);
};

window.sendChatMessage = function(propertyId) {
  const input = document.getElementById('chat-text-input');
  const text = input.value.trim();
  if (!text) return;
  input.value = '';
  handleUserMessage(propertyId, text);
};

function handleUserMessage(propertyId, text) {
  if (!STATE.chats[propertyId]) STATE.chats[propertyId] = [];
  STATE.chats[propertyId].push({
    sender: 'student',
    text: text,
    time: 'Just now'
  });
  renderChatView();

  // Simulated Warden Auto-Reply
  setTimeout(() => {
    let reply = "Hello! Thanks for asking on Stayora. Let me know if you would like me to reserve a bed or send pictures of room 204.";
    const lower = text.toLowerCase();
    if (lower.includes('vacant')) {
      reply = "Yes! We currently have 2 vacant beds in our 2-sharing AC room on the 2nd floor.";
    } else if (lower.includes('visit')) {
      reply = "Sure! You can visit tomorrow between 10:00 AM and 7:00 PM. Our warden will show you the rooms.";
    } else if (lower.includes('food') || lower.includes('mess')) {
      reply = "Yes, 3 meals (breakfast, lunch, dinner) are completely included in the monthly rent. Both North & South Indian food served.";
    } else if (lower.includes('deposit')) {
      reply = "Deposit is 100% refundable with 1 month prior move-out notice. No deductions except electricity units.";
    }

    STATE.chats[propertyId].push({
      sender: 'owner',
      text: reply,
      time: 'Just now'
    });
    renderChatView();
  }, 900);
}

// =========================================================
// 8. NEARBY FACILITIES SCREEN
// =========================================================
function renderFacilitiesView() {
  const p = STATE.properties.find(x => x.id === STATE.selectedPropertyId) || STATE.properties[0];
  const facs = STATE.facilities;

  viewport.innerHTML = `
    <div style="background:#FFFFFF; padding:12px 16px; border-bottom:1px solid var(--border-subtle); display:flex; align-items:center; gap:10px;">
      <button class="back-btn" onclick="navigate('detail', '${p.id}')"><i class="fa-solid fa-arrow-left"></i></button>
      <div>
        <h3 style="font-size:16px; font-weight:800; color:var(--charcoal-dark);">Nearby Facilities & Essentials</h3>
        <p style="font-size:11.5px; color:var(--charcoal-light);">Near ${p.title}</p>
      </div>
    </div>

    <div style="padding:16px; display:flex; flex-direction:column; gap:12px;">
      <!-- Emergency Helpline Notice -->
      <div style="background:var(--coral-badge-bg); border-radius:14px; padding:14px; display:flex; align-items:center; gap:12px; border:1px solid rgba(239, 68, 68, 0.2);">
        <div style="width:38px; height:38px; border-radius:50%; background:#FFFFFF; display:flex; align-items:center; justify-content:center; color:var(--coral-badge); font-size:16px;">
          <i class="fa-solid fa-ambulance"></i>
        </div>
        <div>
          <div style="font-size:13px; font-weight:700; color:var(--coral-badge);">Campus Emergency Tie-Up</div>
          <div style="font-size:11.5px; color:var(--charcoal-dark);">Malla Reddy Narayana Hospital: 24/7 Ambulance 040-23783000</div>
        </div>
      </div>

      ${facs.map(f => `
        <div class="facility-card">
          <div style="display:flex; justify-content:space-between; align-items:flex-start;">
            <div style="display:flex; gap:12px; align-items:center;">
              <div class="facility-icon-circle" style="background:var(--purple-tint); color:var(--purple-primary);">
                <i class="fa-solid ${f.icon}"></i>
              </div>
              <div>
                <span style="font-size:10.5px; font-weight:700; color:var(--charcoal-light); text-transform:uppercase;">${f.category}</span>
                <div style="font-size:13.5px; font-weight:700; color:var(--charcoal-dark);">${f.name}</div>
              </div>
            </div>
            <div class="compat-badge" style="background:var(--purple-tint); color:var(--purple-primary);">${f.distanceKm} km</div>
          </div>

          <div style="display:flex; align-items:center; gap:5px; font-size:12px; color:var(--charcoal-medium); margin-top:2px;">
            <i class="fa-solid fa-person-walking" style="color:var(--electric-blue);"></i>
            <span>${f.travelMinutes} mins (${f.mode})</span>
          </div>

          <div style="font-size:11.5px; color:var(--charcoal-light);">${f.address}</div>
          <div style="font-size:11px; font-weight:600; color:var(--purple-primary);">${f.timing}</div>

          <button class="btn-secondary" style="width:100%; justify-content:center; font-size:12px; padding:8px;" onclick="showToast('Opening Google Maps Directions...')">
            <i class="fa-solid fa-location-arrow"></i> Get Directions
          </button>
        </div>
      `).join('')}
    </div>
  `;
}

// =========================================================
// 9. ROOMMATES SCREEN
// =========================================================
function renderRoommatesView() {
  const rms = STATE.roommates;
  const prefs = STATE.userPreferences;

  viewport.innerHTML = `
    <div style="padding:20px 20px 10px;">
      <div style="display:flex; justify-content:space-between; align-items:center;">
        <div>
          <h2 style="font-size:22px; font-weight:800; color:var(--charcoal-dark);">Roommate Matching</h2>
          <p style="font-size:12px; color:var(--charcoal-light);">Compatible students in ${prefs.college.split(' ')[0]} ${prefs.college.split(' ')[1] || ''}</p>
        </div>
        <div class="compat-badge"><i class="fa-solid fa-wand-magic-sparkles"></i> AI Powered</div>
      </div>
    </div>

    <div style="padding:0 16px 20px; display:flex; flex-direction:column; gap:12px;">
      <div style="background:var(--purple-card-bg); border-radius:12px; padding:12px; font-size:12px; color:var(--purple-dark); display:flex; align-items:center; gap:8px;">
        <i class="fa-solid fa-lightbulb" style="color:var(--purple-primary);"></i>
        Scores are calculated from your sleep timings, study habits, hometown, and personality traits.
      </div>

      ${rms.map(rm => `
        <div class="detail-card" style="padding:14px;">
          <div style="display:flex; justify-content:space-between; align-items:flex-start; margin-bottom:8px;">
            <div style="display:flex; gap:10px; align-items:center;">
              <div class="owner-chat-avatar" style="width:44px; height:44px;">${rm.name.charAt(0)}</div>
              <div>
                <div style="font-size:14px; font-weight:700;">${rm.name} <i class="fa-solid fa-circle-check" style="color:var(--verified-green); font-size:12px;"></i></div>
                <div style="font-size:11.5px; color:var(--charcoal-light);">${rm.branch} • ${rm.year}</div>
              </div>
            </div>
            <div class="compat-badge"><i class="fa-solid fa-wand-magic-sparkles"></i> ${rm.compat}% Match</div>
          </div>

          <div style="font-size:12px; color:var(--charcoal-dark); margin-bottom:8px;">"${rm.bio}"</div>

          <div style="display:flex; gap:6px; flex-wrap:wrap; margin-bottom:10px;">
            ${rm.traits.map(t => `<span class="trait-pill" style="padding:3px 8px; font-size:11px;">${t}</span>`).join('')}
          </div>

          <div style="display:flex; justify-content:space-between; background:#F8FAFC; border-radius:8px; padding:8px 12px; font-size:11.5px; margin-bottom:10px;">
            <div><span style="color:var(--charcoal-light);">Sleep:</span> <strong>${rm.sleep}</strong></div>
            <div><span style="color:var(--charcoal-light);">Sharing:</span> <strong>${rm.sharing}-Sharing</strong></div>
            <div><span style="color:var(--charcoal-light);">From:</span> <strong>${rm.hometown.split('/')[0]}</strong></div>
          </div>

          <button class="btn-primary" style="width:100%; justify-content:center; padding:9px; font-size:13px; background:${rm.connected ? 'var(--verified-green)' : 'var(--purple-primary)'};" onclick="toggleConnectRoommate('${rm.id}')">
            <i class="fa-solid ${rm.connected ? 'fa-check' : 'fa-user-plus'}"></i> ${rm.connected ? 'Connection Request Sent' : 'Connect as Roommate'}
          </button>
        </div>
      `).join('')}
    </div>
  `;
}

window.toggleConnectRoommate = function(id) {
  const rm = STATE.roommates.find(x => x.id === id);
  if (rm) {
    rm.connected = !rm.connected;
    showToast(rm.connected ? `Connection request sent to ${rm.name}!` : 'Request cancelled');
    renderRoommatesView();
  }
};

// Initial App Launch
navigate('home');
