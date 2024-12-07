package com.example.eventure.ui.pages

import androidx.compose.foundation.Image
import com.example.eventure.R
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.android.gms.maps.model.LatLng
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.eventure.components.uniSans
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import java.time.LocalDate
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyRow
import androidx.navigation.NavHostController
import androidx.compose.ui.window.Popup
import androidx.compose.ui.unit.IntOffset
import com.google.android.gms.maps.CameraUpdateFactory
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

// Icon resource mappings
val combinedIconResources = mapOf(
    "common_sports" to R.drawable.common_sports,
    "common_music" to R.drawable.common_music,
    "common_workshop" to R.drawable.common_workshop,
    "common_party" to R.drawable.common_party,

    "rare_sports" to R.drawable.rare_sports,
    "rare_music" to R.drawable.rare_music,
    "rare_workshop" to R.drawable.rare_workshop,
    "rare_party" to R.drawable.rare_party,

    "premium_sports" to R.drawable.premium_sports,
    "premium_music" to R.drawable.premium_music,
    "premium_workshop" to R.drawable.premium_workshop,
    "premium_party" to R.drawable.premium_party,

    )
val genreIcons = mapOf(
    "sports" to R.drawable.sports_icon,
    "workshop" to R.drawable.workshop_icon,
    "music" to R.drawable.music_icon,
    "party" to R.drawable.party_icon,
    "other" to R.drawable.other_icon // Replace with your default "none" icon if available
)
val genreWhiteIcons = mapOf(
    "sports" to R.drawable.sports_white_icon,
    "workshop" to R.drawable.workshop_white_icon,
    "music" to R.drawable.music_white_icon,
    "party" to R.drawable.party_white_icon,
    "other" to R.drawable.other_icon // Replace with your default "none" icon if available
)
val defaultTypeIconResources = mapOf(
    "common" to R.drawable.common,
    "rare" to R.drawable.rare,
    "premium" to R.drawable.premium
    // Add other types as needed
)
val organizer1 = Organizer(
    id = "AAC",
    rating = 5,
    profilePic = R.drawable.quest_marker,
)
val organizer2 = Organizer(
    id = "AEISEC",
    rating = 4,
    profilePic = R.drawable.aeisec_icon,
)
val organizer3 = Organizer(
    id = "CMCoimbra",
    rating = 3,
    profilePic = R.drawable.cmcoimbra_icon,
)
val allOrganizers = listOf(
    organizer1, organizer2, organizer3
)
/*val allEvents = listOf(
    Event("Event 1", "Location 1", "22:00", "2024-12-05", LatLng(40.2056, -8.4196), "Common", "sports"),
    Event("Event 2", "Location 2", "02:00", "2024-12-06", LatLng(40.2111, -8.4200), "Common", "music"),
    Event("Event 3", "Location 3", "18:00", "2024-12-06", LatLng(40.2200, -8.4300), "Rare", "workshop"),
    Event("Event 4", "Location 4", "21:00", "2024-12-07", LatLng(40.20924862590421, -8.427659543014594), "Premium", "party",R.drawable.event_custom_image,true),
    Event("Event 5", "Location 5", "00:00", "2024-12-07", LatLng(40.2300, -8.4400), "Rare", "none")
)*/

val event1 = Event(
    title = "Event 1",
    locationName = "Location 1",
    time = "20:00",
    date = "2024-12-05",
    location = LatLng(40.2056, -8.4196),
    type = "Common",
    genre = "sports",
    imageResId = null,
    hasImage = false,
    eventPost = Post(
        imageResIds = listOf(R.drawable.football_game_icon, R.drawable.football_game2_icon),
        description = "What an electrifying match today between our fierce university teams! Every pass, goal, and save had us on the edge of our seats. Kudos to both squads for showcasing true sportsmanship and skill. Until next time, keep that school spirit high!",
        organizer = organizer1
    )
)

val event2 = Event(
    title = "Event 2",
    locationName = "Location 2",
    time = "02:00",
    date = "2024-12-06",
    location = LatLng(40.2111, -8.4200),
    type = "Common",
    genre = "music",
    imageResId = null,
    hasImage = false,
    eventPost = Post(
        imageResIds = listOf(R.drawable.concert_icon),
        description = "You don't want to miss this concert!",
        organizer = organizer2
    )
)

