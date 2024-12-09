package com.example.eventure.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.material3.*
import com.google.android.gms.maps.model.LatLng
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.eventure.components.uniSans
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun AddEventPage(navController: NavController, organizer: Organizer) {
    // State variables for user input
    var title by remember { mutableStateOf("") }
    var locationName by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Common") }
    var genre by remember { mutableStateOf("None") }
    var description by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }

    val eventTypes = listOf("Premium", "Rare", "Common")
    val genres = listOf("Sports", "Workshop", "Music", "Party", "None", "Mixed")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFFCE2B1))
    ) {
        // Go Back Button
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0CC59B)),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Go Back", style = TextStyle(fontFamily = uniSans, color = Color.White))
        }

        // Title Input
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Event Title") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF0CCA9D),
                unfocusedBorderColor = Color(0xFF0CCA9D),
                cursorColor = Color(0xFF0CCA9D)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Location Name Input
        OutlinedTextField(
            value = locationName,
            onValueChange = { locationName = it },
            label = { Text("Location Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF0CCA9D),
                unfocusedBorderColor = Color(0xFF0CCA9D),
                cursorColor = Color(0xFF0CCA9D)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Time Input
        OutlinedTextField(
            value = time,
            onValueChange = { time = it },
            label = { Text("Time (HH:mm)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF0CCA9D),
                unfocusedBorderColor = Color(0xFF0CCA9D),
                cursorColor = Color(0xFF0CCA9D)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Date Input
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF0CCA9D),
                unfocusedBorderColor = Color(0xFF0CCA9D),
                cursorColor = Color(0xFF0CCA9D)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Event Type Dropdown
        DropdownMenuInput(
            label = "Event Type",
            value = type,
            items = eventTypes,
            onValueChange = { type = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Genre Dropdown
        DropdownMenuInput(
            label = "Genre",
            value = genre,
            items = genres,
            onValueChange = { genre = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description Input
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF0CCA9D),
                unfocusedBorderColor = Color(0xFF0CCA9D),
                cursorColor = Color(0xFF0CCA9D)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Submit Button
        Button(
            onClick = {
                if (title.isNotBlank() && locationName.isNotBlank() && time.isNotBlank() && date.isNotBlank() && description.isNotBlank()) {
                    val newEvent = Event(
                        title = title,
                        locationName = locationName,
                        time = time,
                        date = date,
                        location = LatLng(0.0, 0.0), // Placeholder for now
                        type = type,
                        genre = genre,
                        eventPost = Post(
                            description = description,
                            organizer = organizer
                        )
                    )
                    showConfirmation = true
                    allEvents += newEvent
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0CC59B)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Event", style = TextStyle(fontFamily = uniSans, color = Color.White))
        }

        if (showConfirmation) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Event added successfully!",
                style = TextStyle(fontFamily = uniSans, fontSize = 16.sp, color = Color.Green),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

// Helper Component for Dropdown Menus
@Composable
fun DropdownMenuInput(
    label: String,
    value: String,
    items: List<String>,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = value,
            onValueChange = { },
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF0CCA9D),
                unfocusedBorderColor = Color(0xFF0CCA9D),
                cursorColor = Color(0xFF0CCA9D)
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onValueChange(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
