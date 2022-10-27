package com.kc.marvelapp.presentation.character_listing

import com.kc.marvelapp.domain.models.ComicCharacter

/**
 * State for character listing screen
 */
data class CharacterListingState(
    val characters: List<ComicCharacter> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
