package com.kc.marvelapp.presentation.character_listing

import com.kc.marvelapp.models.ComicCharacter

data class CharacterListingState(
    val characters: List<ComicCharacter> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
