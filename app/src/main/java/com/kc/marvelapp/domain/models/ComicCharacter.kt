package com.kc.marvelapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "characters"
)
data class ComicCharacter(
    @PrimaryKey
    val id: Int,
    val modified: String,
    val description: String,
    val name: String,
    val resourceURI: String,
    val thumbnail: Thumbnail,
    val comics: Comics,
    var isFavourite: Boolean = false
) : Serializable