val event3 = Event(
    title = "Event 3",
    locationName = "Location 3",
    time = "18:00",
    date = "2024-12-06",
    location = LatLng(40.2200, -8.4300),
    type = "Rare",
    genre = "workshop",
    imageResId = null,
    hasImage = false,
    eventPost = Post(
        imageResIds = listOf(R.drawable.workshop_art_icon),
        description = "Art workshop. Improve your skills.",
        organizer = organizer3
    )
)

val event4 = Event(
    title = "Event 4",
    locationName = "Location 4",
    time = "23:00",
    date = "2024-12-07",
    location = LatLng(40.20924862590421, -8.427659543014594),
    type = "Premium",
    genre = "party",
    imageResId = R.drawable.event_custom_image,
    hasImage = true,
    eventPost = Post(
        imageResIds = listOf(R.drawable.party_night_icon),
        description = "This is a description for Event 4.",
        organizer = organizer1
    )
)

val event5 = Event(
    title = "Event 5",
    locationName = "Location 5",
    time = "00:00",
    date = "2024-12-07",
    location = LatLng(40.2300, -8.4400),
    type = "Rare",
    genre = "other",
    imageResId = null,
    hasImage = false,
    eventPost = Post(
        imageResIds = listOf(R.drawable.concert_icon),
        description = "This is a description for Event 5.",
        organizer = organizer2
    )
)

// Update organizer events
// Define a function to update organizer events
fun initializeOrganizerEvents() {
    organizer1.events = listOf(event1, event4)
    organizer2.events = listOf(event2, event5)
    organizer3.events = listOf(event3)
}

// Final list of events
val allEvents = listOf(event1, event2, event3, event4, event5)

