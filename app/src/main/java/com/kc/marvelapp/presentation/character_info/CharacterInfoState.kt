package com.kc.marvelapp.presentation.character_info

import com.kc.marvelapp.domain.models.ComicCharacter

/**
 * State for character detail screen
 */
data class CharacterInfoState(
    val character: ComicCharacter? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)