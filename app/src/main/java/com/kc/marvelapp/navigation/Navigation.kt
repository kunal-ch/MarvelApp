package com.kc.marvelapp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kc.marvelapp.navigation.Screen
import com.kc.marvelapp.presentation.character_info.CharacterInfoScreen
import com.kc.marvelapp.presentation.character_listing.CharacterListingScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.CharacterListingScreen.route){
        composable(route = Screen.CharacterListingScreen.route){
            CharacterListingScreen(navController = navController)
        }
        composable(
            route = Screen.CharacterInfoScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                }
            )
        ){ entry ->
            entry.arguments?.getString("id")?.let { CharacterInfoScreen(id = it) }
        }
    }
}