package com.kc.marvelapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.kc.marvelapp.models.Thumbnail

class CharacterConverter {

    @TypeConverter
    fun fromThumbnail(thumbnail: Thumbnail): String {
        return Gson().toJson(thumbnail)
    }

    @TypeConverter
    fun toThumbnail(thumbnail: String): Thumbnail {
        return Gson().fromJson(thumbnail, Thumbnail::class.java)
    }
}