@Composable
fun HomePage(navController: NavHostController, modifier: Modifier = Modifier) {
    val coimbraLatLng = LatLng(40.2056, -8.4196)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(coimbraLatLng, 12f)
    }

    var selectedEvent by remember { mutableStateOf<Event?>(null) }
    var isMenuOpen by remember { mutableStateOf(false) }
    initializeOrganizerEvents()
    // Initialize all type filters as selected
    val selectedFilters = remember {
        mutableStateMapOf<String, Boolean>().apply {
            listOf("Premium", "Rare", "Common").forEach { type ->
                this[type] = true
            }
        }
    }

    // Initialize all genre filters as selected
    val selectedGenres = remember {
        mutableStateMapOf<String, Boolean>().apply {
            listOf("sports", "workshop", "music", "party", "other").forEach { genre ->
                this[genre] = true
            }
        }
    }

    val selectedTimeRange = remember { mutableStateOf("00:00" to "23:59") }
    val selectedDateRange = remember { mutableStateOf("2024-12-01" to "2024-12-31") }

    /*val allEvents = listOf(
        Event("Event 1", "Location 1", "22:00", "2024-12-05", LatLng(40.2056, -8.4196), "Common", "sports"),
        Event("Event 2", "Location 2", "02:00", "2024-12-06", LatLng(40.2111, -8.4200), "Common", "music"),
        Event("Event 3", "Location 3", "18:00", "2024-12-06", LatLng(40.2200, -8.4300), "Rare", "workshop"),
        Event("Event 4", "Location 4", "21:00", "2024-12-07", LatLng(40.20924862590421, -8.427659543014594), "Premium", "party"),
        Event("Event 5", "Location 5", "00:00", "2024-12-07", LatLng(40.2300, -8.4400), "Rare", "none")
    )*/

    val filteredEvents = allEvents.filter { event ->
        val typeMatch = selectedFilters[event.type] == true
        val timeMatch = event.time in selectedTimeRange.value.first..selectedTimeRange.value.second
        val dateMatch = event.date in selectedDateRange.value.first..selectedDateRange.value.second
        val genreMatch = selectedGenres[event.genre] == true

        typeMatch && genreMatch && timeMatch && dateMatch
    }

    Spacer(modifier = Modifier.height(30.dp))

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp)
                .background(Color(0xFFFCE2B1)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(onClick = { isMenuOpen = true }) {
                        Image(
                            painter = painterResource(id = R.drawable.menu),
                            contentDescription = "Menu",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Button(
                        onClick = { navController.navigate("explore_page") },
                        modifier = Modifier.fillMaxWidth(0.6f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0CC59B))
                    ) {
                        Text(
                            text = "Start searching",
                            style = TextStyle(fontFamily = uniSans)
                        )
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(100.dp)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            InteractiveGameMap(
                featuredEvents = filteredEvents,
                cameraPositionState = cameraPositionState,
                selectedEvent = selectedEvent,
                onMarkerClick = { event ->
                    selectedEvent = event
                },
                navController = navController // Add this line to pass the navController
            )
        }
        if (isMenuOpen) {
            MenuPopup(
                onDismiss = { isMenuOpen = false },
                selectedFilters = selectedFilters,
                selectedTimeRange = selectedTimeRange,
                selectedDateRange = selectedDateRange,
                selectedGenres = selectedGenres,
                events = allEvents,
                navController = navController
            )
        }
    }
}
@Composable
fun InteractiveGameMap(
    featuredEvents: List<Event>,
    cameraPositionState: com.google.maps.android.compose.CameraPositionState,
    selectedEvent: Event?,
    onMarkerClick: (Event) -> Unit,
    navController: NavHostController
) {
    val context = LocalContext.current
    var showPopup by remember { mutableStateOf(false) }
    var popupEvent by remember { mutableStateOf<Event?>(null) }
    var popupOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    val coroutineScope = rememberCoroutineScope()

    // Helper function to get the correct icon resource
    fun getMarkerIcon(event: Event, isSelected: Boolean): Bitmap {
        val combinedKey = "${event.type.lowercase()}_${event.genre.lowercase()}"
        val iconResId = combinedIconResources[combinedKey]
            ?: defaultTypeIconResources[event.type.lowercase()]
            ?: R.drawable.common

        val drawable = ContextCompat.getDrawable(context, iconResId)
            ?: return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

        val baseHeight = 150 // Default size
        val desiredHeight = if (isSelected) (baseHeight * 1.5).toInt() else baseHeight // Enlarge if selected
        val aspectRatio = drawable.intrinsicHeight.toFloat() / drawable.intrinsicWidth.toFloat()
        val adjustedWidth = (desiredHeight / aspectRatio).toInt()

        val bitmap = Bitmap.createBitmap(adjustedWidth, desiredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, adjustedWidth, desiredHeight)
        drawable.draw(canvas)
        return bitmap
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .fillMaxHeight()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = false),
        uiSettings = MapUiSettings(zoomControlsEnabled = true)
    ) {
        featuredEvents.forEach { event ->
            val isSelected = event == selectedEvent
            val markerIcon = getMarkerIcon(event, isSelected)

            Marker(
                state = MarkerState(position = event.location),
                title = event.title,
                snippet = "Click for details",
                icon = BitmapDescriptorFactory.fromBitmap(markerIcon),
                onClick = {
                    onMarkerClick(event) // Notify parent about marker click
                    popupEvent = event
                    showPopup = true

                    // Launch a coroutine to animate the camera
                    coroutineScope.launch {
                        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(event.location, 15f))
                    }

                    // Calculate marker position on screen for popup
                    val markerScreenPosition = cameraPositionState.projection?.toScreenLocation(event.location)
                    popupOffset = markerScreenPosition?.let {
                        Offset(it.x.toFloat(), it.y.toFloat())
                    } ?: Offset(0f, 0f)

                    true
                }
            )
        }
    }

    // Display the popup if showPopup is true
    if (showPopup && popupEvent != null) {
        EventPopup(
            event = popupEvent!!,
            onDismiss = { showPopup = false },
            onNavigateToEventPage = { eventId ->
                navController.navigate("event_page/$eventId")
            },
            offset = popupOffset
        )
    }
}

@Composable
fun MenuPopup(
    onDismiss: () -> Unit,
    selectedFilters: MutableMap<String, Boolean>,
    selectedTimeRange: MutableState<Pair<String, String>>,
    selectedDateRange:  MutableState<Pair<String, String>>,
    selectedGenres: MutableMap<String, Boolean>,
    events: List<Event>,
    navController: NavHostController,
) {
    var activeOption by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80000000))
            .clickable(onClick = { activeOption = null })
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .wrapContentHeight()
                .align(Alignment.TopCenter)
                .offset(y = 100.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (activeOption) {
                    null -> MainMenuContent(
                        onSelectOption = { activeOption = it },
                        onDismiss = onDismiss
                    )
                    "Filters" -> FiltersContent(
                        selectedFilters = selectedFilters,
                        selectedTimeRange = selectedTimeRange,
                        selectedDateRange = selectedDateRange,
                        selectedGenres = selectedGenres, // Pass this parameter correctly
                        onBack = { activeOption = null }
                    )
                    "Calendar" -> CalendarContent(
                        events = allEvents,
                        onBack = { activeOption = null },
                        navController = navController // Pass navController here
                    )
                    "About" -> AboutPopup(onDismiss = { activeOption = null })
                }
            }
        }
    }
}

