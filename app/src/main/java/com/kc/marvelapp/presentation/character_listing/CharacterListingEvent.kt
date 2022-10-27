package com.kc.marvelapp.presentation.character_listing

/**
 * Event for character listing screen
 */
sealed class CharacterListingEvent {
    object Refresh: CharacterListingEvent()
    data class OnSearchQueryChange(val query: String): CharacterListingEvent()
}