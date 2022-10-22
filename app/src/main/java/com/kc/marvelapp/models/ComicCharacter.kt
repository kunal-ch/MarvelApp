package com.kc.marvelapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "characters"
)
data class ComicCharacter(
    //val comics: Comics,
    val description: String,
    //val events: Events,
    @PrimaryKey
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    //val series: Series,
    //val stories: Stories,
    //val thumbnail: Thumbnail,
    //val urls: List<Url>
)