package com.example.eventure.ui.pages

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview(showBackground = true)
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        // Route for the login page
        composable("login") {
            LoginPage(
                onLogin = { email, password ->
                    navController.navigate("home")
                },
                onContinueAsGuest = {
                    navController.navigate("home")
                },
                onGoToRegister = {
                    navController.navigate("register")
                }
            )
        }

        // Route for the home page
        composable("home") {
            HomePage()
        }

        // Route for the register page
        composable("register") {
            RegisterPage(navController = navController)
        }
    }
}