@Composable
fun AboutPopup(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .wrapContentHeight()
            .background(Color.Transparent)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "About the app...",
                style = TextStyle(fontFamily = uniSans, fontSize = 20.sp, fontWeight = FontWeight.Bold),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "This app was created by students within the scope of a subject - Management and Innovation Processes - from the 3rd year of the Computer Engineering degree at the Faculty of Science and Technology of the University of Coimbra (FCTUC), so FCTUC is not responsible for the its use and contents.",
                style = TextStyle(fontFamily = uniSans, fontSize = 14.sp),
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0CC59B)) // Botão verde
            ) {
                Text("Close", color = Color.White)
            }
        }
    }
}

@Composable
fun EventRow(event: Event, onEventClick: (String) -> Unit) {
    val isPremium = event.type == "Premium" // Example: Add shimmer for Premium events
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onEventClick(event.title) } // Navigate on click
            .let { if (isPremium) it.shimmerEffect() else it }, // Add shimmer effect if event is premium
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = event.title,
                style = TextStyle(fontSize = 18.sp, fontFamily = uniSans, color = Color.Black)
            )
            Text(
                text = "Time: ${event.time}",
                style = TextStyle(fontSize = 14.sp, color = Color.Gray)
            )
            Text(
                text = "Location: ${event.locationName}",
                style = TextStyle(fontSize = 14.sp, color = Color.Gray)
            )
            Text(
                text = "Genre: ${event.genre.capitalize()}",
                style = TextStyle(fontSize = 14.sp, color = Color.Gray)
            )
        }
    }
}

@Composable
fun MainMenuContent(onSelectOption: (String) -> Unit, onDismiss: () -> Unit) {
    Text(
        text = "Menu",
        style = TextStyle(fontFamily = uniSans, fontSize = 24.sp),
        color = Color.Black
    )
    Divider(modifier = Modifier.padding(vertical = 8.dp))
    MenuOptionRow(
        iconResId = R.drawable.filter_icon,
        label = "Filters",
        onClick = { onSelectOption("Filters") }
    )
    MenuOptionRow(
        iconResId = R.drawable.calendar,
        label = "Calendar",
        onClick = { onSelectOption("Calendar") }
    )
    MenuOptionRow(
        iconResId = R.drawable.options_icon,
        label = "Settings",
        onClick = { onSelectOption("Settings") }
    )
    MenuOptionRow(
        iconResId = R.drawable.about,
        label = "About",
        onClick = { onSelectOption("About") }
    )
    Spacer(modifier = Modifier.height(8.dp))
    Button(
        onClick = onDismiss,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0CC59B)), // Green background
        modifier = Modifier.padding(8.dp) // Add padding if needed
    ) {
        Text(
            text = "Close",
            style = TextStyle(fontFamily = uniSans, color = Color.White) // Apply uniSans font and white text color
        )
    }
}

