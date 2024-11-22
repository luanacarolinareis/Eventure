package com.example.eventure.ui.pages

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginPage(onLogin = { email, password ->
                // Authentication logic
                // Navigate to home page after successful login
                navController.navigate("home")
            })
        }
        composable("home") {
            HomePage()
        }
    }
}
