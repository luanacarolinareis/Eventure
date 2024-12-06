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
import androidx.compose.ui.tooling.preview.Preview
//For the Featured Events section
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable//To click the featured event
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.eventure.components.bebasNeueFont
import com.example.eventure.components.uniSans
//For the pins on the map
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.Composable
import com.google.maps.android.compose.Marker
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import java.time.LocalDate
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.navigation.NavController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.ui.layout.ContentScale

import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import androidx.compose.ui.focus.*

@Composable
fun ExplorePage(navController: NavController, events: List<Event>) {
    val context = LocalContext.current
    val placesClient = com.google.android.libraries.places.api.Places.createClient(context)
    var searchQuery by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }
    LaunchedEffect(searchQuery) {
        fetchAutocompleteSuggestions(query = searchQuery, placesClient = placesClient) { fetchedSuggestions ->
            suggestions = fetchedSuggestions
        }
    }

    // Sort events by type: Premium, Rare, Common
    val sortedEvents = events.sortedBy {
        when (it.type) {
            "Premium" -> 1
            "Rare" -> 2
            "Common" -> 3
            else -> 4
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCE2B1))
    ) {
        // Go Back Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = { navController.popBackStack() }, // Go back to the previous page
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0CC59B)) // Green button
            ) {
                Text(
                    text = "Go Back",
                    style = TextStyle(fontFamily = uniSans, color = Color.White)
                )
            }
        }

        SearchBarWithSuggestions(
            query = searchQuery,
            onQueryChanged = { newQuery -> searchQuery = newQuery },
            suggestions = suggestions,
            onSuggestionSelected = { suggestion ->
                // Use the placeId to fetch details or adjust map
            }
        )

        // Search Bar
        /* Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = { /* Handle search input */ },
                placeholder = { Text("Search") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
        } */

        // Event List by Type
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Grouped Sections
            item {
                Text(
                    text = "Premium Events",
                    style = TextStyle(fontFamily = uniSans, fontSize = 18.sp, color = Color.Black),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(sortedEvents.filter { it.type == "Premium" }) { event ->
                EventCard(event = event, navController = navController)
            }

            item {
                Text(
                    text = "Rare Events",
                    style = TextStyle(fontFamily = uniSans, fontSize = 18.sp, color = Color.Black),
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            items(sortedEvents.filter { it.type == "Rare" }) { event ->
                EventCard(event = event, navController = navController)
            }

            item {
                Text(
                    text = "Common Events",
                    style = TextStyle(fontFamily = uniSans, fontSize = 18.sp, color = Color.Black),
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            items(sortedEvents.filter { it.type == "Common" }) { event ->
                EventCard(event = event, navController = navController)
            }
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
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Column {
        // Campo de busca
        OutlinedTextField(
            value = query,
            onValueChange = { newQuery ->
                onQueryChanged(newQuery)
                isFocused = newQuery.isNotBlank() // Atualiza o foco apenas se o texto não estiver vazio
            },
            placeholder = { Text("Search") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState -> isFocused = focusState.isFocused && query.isNotBlank() }
                .focusTarget(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFF0CCA9D),
                unfocusedBorderColor = Color(0xFF0CCA9D),
                cursorColor = Color(0xFF0CCA9D)
            ),
            singleLine = true
        )

        // Mostrar sugestões apenas se houver foco, sugestões disponíveis e texto preenchido
        if (isFocused && query.isNotBlank() && suggestions.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .heightIn(max = 200.dp) // Altura suficiente para 2 sugestões
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(suggestions) { suggestion ->
                        Text(
                            text = suggestion.getFullText(null).toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSuggestionSelected(suggestion)
                                    isFocused = false // Fechar sugestões ao selecionar
                                }
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EventCard(event: Event, navController: NavController) {
    val typeColor = when (event.type) {
        "Premium" -> Color(0xFFD4AF37) // Gold for Premium
        "Rare" -> Color(0xFF4A90E2)    // Blue for Rare
        "Common" -> Color(0xFF7B7B7B)  // Gray for Common
        else -> Color.Gray
    }

    // Retrieve the logo for the event's genre
    val genreLogo = genreWhiteIcons[event.genre] ?: R.drawable.logo

    // Placeholder image resource for the event banner
    val eventImage = event.imageResId ?: R.drawable.event_default_image // Replace with your actual default image resource

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { // Navigate to EventPage when clicked
            navController.navigate("event_page/${event.title}")
            },
        elevation = CardDefaults.cardElevation(4.dp)

    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Content of the EventCard
            Column(modifier = Modifier.fillMaxSize()) {
                // Main content area
                Box(
                    modifier = Modifier
                        .weight(1f) // Take up the rest of the space above the stripe
                        .fillMaxWidth()
                ) {
                    if (event.hasImage) {
                        // Display the image if available
                        Image(
                            painter = painterResource(id = eventImage),
                            contentDescription = "Event Banner",
                            modifier = Modifier.fillMaxSize(),
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Crop // Crop to fit
                        )
                    } else {
                        // White background if no image is available
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                        )
                    }

                    // Event Title (Top Left) over the image or white background
                    Text(
                        text = event.title,
                        style = TextStyle(fontSize = 24.sp, fontFamily = uniSans, color = Color.Black),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                    )
                }

                // Bottom stripe with genre and organizer logos
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .let {
                            if (event.type == "Premium") {
                                it.shimmerEffect() // Apply shimmer effect for Premium events
                            } else {
                                it.background(typeColor) // Regular background for other types
                            }
                        }
                ) {
                    // Genre Logo (Left Side of Stripe)
                    Image(
                        painter = painterResource(id = genreLogo), // Use the genre logo dynamically
                        contentDescription = "Genre Logo",
                        modifier = Modifier
                            .size(64.dp)
                            .align(Alignment.CenterStart) // Align to the left of the stripe
                            .padding(start = 8.dp)
                    )

                    // Organizer Profile Picture (Right Side of Stripe)
                    Image(
                        painter = painterResource(id = event.eventPost.organizer.profilePic ?: R.drawable.default_profile_pic), // Replace with actual organizer profile image
                        contentDescription = "Organizer Profile Picture",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterEnd) // Align to the right of the stripe
                            .padding(end = 8.dp)
                    )
                }
            }
        }
    }
}