@Composable
fun MenuOptionRow(iconResId: Int, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = label,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            style = TextStyle(fontFamily = uniSans, fontSize = 18.sp),
            color = Color.Gray
        )
    }
}
@Composable
fun FiltersContent(
    selectedFilters: MutableMap<String, Boolean>,
    selectedTimeRange: MutableState<Pair<String, String>>,
    selectedDateRange: MutableState<Pair<String, String>>,
    selectedGenres: MutableMap<String, Boolean>, // Updated to use multiple genres
    onBack: () -> Unit
) {
    val filters = listOf("Premium", "Rare", "Common")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp) // Constrain height to ensure the popup doesn't grow too large
        ) {
            Text(
                text = "Filters",
                style = TextStyle(fontFamily = uniSans, fontSize = 24.sp),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Scrollable content for filters
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .weight(1f) // Allow the LazyColumn to take up available space
                    .fillMaxWidth()
            ) {
                // Category Filters
                item {
                    Text(
                        text = "Category Filters",
                        style = TextStyle(fontSize = 18.sp, fontFamily = uniSans, color = Color.Black)
                    )
                }
                items(filters) { filter ->
                    val isSelected = selectedFilters[filter] == true
                    FilterRow(
                        filter = filter,
                        isSelected = isSelected,
                        onToggle = {
                            selectedFilters[filter] = !isSelected
                        }
                    )
                }

                // Genre Filters
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Genre Filters",
                        style = TextStyle(fontSize = 18.sp, fontFamily = uniSans, color = Color.Black)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    GenreFilter(selectedGenres = selectedGenres)
                }

                // Time Range Filters
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    TimeRangeFilter(selectedTimeRange = selectedTimeRange)
                }

                // Date Range Filters
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    DateRangeFilter(selectedDateRange = selectedDateRange)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Back button
            Button(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0CC59B)) // Green button
            ) {
                Text(text = "Apply all")
            }
        }
    }
}
@Composable
fun GenreFilter(selectedGenres: MutableMap<String, Boolean>) {
    val genres = listOf("sports", "workshop", "music", "party", "other")
    /*val genreIcons = mapOf(
        "sports" to R.drawable.sports_icon,
        "workshop" to R.drawable.workshop_icon,
        "music" to R.drawable.music_icon,
        "party" to R.drawable.party_icon,
        "none" to R.drawable.logo // Replace with your default "none" icon if available
    )*/

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Filter by Genre",
            style = TextStyle(fontSize = 18.sp, fontFamily = uniSans, color = Color.Black)
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(genres) { genre ->
                val isSelected = selectedGenres[genre] == true
                Column(
                    modifier = Modifier
                        .clickable { selectedGenres[genre] = selectedGenres[genre] != true }
                        .background(
                            color = if (isSelected) Color(0xFF0CC59B) else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = genreIcons[genre] ?: R.drawable.logo),
                        contentDescription = genre,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = genre.capitalize(),
                        style = TextStyle(fontSize = 14.sp, color = if (isSelected) Color.White else Color.Black)
                    )
                }
            }
        }
    }
}
@Composable
fun TimeRangeFilter(selectedTimeRange: MutableState<Pair<String, String>>) {
    var startTime by remember { mutableStateOf(selectedTimeRange.value.first) }
    var endTime by remember { mutableStateOf(selectedTimeRange.value.second) }

    Column {
        Text(
            text = "Filter by Time",
            style = TextStyle(fontFamily = uniSans, fontSize = 18.sp),
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = startTime,
                onValueChange = { startTime = it },
                label = { Text("Start Time") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = endTime,
                onValueChange = { endTime = it },
                label = { Text("End Time") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                selectedTimeRange.value = Pair(startTime, endTime)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0CC59B)), // Green background
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Apply",
                style = TextStyle(fontFamily = uniSans, color = Color.White) // Apply uniSans font and white text color
            )
        }
    }
}
@Composable
fun DateRangeFilter(selectedDateRange: MutableState<Pair<String, String>>) {
    var startDate by remember { mutableStateOf(selectedDateRange.value.first) }
    var endDate by remember { mutableStateOf(selectedDateRange.value.second) }

    Column {
        Text(
            text = "Filter by Date",
            style = TextStyle(fontFamily = uniSans, fontSize = 18.sp),
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = startDate,
                onValueChange = { startDate = it },
                label = { Text("Start Date") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = endDate,
                onValueChange = { endDate = it },
                label = { Text("End Date") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                selectedDateRange.value = Pair(startDate, endDate)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0CC59B)), // Green background
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Apply",
                style = TextStyle(fontFamily = uniSans, color = Color.White) // Apply uniSans font and white text color
            )
        }
    }
}

