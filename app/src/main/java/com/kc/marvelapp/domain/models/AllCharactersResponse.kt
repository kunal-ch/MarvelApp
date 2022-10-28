package com.kc.marvelapp.domain.models

data class AllCharactersResponse(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    var data: Data,
    val etag: String,
    val status: String
)