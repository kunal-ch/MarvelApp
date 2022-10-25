package com.kc.marvelapp.models

import java.io.Serializable

data class Thumbnail(
    val extension: String,
    val path: String
) : Serializable