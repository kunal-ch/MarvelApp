package com.kc.marvelapp.domain.models

import com.google.gson.annotations.SerializedName

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    @SerializedName("results")
    val characters: List<ComicCharacter>,
    val total: Int
)