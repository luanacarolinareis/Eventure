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
import com.google.accompanist.pager.*
import androidx.compose.foundation.ExperimentalFoundationApi

import androidx.compose.ui.layout.ContentScale

@Composable
fun OrganizerProfilePage(navController: NavController, organizer: Organizer) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Green Back Button
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF20B440)), // Green background
            modifier = Modifier.padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "<",
                style = TextStyle(fontSize = 20.sp, fontFamily = uniSans, color = Color.White)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Organizer Info
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = organizer.profilePic ?: R.drawable.default_profile_pic),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = organizer.id,
                    style = TextStyle(fontSize = 20.sp, fontFamily = uniSans, color = Color.Black)
                )
                Text(
                    text = "${organizer.rating}/5 stars | ${organizer.events.size} events",
                    style = TextStyle(fontSize = 16.sp, color = Color.Gray)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List of Events
        Text(
            text = "Events by ${organizer.id}:",
            style = TextStyle(fontSize = 18.sp, fontFamily = uniSans, color = Color.Black)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // LazyColumn for Event Cards
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(organizer.events) { event ->
                EventCard(event = event, navController = navController)
            }
        }
    }
}

