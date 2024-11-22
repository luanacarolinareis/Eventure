package com.example.eventure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.eventure.ui.pages.AppNavigation
import com.example.eventure.theme.EventureTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventureTheme {
                AppNavigation()
            }
        }
    }
}
