package com.example.eventure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.eventure.ui.pages.AppNavigation
import com.example.eventure.components.EventureTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import android.util.Log
import com.google.android.libraries.places.api.Places

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start Firebase
        FirebaseApp.initializeApp(this)
        Log.d("FirebaseSetup", "Firebase configured successfully")

        // Test Firebase Analytics
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle().apply {
            putString("test_event", "Firebase connected successfully")
        }
        analytics.logEvent("test_event", bundle)
        Log.d("FirebaseAnalytics", "Test event sent to Firebase Analytics")

        // Initialize the Places API
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.google_maps_key))
        }

        setContent {
            EventureTheme {
                AppNavigation()
            }
        }
    }
}
