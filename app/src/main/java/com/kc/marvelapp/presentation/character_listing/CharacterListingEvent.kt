package com.kc.marvelapp.presentation.character_listing

sealed class CharacterListingEvent {
    object Refresh: CharacterListingEvent()
    data class OnSearchQueryChange(val query: String): CharacterListingEvent()
}