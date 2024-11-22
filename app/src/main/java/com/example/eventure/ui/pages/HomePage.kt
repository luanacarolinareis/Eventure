package com.example.eventure.ui.pages

import com.example.eventure.R
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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

@Composable
@Preview(showBackground = true)
fun HomePage(modifier: Modifier = Modifier) {
    val coimbraLatLng = LatLng(40.2056, -8.4196)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(coimbraLatLng, 12f)
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
                        androidx.compose.foundation.Image(
                            painter = painterResource(id = R.drawable.menu),
                            contentDescription = "Menu",
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    Button(
                        onClick = { /* Action */ },
                        modifier = Modifier
                            .fillMaxWidth(0.6f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0CCA9D)
                        )
                    ) {
                        Text(text = "Começar pesquisa")
                    }
                }

                androidx.compose.foundation.Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(100.dp)
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(500.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = false),
                uiSettings = MapUiSettings(zoomControlsEnabled = true)
            )
        }
    }
}
