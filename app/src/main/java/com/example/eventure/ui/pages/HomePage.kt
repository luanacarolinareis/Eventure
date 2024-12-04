package com.example.eventure.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.eventure.R
import com.example.eventure.components.uniSans
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.maps.android.compose.*
import androidx.compose.ui.draw.clip
import android.graphics.Bitmap
import android.graphics.Canvas
import com.google.android.gms.maps.model.BitmapDescriptorFactory


@Composable
@Preview(showBackground = true)
fun HomePage(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val placesClient = com.google.android.libraries.places.api.Places.createClient(context)

    val coimbraLatLng = LatLng(40.2056, -8.4196)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(coimbraLatLng, 12f)
    }

    var selectedEvent by remember { mutableStateOf<Event?>(null) }
    val featuredEvents = listOf(
        Event("Event 1", "Location 1", "10:00 AM", LatLng(40.2056, -8.4196)),
        Event("Event 2", "Location 2", "2:00 PM", LatLng(40.2111, -8.4200)),
        Event("Event 3", "Location 3", "6:00 PM", LatLng(40.2200, -8.4300))
    )

    var searchQuery by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }

    LaunchedEffect(searchQuery) {
        fetchAutocompleteSuggestions(query = searchQuery, placesClient = placesClient) { fetchedSuggestions ->
            suggestions = fetchedSuggestions
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp)
                .background(Color(0xFFFCE2B1)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
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
                    IconButton(onClick = { /* Action */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.menu),
                            contentDescription = "Menu",
                            modifier = Modifier.size(80.dp)
                        )
                    }

                    SearchBarWithSuggestions(
                        query = searchQuery,
                        onQueryChanged = { newQuery -> searchQuery = newQuery },
                        suggestions = suggestions,
                        onSuggestionSelected = { suggestion ->
                            // Use the placeId to fetch details or adjust map
                        }
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(100.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Featured Events",
                style = TextStyle(
                    fontFamily = uniSans,
                    fontSize = 25.sp
                ),
                color = Color(0xFF095FA7),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 16.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
            ) {
                items(featuredEvents) { event ->
                    FeaturedEventCard(
                        event = event,
                        isSelected = event == selectedEvent,
                        onClick = {
                            selectedEvent = event
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(event.location, 14f)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            InteractiveGameMap(
                featuredEvents = featuredEvents,
                cameraPositionState = cameraPositionState,
                selectedEvent = selectedEvent,
                onMarkerClick = { event -> selectedEvent = event }
            )
        }
    }
}

fun fetchAutocompleteSuggestions(
    query: String,
    placesClient: PlacesClient,
    onSuggestionsFetched: (List<AutocompletePrediction>) -> Unit
) {
    if (query.isNotBlank()) {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response -> onSuggestionsFetched(response.autocompletePredictions) }
            .addOnFailureListener { onSuggestionsFetched(emptyList()) }
    }
}

@Composable
fun SearchBarWithSuggestions(
    query: String,
    onQueryChanged: (String) -> Unit,
    suggestions: List<AutocompletePrediction>,
    onSuggestionSelected: (AutocompletePrediction) -> Unit
) {
    Column {
        TextField(
            value = query,
            onValueChange = onQueryChanged,
            label = { Text("Search...") },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color(0xFF0CCA9D),
                focusedBorderColor = Color(0xFF0CCA9D),
                unfocusedBorderColor = Color(0xFF0CCA9D)
            ),
            singleLine = true
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.White, RoundedCornerShape(8.dp))
        ) {
            items(suggestions) { suggestion ->
                Text(
                    text = suggestion.getFullText(null).toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSuggestionSelected(suggestion) }
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun InteractiveGameMap(
    featuredEvents: List<Event>,
    cameraPositionState: CameraPositionState,
    selectedEvent: Event?,
    onMarkerClick: (Event) -> Unit
) {
    val context = LocalContext.current

    // Helper function to resize the drawable
    fun resizeDrawable(drawableResId: Int, width: Int, height: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(context, drawableResId)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable?.setBounds(0, 0, canvas.width, canvas.height)
        drawable?.draw(canvas)
        return bitmap
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = false),
        uiSettings = MapUiSettings(zoomControlsEnabled = true)
    ) {
        featuredEvents.forEachIndexed { index, event ->
            val iconSize = if (selectedEvent == event) 150 else 100 // Enlarge icon if selected

            val markerIcon = if (index < 2) { // Use different icons
                resizeDrawable(R.drawable.quest_marker, iconSize, iconSize)
            } else {
                resizeDrawable(R.drawable.quest_marker, iconSize, iconSize)
            }

            Marker(
                state = MarkerState(position = event.location),
                title = event.title,
                snippet = "Click for details",
                icon = BitmapDescriptorFactory.fromBitmap(markerIcon),
                onClick = {
                    onMarkerClick(event) // Notify parent about marker click
                    true
                }
            )
        }
    }
}


// Pop-up composable to show event details
@Composable
fun PopupWindow(event: Event, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80000000)) // Semi-transparent background
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
            containerColor = if (isSelected) Color(0xFF0CCA9D) else Color(0xFFF5F5F5) // Dynamic card background
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
// Event data class with location
data class Event(
    val title: String,
    val locationName: String,
    val time: String,
    val location: LatLng
)

