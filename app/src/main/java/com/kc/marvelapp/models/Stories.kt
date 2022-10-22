package com.kc.marvelapp.models

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<StoriesItem>,
    val returned: Int
)