@Composable
fun FilterRow(filter: String, isSelected: Boolean, onToggle: () -> Unit) {
    // Map of filters to their respective image resources
    val filterImages = mapOf(
        "Common" to R.drawable.common,
        "Uncommon" to R.drawable.uncommon,
        "Rare" to R.drawable.rare,
        "Premium" to R.drawable.premium,
        "Featured" to R.drawable.quest_marker
    )

    val isPremium = filter == "Premium"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle) // Toggle selection state on click
            .padding(vertical = 8.dp)
        .let { if (isPremium) it.shimmerEffect() else it },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Load the respective image for the filter
        Image(
            painter = painterResource(id = filterImages[filter] ?: R.drawable.filter_icon),
            contentDescription = filter,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = filter,
            style = TextStyle(fontSize = 18.sp, fontFamily = uniSans),
            color = if (isSelected) Color.Black else Color.LightGray
        )
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    val shimmerColors = listOf(
        Color(0xFFF4D03F), // Gold
        Color(0xFFE2B13C),
        Color(0xFFF4D03F)
    )

    val transition = rememberInfiniteTransition()
    val progress = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    background(
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(x = -100f + (progress.value * 200f), y = 0f),
            end = Offset(x = 300f + (progress.value * 200f), y = 100f)
        )
    )
}
// Data class to represent a filter
data class Filter(val name: String, val imageRes: Int)

@Composable
fun CalendarContent(
    events: List<Event>,
    onBack: () -> Unit,
    navController: NavHostController // Add navController as a parameter
) {
    val today = remember { LocalDate.now() }
    var currentMonth by remember { mutableStateOf(today.withDayOfMonth(1)) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val eventsGroupedByDate = events.groupBy { LocalDate.parse(it.date) }

    if (selectedDate != null) {
        EventListForDay(
            date = selectedDate!!,
            events = eventsGroupedByDate[selectedDate!!] ?: emptyList(),
            onBack = { selectedDate = null },
            navController = navController // Pass navController here
        )
    } else {
        MonthlyCalendar(
            currentMonth = currentMonth,
            eventsGroupedByDate = eventsGroupedByDate,
            onDateClick = { date -> selectedDate = date },
            onMonthChange = { offset -> currentMonth = currentMonth.plusMonths(offset.toLong()) },
            onBack = onBack
        )
    }
}

@Composable
fun MonthlyCalendar(
    currentMonth: LocalDate,
    eventsGroupedByDate: Map<LocalDate, List<Event>>,
    onDateClick: (LocalDate) -> Unit,
    onMonthChange: (Int) -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Month Navigation
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onMonthChange(-1) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0CC59B))
            ) {
                Text(text = "<", style = TextStyle(fontFamily = uniSans))
            }
            Button(
                onClick = { onMonthChange(1) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0CC59B))
            ) {
                Text(text = ">", style = TextStyle(fontFamily = uniSans))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${currentMonth.month.name.lowercase().capitalize()} ${currentMonth.year}",
                style = TextStyle(fontFamily = uniSans, fontSize = 24.sp),
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Calendar Grid
        val days = createMonthGrid(currentMonth)
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .wrapContentHeight() // Use wrapContentHeight instead of fillMaxSize
                .fillMaxWidth(), // Allow it to take up full width
            contentPadding = PaddingValues(4.dp)
        ) {
            items(days) { day ->
                DayCell(
                    date = day,
                    isCurrentMonth = day?.month == currentMonth.month,
                    hasEvents = day != null && eventsGroupedByDate.containsKey(day),
                    onClick = { if (day != null) onDateClick(day) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0CC59B))
        ) {
            Text("Back")
        }
    }
}

@Composable
fun DayCell(
    date: LocalDate?,
    isCurrentMonth: Boolean,
    hasEvents: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .background(
                if (isCurrentMonth) Color.White else Color.LightGray,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable(enabled = date != null, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (date != null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = date.dayOfMonth.toString(),
                    style = TextStyle(
                        fontFamily = uniSans,
                        fontSize = 16.sp,
                        color = if (isCurrentMonth) Color.Black else Color.Gray
                    )
                )
                if (hasEvents) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color.Red, shape = CircleShape)
                    )
                }
            }
        }
    }
}

