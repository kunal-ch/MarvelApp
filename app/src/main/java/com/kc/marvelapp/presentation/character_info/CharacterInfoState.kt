package com.kc.marvelapp.presentation.character_info

import com.kc.marvelapp.domain.models.ComicCharacter

data class CharacterInfoState(
    val character: ComicCharacter? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)