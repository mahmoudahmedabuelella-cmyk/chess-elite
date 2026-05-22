package com.chess.elite.ui.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Play : Screen("play", "Play")
    object Puzzles : Screen("puzzles", "Puzzles")
}

@Composable
fun ChessEliteAppNavigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                // Bottom bar mapping icons & labels
            }
        }
    ) { padding ->
        NavHost(navController, startDestination = "home") {
            composable("home") { HomeScreen() }
            composable("play") { PlayScreen() }
            composable("puzzles") { PuzzlesScreen() }
        }
    }
}