@Composable
fun EventListForDay(
    date: LocalDate,
    events: List<Event>,
    onBack: () -> Unit,
    navController: NavHostController // Ensure navController is a parameter
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Events on ${date.dayOfMonth} ${date.month.name.lowercase().capitalize()} ${date.year}",
            style = TextStyle(fontFamily = uniSans, fontSize = 24.sp),
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(events) { event ->
                EventRow(event = event, onEventClick = { eventId ->
                    // Navigate to the event page
                    navController.navigate("event_page/$eventId")
                })
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0CC59B))
        ) {
            Text("Back")
        }
    }
}
fun createMonthGrid(month: LocalDate): List<LocalDate?> {
    val firstDayOfMonth = month.withDayOfMonth(1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
    val daysInMonth = month.lengthOfMonth()

    val days = (1..daysInMonth).map { day -> firstDayOfMonth.withDayOfMonth(day) }
    val leadingBlanks = List(firstDayOfWeek) { null }
    val trailingBlanks = List((7 - (days.size + leadingBlanks.size) % 7) % 7) { null }

    return leadingBlanks + days + trailingBlanks
}

@Composable
fun SettingsContent(onBack: () -> Unit) {
    Text(
        text = "Settings",
        style = TextStyle(fontFamily = uniSans, fontSize = 24.sp),
        color = Color.Black
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = "Settings content goes here.", style = TextStyle(fontSize = 16.sp), color = Color.Gray)
    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = onBack) {
        Text(text = "Back")
    }
}

// Pop-up composable to show event details
@Composable
fun PopupWindow(event: Event, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            //.background(Color(0x80000000)) // Semi-transparent background
            .clickable(onClick = onDismiss) // Close on outside click
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .wrapContentHeight()
                .align(Alignment.Center),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = event.title,
                    style = TextStyle(fontFamily = uniSans, fontSize = 24.sp),
                    color = Color.Black
                )
                Text(
                    text = "Location: ${event.locationName}",
                    style = TextStyle(fontFamily = uniSans, fontSize = 16.sp),
                    color = Color.Gray
                )
                Text(
                    text = "Time: ${event.time}",
                    style = TextStyle(fontFamily = uniSans, fontSize = 16.sp),
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onDismiss) {
                    Text(text = "Close")
                }
            }
        }
    }
}

@Composable
fun FeaturedEventCard(event: Event, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick), // Make the card clickable
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFF0CC59B) else Color(0xFFF5F5F5) // Dynamic card background
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = event.title,
                    fontFamily = uniSans,
                    fontSize = 20.sp,
                    color = if (isSelected) Color.White else Color.Black
                )
                Text(
                    text = event.locationName,
                    fontFamily = uniSans,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isSelected) Color.White else Color.Black
                )
                Text(
                    text = event.time,
                    fontFamily = uniSans,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isSelected) Color.White else Color.Black
                )
            }
        }
    }
}
@Composable
fun EventPopup(
    event: Event,
    onDismiss: () -> Unit,
    onNavigateToEventPage: (String) -> Unit,
    offset: Offset // Accept offset for popup positioning
) {
    Popup(
        alignment = Alignment.TopStart,
        offset = IntOffset(offset.x.toInt(), offset.y.toInt()), // Use the calculated offset
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = Modifier
                .width(200.dp)
                .clickable {
                    onNavigateToEventPage(event.title)
                    onDismiss() // Close the popup after navigation
                },
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White) // Explicitly set the background color to white
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = event.title,
                    style = TextStyle(fontSize = 16.sp, fontFamily = uniSans, color = Color.Black)
                )
                Text(
                    text = "Date: ${event.date}",
                    style = TextStyle(fontSize = 14.sp, fontFamily = uniSans, color = Color.Gray)
                )
                Text(
                    text = "Location: ${event.locationName}",
                    style = TextStyle(fontSize = 14.sp, fontFamily = uniSans, color = Color.Gray)
                )
            }
        }
    }
}



// Event data class with location
data class Event(
    val title: String,
    val locationName: String,
    val time: String,
    val date: String, // Add date field in "yyyy-MM-dd" format
    val location: LatLng,
    val type: String, //Premium, Rare, Common
    val genre: String, // sports, workshop, music, party, none or mixed
    val imageResId: Int? = null, // Optional image resource ID
    val hasImage: Boolean = false, // Flag to indicate if the event has an image
    val eventPost: Post
)
//Post data class that stores post info
data class Post(
    val imageResIds: List<Int>? = null, // Allow multiple image resource IDs
    val description: String,
    val organizer: Organizer
)

//Organizer data class
data class Organizer(
    val id: String,
    val rating: Int,
    val profilePic: Int? = null,
    var events: List<Event> = emptyList() // Allow multiple events and to be mutable
)

