package com.kc.marvelapp.models

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val characters: List<ComicCharacter>,
    val total: Int
)