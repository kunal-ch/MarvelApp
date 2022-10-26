package com.kc.marvelapp.navigation

sealed class Screen(val route: String){
    object CharacterListingScreen: Screen("character_listing_screen")
    object CharacterInfoScreen: Screen("character_info_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { args ->
                append("/$args")
            }
        }
    }
}
