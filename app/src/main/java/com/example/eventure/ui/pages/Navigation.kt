package com.example.eventure.ui.pages

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.navArgument

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
            HomePage(navController = navController)
        }

        // Route for the register page
        composable("register") {
            RegisterPage(navController = navController)
        }

        // Explore/Search Page
        composable("explore_page") {
            val events = allEvents // Replace this with dynamic events if applicable
            ExplorePage(navController, events)
        }
        composable(
            route = "event_page/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            val event = allEvents.firstOrNull { it.title == eventId } // Retrieve event details
            if (event != null) {
                EventPage(navController = navController, eventId = eventId, event = event)
            } else {
                // Handle event not found
            }
        }

        // Organizer Profile Page
        composable(
            route = "organizer_profile/{organizerId}",
            arguments = listOf(navArgument("organizerId") { type = NavType.StringType })
        ) { backStackEntry ->
            val organizerId = backStackEntry.arguments?.getString("organizerId") ?: ""
            val organizer = allOrganizers.firstOrNull { it.id == organizerId } // Retrieve organizer details
            if (organizer != null) {
                OrganizerProfilePage(navController = navController, organizer = organizer)
            } else {
                // Handle organizer not found
            }
        }

        // Add event Page
        composable(
            route = "add_event_page/{organizerId}",
            arguments = listOf(navArgument("organizerId") { type = NavType.StringType })
        )  { backStackEntry ->
            val organizerId = backStackEntry.arguments?.getString("organizerId") ?: ""
            val organizer = allOrganizers.firstOrNull { it.id == organizerId } // Retrieve organizer details
            if (organizer != null) {
                AddEventPage(navController = navController, organizer = organizer)
            } else {
                // Handle organizer not found
            }
        }
    }
}
